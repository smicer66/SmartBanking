package com.probase.potzr.SmartBanking.models.responses.fundstransfer;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
public class FundsTransferResponse {
    private String fromAccount;
    private String fromBranch;
    private String fromCurrency;
    private String toAccount;
    private String toBranch;
    private String toCurrency;
    private BigDecimal transferAmount;
    private String ultimateBeneficiary;
    private String status;
    private BigInteger transactionId;
    private String transactionRef;

}
