package com.demo.bank.loans.service;

import com.demo.bank.loans.dto.LoanDto;

public interface ILoanService {
    /**
     * @param mobileNumber
     */
    void createLoan(String mobileNumber);

    /**
     * @param mobileNumber
     * @return LoanDto - Details of Loan
     */
    LoanDto getByMobileNumber(String correlationId, String mobileNumber);

    /**
     * @param loanDto
     * @return boolean - indicating if the operation was successful
     */
    boolean updateLoanDetails(LoanDto loanDto);

    /**
     * @param mobileNumber
     * @return boolean - indicating if the operation was successful
     */
    boolean deleteByMobileNumber(String mobileNumber);
}
