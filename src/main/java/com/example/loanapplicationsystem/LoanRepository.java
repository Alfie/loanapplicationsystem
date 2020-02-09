package com.example.loanapplicationsystem;

import org.springframework.data.jpa.repository.CrudRepository;
 
public interface LoanRepository extends CrudRepository<Loan, Long> {
 
}
