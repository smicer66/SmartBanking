package com.probase.potzr.SmartBanking.models.core;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankAccount {
    private String bankAccountNumber;
    private String bankAccountName;
    private String bankName;
    private String bankCode;
    private String branchCode;
    private String branchName;
}
