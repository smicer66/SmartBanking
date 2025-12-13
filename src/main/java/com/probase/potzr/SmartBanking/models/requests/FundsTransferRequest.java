package com.probase.potzr.SmartBanking.models.requests;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FundsTransferRequest {
    private String bankCode;
    private String bankAccountNumber;
    private String sourceIPAddress;
    private String userId;

}
