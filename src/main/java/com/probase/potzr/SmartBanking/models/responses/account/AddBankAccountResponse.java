package com.probase.potzr.SmartBanking.models.responses.account;


import com.probase.potzr.SmartBanking.models.core.BankAccount;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBankAccountResponse {
    private int status;
    private String message;
    private BankAccount bankAccount;
}
