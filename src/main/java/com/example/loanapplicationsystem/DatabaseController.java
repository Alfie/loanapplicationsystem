package com.example.loanapplicationsystem;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DatabaseController {
  
  @Value("${spring.datasource.url}")
  private String dbUrl;
	
  @Value("classpath:schema.sql")
  private String fp;

  @Autowired
  private DataSource dataSource;
  
  @RequestMapping("/db")
  String db(Map<String, Object> model) {
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      //stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
	    try {
		executeScriptUsingStatement(stmt, fp);
	} catch (IOException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
      //stmt.executeUpdate("DROP TABLE IF EXISTS ticks");
      stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
      ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

      ArrayList<String> output = new ArrayList<String>();
      while (rs.next()) {
        output.add("Read from DB: " + rs.getTimestamp("tick"));
      }

      model.put("records", output);
      return "db";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }
	
  @Bean
  public DataSource dataSource() throws SQLException {
    if (dbUrl == null || dbUrl.isEmpty()) {
      return new HikariDataSource();
    } else {
      HikariConfig config = new HikariConfig();
      config.setJdbcUrl(dbUrl);
      return new HikariDataSource(config);
    }
  }

   static void executeScriptUsingStatement(Statement statement, String filepath ) throws IOException, SQLException {
	String scriptFilePath = filepath;
	BufferedReader reader = null;
	Statement statement = null;
	try {
		// load driver class for mysql
		//Class.forName("com.mysql.jdbc.Driver");
		// create statement object
		//statement = con.createStatement();
		// initialize file reader
		reader = new BufferedReader(new FileReader(filepath));
		String line = null;
		// read script line by line
		while ((line = reader.readLine()) != null) {
			// execute query
			statement.executeUpdate(line);
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		// close file reader
		if (reader != null) {
			reader.close();
		}
		// close db connection
		if (con != null) {
			con.close();
		}
	}
   }
}
