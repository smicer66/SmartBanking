package com.probase.potzr.SmartBanking.models.responses.fundstransfer;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FundsTransferRTGSResponse {

    private String fromAccount;
    private String fromBranch;
    private String fromCurrency;
    private String toAccount;
    private String toBranch;
    private String localBranch;
    private String toCurrency;
    private String transferAmount;
    private String ultimateBeneficiary;
    private String status;
    private String transactionId;
    private String productCode;
}
