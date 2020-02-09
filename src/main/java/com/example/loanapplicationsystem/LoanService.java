package com.example.loanapplicationsystem;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
@Service
@Transactional
public class LoanService {
 
    @Autowired
    private LoanRepository repo;
     
    public List<Loan> listAll() {
        return repo.findAll();
    }
     
    public void save(Loan loan) {
        repo.save(loan);
    }
     
    public Loan get(long id) {
        return repo.findById(id).get();
    }
     
    public void delete(long id) {
        repo.deleteById(id);
    }
 
    @Bean(name="entityManagerFactory")
    public LocalSessionFactoryBean sessionFactory() {
       LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

       return sessionFactory;
    } 
}
