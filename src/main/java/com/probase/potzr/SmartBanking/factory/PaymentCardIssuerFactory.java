package com.probase.potzr.SmartBanking.factory;


import com.probase.potzr.SmartBanking.contract.IPaymentCardIssuerProcessor;
import com.probase.potzr.SmartBanking.models.core.Client;
import com.probase.potzr.SmartBanking.models.core.ClientSetting;
import com.probase.potzr.SmartBanking.models.enums.CoreBankingType;
import com.probase.potzr.SmartBanking.models.enums.PaymentCardIssuer;
import com.probase.potzr.SmartBanking.models.requests.CreateMCClientRequest;
import com.probase.potzr.SmartBanking.models.requests.IssueCardRequest;
import com.probase.potzr.SmartBanking.models.responses.mc.IssueCardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.*;

@Component
public class PaymentCardIssuerFactory {
    private Map<PaymentCardIssuer, IPaymentCardIssuerProcessor> paymentCardIssuerProcessorMap= new HashMap<PaymentCardIssuer, IPaymentCardIssuerProcessor>();


    @Autowired
    public PaymentCardIssuerFactory(List<IPaymentCardIssuerProcessor> paymentCardIssuerProcessorList)
    {
        System.out.println("size2 = " + paymentCardIssuerProcessorList.size());
        paymentCardIssuerProcessorList.forEach(pcip -> {
            this.paymentCardIssuerProcessorMap.put(pcip.getPaymentCardIssuer(), pcip);
        });
    }


    public List<PaymentCardIssuer> getPaymentCardIssuers() {
        return new ArrayList<>(paymentCardIssuerProcessorMap.keySet());
    }

    public IssueCardResponse issueCard(Client client, Collection<ClientSetting> clientSettings,
           IssueCardRequest issueCardRequest) throws UnrecoverableKeyException, CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException {
        System.out.println("issueCardRequest.getPaymentCardIssuer()...." + issueCardRequest.getPaymentCardIssuer());
        System.out.println(this.paymentCardIssuerProcessorMap.keySet());
        IPaymentCardIssuerProcessor iPaymentCardIssuerProcessor = this.paymentCardIssuerProcessorMap.get(PaymentCardIssuer.valueOf(issueCardRequest.getPaymentCardIssuer()));
        return iPaymentCardIssuerProcessor.issueCard(client, clientSettings, issueCardRequest);
    }
}
