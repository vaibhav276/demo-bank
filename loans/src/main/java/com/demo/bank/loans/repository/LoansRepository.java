package com.demo.bank.loans.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.bank.loans.entity.Loan;

@Repository
public interface LoansRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByMobileNumber(String mobileNumber);
    Optional<Loan> findByLoanNumber(String loanNumber);
}
