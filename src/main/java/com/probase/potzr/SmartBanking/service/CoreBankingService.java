package com.probase.potzr.SmartBanking.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.probase.potzr.SmartBanking.exceptions.ApplicationException;
import com.probase.potzr.SmartBanking.factory.CoreBankingFactory;
import com.probase.potzr.SmartBanking.factory.FundsTransferFactory;
import com.probase.potzr.SmartBanking.models.core.*;
import com.probase.potzr.SmartBanking.models.dto.BankAccountDTO;
import com.probase.potzr.SmartBanking.models.enums.CoreBankingType;
import com.probase.potzr.SmartBanking.models.enums.Gender;
import com.probase.potzr.SmartBanking.models.enums.MaritalStatus;
import com.probase.potzr.SmartBanking.models.enums.TokenType;
import com.probase.potzr.SmartBanking.models.requests.AddBankAccountRequest;
import com.probase.potzr.SmartBanking.models.requests.BalanceInquiryRequest;
import com.probase.potzr.SmartBanking.models.requests.FundsTransferRequest;
import com.probase.potzr.SmartBanking.models.responses.account.AddBankAccountResponse;
import com.probase.potzr.SmartBanking.models.responses.balanceinquiry.BalanceInquiryResponse;
import com.probase.potzr.SmartBanking.models.responses.fundstransfer.FundsTransferResponse;
import com.probase.potzr.SmartBanking.providers.TokenProvider;
import com.probase.potzr.SmartBanking.repositories.core.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.management.ObjectName;
import java.time.LocalDate;
import java.util.Collection;

@Service
public class CoreBankingService {

    @Autowired
    private CoreBankingFactory coreBankingFactory;
    @Autowired
    private FundsTransferFactory fundsTransferFactory;

    @Autowired
    private IClientSettingRepository clientSettingRepository;
    @Autowired
    private IClientRepository clientRepository;
    @Autowired
    private ITokenRepository tokenRepository;
    @Autowired
    private IAddressRepository addressRepository;
    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    private IBankAccountRepository bankAccountRepository;
    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private HttpServletRequest request;

    public BalanceInquiryResponse getAccountBalanceInquiry(@RequestBody BalanceInquiryRequest balanceInquiryRequest) throws ApplicationException {
        CoreBankingType coreBankingType = CoreBankingType.FLEXCUBE;

        String bankCode = balanceInquiryRequest.getBankCode();
        Client client = clientRepository.getClientByBankCode(bankCode);
        Collection<ClientSetting> clientSettings = clientSettingRepository.getClientSettingByClientId(client.getClientId());
        return coreBankingFactory.getAccountBalanceInquiry(clientSettings, coreBankingType, balanceInquiryRequest);//.getCoreBankingType().name();
//        return new BalanceInquiryResponse();
    }

    public FundsTransferResponse doFundsTransfer(@RequestBody FundsTransferRequest fundsTransferRequest) throws ApplicationException {

        String sourceBankCode = fundsTransferRequest.getFrombankCode();
        Client client = clientRepository.getClientByBankCode(sourceBankCode);
        Collection<ClientSetting> clientSettings = clientSettingRepository.getClientSettingByClientId(client.getClientId());
        System.out.println("client settings .." + clientSettings.size());
        return fundsTransferFactory.doFundsTransfer(client, clientSettings, fundsTransferRequest);
//        return new FundsTransferResponse();
    }


    public AddBankAccountResponse addBankAccountToCustomer(@RequestBody AddBankAccountRequest addBankAccountRequest) throws ApplicationException {
        String bankCode = addBankAccountRequest.getBankCode();

        CoreBankingType coreBankingType = CoreBankingType.FLEXCUBE;

        Client client = clientRepository.getClientByBankCode(bankCode);
        Collection<ClientSetting> clientSettings = clientSettingRepository.getClientSettingByClientId(client.getClientId());
        return coreBankingFactory.addBankAccountToCustomer(client, clientSettings, coreBankingType, addBankAccountRequest);//.getCoreBankingType().name();
    }

    public AddBankAccountResponse confirmAddAccount(String token) throws ApplicationException, JsonProcessingException {
        String jwtToken = this.request.getHeader("Authorization").substring("Bearer ".length());
        User user = tokenProvider.getUserFromToken(jwtToken);
        Token tk = tokenRepository.getValidToken(user.getUserId(), token, TokenType.BANK_ACCOUNT_VALIDATION);
        if(tk!=null)
        {
            String tokenData = tk.getData();
            ObjectMapper objectMapper = new ObjectMapper();
            AddBankAccountResponse addBankAccountResponse = objectMapper.readValue(tokenData, AddBankAccountResponse.class);
            BankAccountDTO bankAccountDTO = addBankAccountResponse.getBankAccountDTO();

            Address address  = new Address();
            address.setAddressLine1(bankAccountDTO.getAddressLine1());
            address.setAddressLine2(bankAccountDTO.getAddressLine2());
            address.setAddressLine3(bankAccountDTO.getAddressLine3());
            address.setAddressLine4(bankAccountDTO.getAddressLine4());
            address.setCountry(bankAccountDTO.getAddressCountry());
            address = (Address)addressRepository.save(address);


            Customer customer = new Customer();
            customer.setCountryOfOrigin(bankAccountDTO.getCountryOfOrigin());
            customer.setGender(Gender.valueOf(addBankAccountResponse.getBankAccountDTO().getGender()));
            customer.setCountryOfOriginAlfa3Code(bankAccountDTO.getCountryOfOrigin());
            customer.setCurrentAddressId(address.getAddressId());
            LocalDate dateOfBirth = LocalDate.parse(addBankAccountResponse.getBankAccountDTO().getDateOfBirth());
            customer.setDateOfBirth(dateOfBirth);
            customer.setEmailAddress(addBankAccountResponse.getBankAccountDTO().getEmailAddress());
            customer.setMobileNumber(user.getMobileNumber());
            customer.setFirstName(bankAccountDTO.getFirstName());
            customer.setLastName(bankAccountDTO.getLastName());
            customer.setMaritalStatus(MaritalStatus.valueOf(bankAccountDTO.getMaritalStatus()));
            customer.setUserId(user.getUserId());
            customer  = (Customer)this.customerRepository.save(customer);

            BankAccount bankAccount = new BankAccount();
            BeanUtils.copyProperties(bankAccountDTO, bankAccount);
            bankAccount.setBankAccountName(bankAccountDTO.getBankAccountName());
            bankAccount.setBankAccountNumber(bankAccountDTO.getBankAccountNumber());
            bankAccount.setCustomerId(customer.getCustomerId());
            bankAccount = this.bankAccountRepository.save(bankAccount);

            AddBankAccountResponse addBankAccountResponse1 = new AddBankAccountResponse();
            addBankAccountResponse1.setMessage("Your account has been added to your profile. You can now carry out transactions using your bank account");
            addBankAccountResponse1.setStatus(0);
            return addBankAccountResponse1;
        }

        AddBankAccountResponse addBankAccountResponse = new AddBankAccountResponse();
        addBankAccountResponse.setMessage("We could not confirm your bank account. No bank account has been added to your profile");
        addBankAccountResponse.setStatus(1);
        return addBankAccountResponse;
    }
}
