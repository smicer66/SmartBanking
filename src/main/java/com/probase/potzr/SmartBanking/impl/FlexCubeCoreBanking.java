package com.probase.potzr.SmartBanking.impl;

import com.probase.potzr.SmartBanking.contract.ICoreBanking;
import com.probase.potzr.SmartBanking.exceptions.ApplicationException;
import com.probase.potzr.SmartBanking.models.core.ClientSetting;
import com.probase.potzr.SmartBanking.models.enums.ClientSettingName;
import com.probase.potzr.SmartBanking.models.enums.CoreBankingType;
import com.probase.potzr.SmartBanking.models.requests.BalanceInquiryRequest;
import com.probase.potzr.SmartBanking.models.responses.balanceinquiry.BalanceInquiryCasaResponse;
import com.probase.potzr.SmartBanking.models.responses.balanceinquiry.BalanceInquiryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;


@Component
public class FlexCubeCoreBanking implements ICoreBanking {

    private static CoreBankingType coreBankingType = CoreBankingType.FLEXCUBE;

    @Autowired
    RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public CoreBankingType getCoreBankingType() {
        return coreBankingType;
    }

    @Override
    public BalanceInquiryResponse getBalanceInquiry(Collection<ClientSetting> clientSettings, BalanceInquiryRequest balanceInquiryRequest) throws ApplicationException {

        String accountNumber = balanceInquiryRequest.getBankAccountNumber();
        String bankCode = balanceInquiryRequest.getBankCode();

        try {
            String endppoint = null;
            Optional<ClientSetting> clientSetting = clientSettings.stream().filter(cs -> {
                return cs.getSettingName().equals(ClientSettingName.BALANCE_INQUIRY_ENDPOINT.name());
            }).findFirst();

            //http://(IP):(Port)/AccountService/v12.3/accounts /casaBalance/{accountNumber}
            String uri = UriComponentsBuilder
                    .fromUriString(clientSetting.get().getSettingValue())
                    .path(String.format("/%s", accountNumber))
                    .build()
                    .toString();

            logger.info("Uri...{}", uri);

            ResponseEntity<BalanceInquiryCasaResponse> response =
                    restTemplate.exchange(uri, HttpMethod.GET, null,
                            new ParameterizedTypeReference<BalanceInquiryCasaResponse>() {
                            });

            if (response.getBody() != null) {
                BalanceInquiryCasaResponse balanceInquiryCasaResponse =  response.getBody();

                BalanceInquiryResponse balanceInquiryResponse = new BalanceInquiryResponse();
                balanceInquiryResponse.setCurrency(balanceInquiryCasaResponse.getCcy());
                balanceInquiryResponse.setBankAccountNumber(balanceInquiryCasaResponse.getCustAcNo());
                balanceInquiryResponse.setAccountBalance(BigDecimal.valueOf(Double.parseDouble(balanceInquiryCasaResponse.getAvailableBalance())));
                balanceInquiryResponse.setCurrentBalance(BigDecimal.valueOf(Double.parseDouble(balanceInquiryCasaResponse.getAvailableBalance())));
                return balanceInquiryResponse;
            }
        } catch (RuntimeException e) {
            throw new ApplicationException("Failed to retrieve balance inquiry", e);
        }
        return null;

    }
}
