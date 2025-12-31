package com.probase.potzr.SmartBanking.models.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankAccountDTO {
    private String bankAccountNumber;
    private String bankAccountName;
    private String bankName;
    private String bankCode;
    private String branchCode;
    private String branchName;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String countryOfOrigin;
    private String gender;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressCountry;
    private String emailAddress;
    private String maritalStatus;
}

