package com.probase.potzr.SmartBanking.models.responses.account;


import com.probase.potzr.SmartBanking.models.dto.BankAccountDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBankAccountResponse {
    private int status;
    private String message;
    private BankAccountDTO bankAccountDTO;
}
