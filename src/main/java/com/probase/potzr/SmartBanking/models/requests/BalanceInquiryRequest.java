package com.probase.potzr.SmartBanking.models.requests;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BalanceInquiryRequest {
    private String bankCode;
    private String bankAccountNumber;
    private String sourceIPAddress;
    private String userId;

}
