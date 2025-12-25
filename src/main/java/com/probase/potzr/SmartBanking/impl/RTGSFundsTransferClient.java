package com.probase.potzr.SmartBanking.impl;

import com.google.gson.Gson;
import com.probase.potzr.SmartBanking.contract.IFundsTransferClient;
import com.probase.potzr.SmartBanking.exceptions.ApplicationException;
import com.probase.potzr.SmartBanking.models.core.Client;
import com.probase.potzr.SmartBanking.models.core.ClientSetting;
import com.probase.potzr.SmartBanking.models.core.Transaction;
import com.probase.potzr.SmartBanking.models.enums.*;
import com.probase.potzr.SmartBanking.models.requests.FundsTransferRequest;
import com.probase.potzr.SmartBanking.models.responses.fundstransfer.FundsTransferRTGSResponse;
import com.probase.potzr.SmartBanking.models.responses.fundstransfer.FundsTransferResponse;
import com.probase.potzr.SmartBanking.repositories.core.ITransactionRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class RTGSFundsTransferClient implements IFundsTransferClient {


    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ITransactionRepository transactionRepository;

    private Logger logger = Logger.getLogger("RTGSFundsTransferClient");

    @Override
    public FundsTransferType getFundsTransferType() {
        return FundsTransferType.RTGS;
    }

    @Override
    public FundsTransferResponse doFundsTransfer(Client client, Collection<ClientSetting> clientSettings,
                     FundsTransferRequest fundsTransferRequest) throws ApplicationException {
        String sourceAccountNumber = fundsTransferRequest.getFromAccount();
        String recipientAccountNumber = fundsTransferRequest.getToAccount();
        String sourceBankCode = fundsTransferRequest.getFrombankCode();
        String recipientBankCode = fundsTransferRequest.getToBankCode();
        BigDecimal transferAmount = fundsTransferRequest.getTransferAmount();
        String sourceCurrency = fundsTransferRequest.getFromCurrency();
        String recipientCurrency = fundsTransferRequest.getToCurrency();


        Transaction transaction = new Transaction();
        transaction.setTransactionRef(RandomStringUtils.randomAlphanumeric(16).toUpperCase());
        transaction.setClientId(client.getClientId());
        transaction.setChannel(fundsTransferRequest.getChannel());
        transaction.setMessageRequest(new Gson().toJson(fundsTransferRequest));
        transaction.setOrderRef(fundsTransferRequest.getOrderRef());
        transaction.setRecipientDetails(fundsTransferRequest.getToAccount()+"||||"+fundsTransferRequest.getToBankCode()+"||||"+fundsTransferRequest.getToBranchCode());
        transaction.setFixedCharge(BigDecimal.valueOf(0.00));
        transaction.setTransactionCharge(BigDecimal.valueOf(0.00));
        transaction.setSchemeTransactionCharge(BigDecimal.valueOf(0.00));
        transaction.setSchemeTransactionPercentage(BigDecimal.valueOf(0.00));
        transaction.setServiceType(ServiceType.FUNDS_TRANSFER);
        transaction.setSmartBankingCurrency(SmartBankingCurrency.valueOf(fundsTransferRequest.getFromCurrency()));
        transaction.setStatus(TransactionStatus.PENDING);
        transaction = (Transaction) transactionRepository.save(transaction);




        try {
            String endppoint = null;
            Optional<ClientSetting> clientSetting = clientSettings.stream().filter(cs -> {
                return cs.getSettingName().equals(ClientSettingName.FUNDS_TRANSFER_RTGS_ENDPOINT.name());
            }).findFirst();

            //http://(IP):(Port)/FCLiteWeb/FundTransferService/createContract
            //RestService/Oracle_Flexcube_Restful_Services_Usage.pdf
            String uri = UriComponentsBuilder
                    .fromUriString(clientSetting.get().getSettingValue())
                    .build()
                    .toString();

            logger.info(uri);

            ResponseEntity<FundsTransferRTGSResponse> response =
                    restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(fundsTransferRequest),
                            new ParameterizedTypeReference<FundsTransferRTGSResponse>() {
                            });

            if (response.getBody() != null) {
                FundsTransferRTGSResponse fundsTransferRTGSResponse =  response.getBody();

                FundsTransferResponse fundsTransferResponse = new FundsTransferResponse();
                BeanUtils.copyProperties(fundsTransferRTGSResponse, fundsTransferResponse);

                if(fundsTransferResponse.getStatus().equals("Success"))
                {
                    transaction.setStatus(TransactionStatus.SUCCESS);
                    transaction.setBankPaymentReference(fundsTransferResponse.getTransactionRef());
                }

                return fundsTransferResponse;
            }
        } catch (RuntimeException e) {
            throw new ApplicationException("Failed to retrieve balance inquiry", e);
        }
        return null;
    }
}
