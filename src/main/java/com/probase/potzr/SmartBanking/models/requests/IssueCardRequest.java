package com.probase.potzr.SmartBanking.models.requests;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;


@Getter
@Setter
public class IssueCardRequest {
    private BigInteger customerId;
    private BigInteger walletId;
    private String cardType;
    private String paymentCardIssuer;
    private String bankCode;
    private LocalDateTime requestDate;
}
