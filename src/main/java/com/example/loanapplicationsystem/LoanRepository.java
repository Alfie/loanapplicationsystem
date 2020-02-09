package com.example.loanapplicationsystem;

import org.springframework.data.jpa.repository.JpaRepository;
 
public interface LoanRepository extends JpaRepository<Loan, Long> {
 
}
