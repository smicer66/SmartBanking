package com.probase.potzr.SmartBanking.contract;

import com.probase.potzr.SmartBanking.models.enums.PaymentCardIssuer;
import com.probase.potzr.SmartBanking.models.requests.IssueCardRequest;
import com.probase.potzr.SmartBanking.models.responses.mc.IssueCardResponse;

public interface IPaymentCardIssuerProcessor {

    PaymentCardIssuer getPaymentCardIssuer();
    public IssueCardResponse issueCard(IssueCardRequest issueCardRequest);
}
