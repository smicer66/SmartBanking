package com.probase.potzr.SmartBanking.contract;


import com.probase.potzr.SmartBanking.models.core.Client;
import com.probase.potzr.SmartBanking.models.core.ClientSetting;
import com.probase.potzr.SmartBanking.models.enums.PaymentCardIssuer;
import com.probase.potzr.SmartBanking.models.requests.IssueCardRequest;
import com.probase.potzr.SmartBanking.models.responses.mc.IssueCardResponse;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Collection;

public interface IPaymentCardIssuerProcessor {

    PaymentCardIssuer getPaymentCardIssuer();
    IssueCardResponse issueCard(
            Client client,
            Collection<ClientSetting> clientSettings,
            IssueCardRequest issueCardRequest) throws UnrecoverableKeyException, CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException;
}
