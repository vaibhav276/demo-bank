package com.demo.bank.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Account extends BaseEntity {
    @Id
    private Long accountNumber;

    @Column
    private Long customerId;

    @Column
    private String accountType;

    @Column
    private String branchAddress;

    @Column(name = "communication_sw")
    private Boolean communicationSw;
}
