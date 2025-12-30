package com.probase.potzr.SmartBanking.impl;

import com.probase.potzr.SmartBanking.contract.ICoreBanking;
import com.probase.potzr.SmartBanking.exceptions.ApplicationException;
import com.probase.potzr.SmartBanking.models.core.*;
import com.probase.potzr.SmartBanking.models.enums.ClientSettingName;
import com.probase.potzr.SmartBanking.models.enums.CoreBankingType;
import com.probase.potzr.SmartBanking.models.enums.TokenType;
import com.probase.potzr.SmartBanking.models.requests.AddBankAccountRequest;
import com.probase.potzr.SmartBanking.models.requests.BalanceInquiryRequest;
import com.probase.potzr.SmartBanking.models.responses.account.AddBankAccountResponse;
import com.probase.potzr.SmartBanking.models.responses.account.CustomerDetailResponse;
import com.probase.potzr.SmartBanking.models.responses.balanceinquiry.BalanceInquiryCasaResponse;
import com.probase.potzr.SmartBanking.models.responses.balanceinquiry.BalanceInquiryResponse;
import com.probase.potzr.SmartBanking.models.responses.dummy.FlexCubeCustomerDetailCasaResponse;
import com.probase.potzr.SmartBanking.providers.TokenProvider;
import com.probase.potzr.SmartBanking.repositories.core.ITokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;


@Component
public class FlexCubeCoreBanking implements ICoreBanking {

    private static CoreBankingType coreBankingType = CoreBankingType.FLEXCUBE;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    ITokenRepository tokenRepository;

    @Autowired
    private HttpServletRequest request;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public CoreBankingType getCoreBankingType() {
        return coreBankingType;
    }

    @Value("${user.account.token.valid.period}")
    private int userAccountTokenValidPeriod;

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
                balanceInquiryResponse.setCustomerNumber(balanceInquiryCasaResponse.getCustNo());
                balanceInquiryResponse.setBankAccountName(balanceInquiryCasaResponse.getCustAcDescription());
                return balanceInquiryResponse;
            }
        } catch (RuntimeException e) {
            throw new ApplicationException("Failed to retrieve balance inquiry", e);
        }
        return null;

    }


    @Override
    public AddBankAccountResponse getCustomerDetailByAccountNo(Client client, Collection<ClientSetting> clientSettings, AddBankAccountRequest addBankAccountRequest) throws ApplicationException {

        String accountNumber = addBankAccountRequest.getAccountNumber();
        String bankCode = addBankAccountRequest.getBankCode();
        String jwtToken = this.request.getHeader("Authorization").substring("Bearer ".length());
        User user = tokenProvider.getUserFromToken(jwtToken);


        BalanceInquiryRequest balanceInquiryRequest = new BalanceInquiryRequest();
        balanceInquiryRequest.setBankCode(bankCode);
        balanceInquiryRequest.setBankAccountNumber(accountNumber);
        balanceInquiryRequest.setSourceIPAddress("127.0.0.1");
        balanceInquiryRequest.setUserId(user.getUserId().toString());
        BalanceInquiryResponse balanceInquiryResponse = this.getBalanceInquiry(clientSettings, balanceInquiryRequest);

        String customerNumber = balanceInquiryResponse.getCustomerNumber();



        try {
            String endppoint = null;
            Optional<ClientSetting> clientSetting = clientSettings.stream().filter(cs -> {
                return cs.getSettingName().equals(ClientSettingName.CUSTOMER_INQUIRY_ENDPOINT.name());
            }).findFirst();

            //http://(IP):(Port)/FCLiteWeb/Customer/get/{customerNumber}
            String uri = UriComponentsBuilder
                    .fromUriString(clientSetting.get().getSettingValue())
                    .path(String.format("/%s/%s", customerNumber, bankCode))
                    .build()
                    .toString();

            logger.info("Uri...{}", uri);

            ResponseEntity<FlexCubeCustomerDetailCasaResponse> response =
                    restTemplate.exchange(uri, HttpMethod.GET, null,
                            new ParameterizedTypeReference<FlexCubeCustomerDetailCasaResponse>() {
                            });

            if (response.getBody() != null) {
                FlexCubeCustomerDetailCasaResponse flexCubeCustomerDetailCasaResponse =  response.getBody();

                BankAccount bankAccount = new BankAccount();
                bankAccount.setBankCode(bankCode);
                bankAccount.setBankName(client.getClientName());
                bankAccount.setBankAccountNumber(balanceInquiryResponse.getBankAccountNumber());
                bankAccount.setBranchCode(flexCubeCustomerDetailCasaResponse.getBRANCH_CODE());
                bankAccount.setBankAccountName(balanceInquiryResponse.getBankAccountName());

                AddBankAccountResponse addBankAccountResponse = new AddBankAccountResponse();
                addBankAccountResponse.setBankAccount(bankAccount);
                addBankAccountResponse.setStatus(0);
                addBankAccountResponse.setMessage("Account validated successfully");


                Token token  = new Token();
                token.setTokenOwnedByUserId(user.getUserId());
                token.setToken(RandomStringUtils.randomNumeric(6));
                token.setExpiredAt(LocalDateTime.now().plusHours(userAccountTokenValidPeriod));
                token.setTokenType(TokenType.SIGNUP);
                tokenRepository.save(token);

                return addBankAccountResponse;
            }
        } catch (RuntimeException e) {
            throw new ApplicationException("Failed to retrieve balance inquiry", e);
        }
        return null;

    }
}
