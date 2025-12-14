package com.probase.potzr.SmartBanking.models.requests;


import com.probase.potzr.SmartBanking.models.enums.FundsTransferType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FundsTransferRequest {
    private String fromAccount;
    private String frombankCode;
    private String fromBranchCode;
    private String fromCurrency;
    private String toAccount;
    private String toBranchCode;
    private String toBankCode;
    private String toCurrency;
    private String transferAmount;
    private String ultimateBeneficiaryCode;
    private FundsTransferType fundsTransferType;

}
