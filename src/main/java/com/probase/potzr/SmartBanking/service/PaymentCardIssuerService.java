package com.probase.potzr.SmartBanking.service;


import com.mastercard.developer.mdes_digital_enablement_client.ApiException;
import com.probase.potzr.SmartBanking.factory.PaymentCardIssuerFactory;
import com.probase.potzr.SmartBanking.models.core.Client;
import com.probase.potzr.SmartBanking.models.core.ClientSetting;
import com.probase.potzr.SmartBanking.models.enums.PaymentCardIssuer;
import com.probase.potzr.SmartBanking.models.requests.IssueCardRequest;
import com.probase.potzr.SmartBanking.models.responses.mc.IssueCardResponse;
import com.probase.potzr.SmartBanking.repositories.core.IClientRepository;
import com.probase.potzr.SmartBanking.repositories.core.IClientSettingRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Collection;

@Service
public class PaymentCardIssuerService {

    @Autowired
    private PaymentCardIssuerFactory paymentCardIssuerFactory;
    @Autowired
    private IClientRepository clientRepository;
    @Autowired
    private IClientSettingRepository clientSettingRepository;
    @Autowired
    private HttpServletRequest request;


    public IssueCardResponse issueCard(IssueCardRequest issueCardRequest) throws UnrecoverableKeyException, CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, ApiException {
        PaymentCardIssuer paymentCardIssuer = PaymentCardIssuer.MASTERCARD;
        String sourceBankCode = issueCardRequest.getBankCode();
        Client client = clientRepository.getClientByBankCode(sourceBankCode);
        Collection<ClientSetting> clientSettings = clientSettingRepository.getClientSettingByClientId(client.getClientId());
        return this.paymentCardIssuerFactory.issueCard(client, clientSettings,
                issueCardRequest);
    }
}
