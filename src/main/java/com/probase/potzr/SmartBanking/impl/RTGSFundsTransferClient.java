package com.probase.potzr.SmartBanking.impl;

import com.probase.potzr.SmartBanking.contract.IFundsTransferClient;
import com.probase.potzr.SmartBanking.exceptions.ApplicationException;
import com.probase.potzr.SmartBanking.models.Client;
import com.probase.potzr.SmartBanking.models.ClientSetting;
import com.probase.potzr.SmartBanking.models.enums.ClientSettingName;
import com.probase.potzr.SmartBanking.models.enums.FundsTransferType;
import com.probase.potzr.SmartBanking.models.requests.FundsTransferRequest;
import com.probase.potzr.SmartBanking.models.responses.balanceinquiry.BalanceInquiryCasaResponse;
import com.probase.potzr.SmartBanking.models.responses.balanceinquiry.BalanceInquiryResponse;
import com.probase.potzr.SmartBanking.models.responses.fundstransfer.FundsTransferRTGSResponse;
import com.probase.potzr.SmartBanking.models.responses.fundstransfer.FundsTransferResponse;
import com.probase.potzr.SmartBanking.repositories.IClientRepository;
import com.probase.potzr.SmartBanking.repositories.IClientSettingRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
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
    private IClientRepository clientRepository;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = Logger.getLogger("RTGSFundsTransferClient");


    @Autowired
    private IClientSettingRepository clientSettingRepository;

    @Override
    public FundsTransferType getFundsTransferType() {
        return FundsTransferType.RTGS;
    }

    @Override
    public FundsTransferResponse doFundsTransfer(FundsTransferRequest fundsTransferRequest) throws ApplicationException {
        String sourceAccountNumber = fundsTransferRequest.getFromAccount();
        String recipientAccountNumber = fundsTransferRequest.getToAccount();
        String sourceBankCode = fundsTransferRequest.getFrombankCode();
        String recipientBankCode = fundsTransferRequest.getToBankCode();
        BigDecimal transferAmount = fundsTransferRequest.getTransferAmount();
        String sourceCurrency = fundsTransferRequest.getFromCurrency();
        String recipientCurrency = fundsTransferRequest.getToCurrency();




        Client client = clientRepository.getClientByBankCode(sourceBankCode);
        Collection<ClientSetting> clientSettings = clientSettingRepository.getClientSettingByClientId(client.getClientId());
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

                return fundsTransferResponse;
            }
        } catch (RuntimeException e) {
            throw new ApplicationException("Failed to retrieve balance inquiry", e);
        }
        return null;
    }
}
