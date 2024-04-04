package com.demo.bank.loans.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Loan extends BaseEntity {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long loanId;

    @Column
	private String mobileNumber;

    @Column
	private String loanNumber;

    @Column
	private String loanType;

    @Column
	private int totalLoan;

    @Column
	private int amountPaid;

    @Column
	private int outstandingAmount;
}
