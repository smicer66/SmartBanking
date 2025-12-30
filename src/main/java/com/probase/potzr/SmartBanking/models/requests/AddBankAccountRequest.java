package com.probase.potzr.SmartBanking.models.requests;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBankAccountRequest {
    private String bankCode;
    private String accountNumber;

}
