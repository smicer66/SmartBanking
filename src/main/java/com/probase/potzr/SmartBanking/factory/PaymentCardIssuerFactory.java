package com.probase.potzr.SmartBanking.factory;

import com.probase.potzr.SmartBanking.contract.IPaymentCardIssuerProcessor;
import com.probase.potzr.SmartBanking.models.enums.PaymentCardIssuer;
import com.probase.potzr.SmartBanking.models.requests.IssueCardRequest;
import com.probase.potzr.SmartBanking.models.responses.mc.IssueCardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PaymentCardIssuerFactory {
    private Map<PaymentCardIssuer, IPaymentCardIssuerProcessor> paymentCardIssuerProcessorMap= new HashMap<>();


    @Autowired
    public PaymentCardIssuerFactory(List<IPaymentCardIssuerProcessor> paymentCardIssuerProcessorList)
    {
        paymentCardIssuerProcessorList.forEach(pcip -> {
            this.paymentCardIssuerProcessorMap.put(pcip.getPaymentCardIssuer(), pcip);
        });
    }


    public IssueCardResponse issueCard(IssueCardRequest issueCardRequest)
    {
        IPaymentCardIssuerProcessor iPaymentCardIssuerProcessor = this.paymentCardIssuerProcessorMap.get(issueCardRequest.getPaymentCardIssuer());
        return iPaymentCardIssuerProcessor.issueCard(issueCardRequest);
    }
}
