package com.probase.potzr.SmartBanking.service;


import com.probase.potzr.SmartBanking.factory.PaymentCardIssuerFactory;
import com.probase.potzr.SmartBanking.models.requests.IssueCardRequest;
import com.probase.potzr.SmartBanking.models.responses.mc.IssueCardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    @Autowired
    private PaymentCardIssuerFactory paymentCardIssuerFactory;


    public IssueCardResponse issueCard(IssueCardRequest issueCardRequest)
    {
        return paymentCardIssuerFactory.issueCard(issueCardRequest);
    }
}
