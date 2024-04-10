package com.demo.bank.loans.service.impl;

import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.demo.bank.loans.constants.LoanConstants;
import com.demo.bank.loans.dto.LoanDto;
import com.demo.bank.loans.entity.Loan;
import com.demo.bank.loans.exception.LoanAlreadyExistsException;
import com.demo.bank.loans.exception.ResourceNotFoundException;
import com.demo.bank.loans.mapper.LoanMapper;
import com.demo.bank.loans.repository.LoansRepository;
import com.demo.bank.loans.service.ILoanService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements ILoanService {

    LoansRepository loansRepository;

    public static final Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loan> loan = loansRepository.findByMobileNumber(mobileNumber);
        if (loan.isPresent()) {
            throw new LoanAlreadyExistsException(mobileNumber);
        }

        Loan newLoan = createNewLoan(mobileNumber);
        loansRepository.save(newLoan);
    }

    private Loan createNewLoan(String mobileNumber) {
        Loan newLoan = new Loan();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoanConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    @Override
    public LoanDto getByMobileNumber(String correlationId, String mobileNumber) {

        logger.debug("[{}]: Getting loan for mobile number {}", correlationId, mobileNumber);

        Loan loan = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );

        return LoanMapper.mapToLoanDto(loan, new LoanDto());
    }

    @Override
    public boolean updateLoanDetails(LoanDto loanDto) {
        String loanNumber = loanDto.getLoanNumber();
        Loan loan = loansRepository.findByLoanNumber(loanNumber).orElseThrow(
            () -> new ResourceNotFoundException("Loan", "loanNumber", loanNumber)
        );

        LoanMapper.mapToLoan(loanDto, loan);
        loansRepository.save(loan);

        return true;
    }

    @Override
    public boolean deleteByMobileNumber(String mobileNumber) {
        Loan loan = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );

        loansRepository.deleteById(loan.getLoanId());
        return true;
    }
    
}
