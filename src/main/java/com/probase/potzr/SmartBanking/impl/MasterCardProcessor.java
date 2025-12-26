package com.probase.potzr.SmartBanking.impl;

import com.probase.potzr.SmartBanking.contract.IPaymentCardIssuerProcessor;
import com.probase.potzr.SmartBanking.models.enums.PaymentCardIssuer;
import com.probase.potzr.SmartBanking.models.requests.IssueCardRequest;
import com.probase.potzr.SmartBanking.models.responses.mc.IssueCardResponse;

public class MasterCardProcessor implements IPaymentCardIssuerProcessor {
    @Override
    public PaymentCardIssuer getPaymentCardIssuer() {
        return PaymentCardIssuer.MASTERCARD;
    }

    @Override
    public IssueCardResponse issueCard(IssueCardRequest issueCardRequest) {
        return null;
    }
}
