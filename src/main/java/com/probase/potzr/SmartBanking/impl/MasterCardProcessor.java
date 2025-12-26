package com.probase.potzr.SmartBanking.impl;

import com.probase.potzr.SmartBanking.contract.IPaymentCardIssuerProcessor;
import com.probase.potzr.SmartBanking.models.core.Client;
import com.probase.potzr.SmartBanking.models.core.ClientSetting;
import com.probase.potzr.SmartBanking.models.core.Customer;
import com.probase.potzr.SmartBanking.models.core.IdentificationDocument;
import com.probase.potzr.SmartBanking.models.enums.ClientSettingName;
import com.probase.potzr.SmartBanking.models.enums.Gender;
import com.probase.potzr.SmartBanking.models.enums.MaritalStatus;
import com.probase.potzr.SmartBanking.models.enums.PaymentCardIssuer;
import com.probase.potzr.SmartBanking.models.mc.*;
import com.probase.potzr.SmartBanking.models.requests.CreateMCClientRequest;
import com.probase.potzr.SmartBanking.models.requests.IssueCardRequest;
import com.probase.potzr.SmartBanking.models.responses.fundstransfer.FundsTransferResponse;
import com.probase.potzr.SmartBanking.models.responses.mc.IssueCardResponse;
import com.probase.potzr.SmartBanking.repositories.core.IAddressRepository;
import com.probase.potzr.SmartBanking.repositories.core.IClientRepository;
import com.probase.potzr.SmartBanking.repositories.core.IClientSettingRepository;
import com.probase.potzr.SmartBanking.repositories.core.IIdentificationDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

public class MasterCardProcessor implements IPaymentCardIssuerProcessor {

    @Autowired
    private IClientRepository clientRepository;
    @Autowired
    private IIdentificationDocumentRepository identificationDocumentRepository;

    @Autowired
    private IClientSettingRepository clientSettingRepository;

    @Autowired
    private RestTemplate restTemplate;

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");


    @Value("${company.department}")
    private String companyDepartment;
    @Value("${company.name}")
    private String companyName;
    @Value("${company.base.country.of.operation}")
    private String baseCountryOfOperation;



    @Override
    public PaymentCardIssuer getPaymentCardIssuer() {
        return PaymentCardIssuer.MASTERCARD;
    }

    @Override
    public IssueCardResponse issueCard(IssueCardRequest issueCardRequest) {

        Customer customer = issueCardRequest.getCustomer();
        Client client = clientRepository.getClientByBankCode(issueCardRequest.getBankCode());
        Collection<ClientSetting> clientSettings = clientSettingRepository.getClientSettingByClientId(client.getClientId());

        String endppoint = null;
        Optional<ClientSetting> clientSetting = clientSettings.stream().filter(cs -> {
            return cs.getSettingName().equals(ClientSettingName.MASTERCARD_ISSUE_CARD_ENDPOINT.name());
        }).findFirst();

        IdentificationDocument identificationDocument = identificationDocumentRepository.
                getIdentifcationDocumentById(issueCardRequest.getCustomer().getIdentificationDocumentId());


        String uri = UriComponentsBuilder
                .fromUriString(clientSetting.get().getSettingValue())
                .build()
                .toString();

        Date cardRequestDate = issueCardRequest.getCardRequestDate();
        String additionalDate01 = df.format(cardRequestDate);
        String additionalDate02 = LocalDateTime.now().format(dtf);
        List<ClientCustomData> clientCustomDataList = new ArrayList<ClientCustomData>();
        ClientCustomData clientCustomData = new ClientCustomData();
        clientCustomData.setRemoveTag(false);
        clientCustomData.setTagContainer("ADD_INFO_01");
        clientCustomData.setTagName("CustomerCode");
        clientCustomData.setTagValue(issueCardRequest.getCustomer().getCustomerId().toString());
        clientCustomDataList.add(clientCustomData);

        ClientContactData clientContactData = new ClientContactData();
        clientContactData.setEmail(issueCardRequest.getCustomer().getEmailAddress());
        clientContactData.setPhoneNumberMobile(issueCardRequest.getCustomer().getMobileNumber());
        clientContactData.setPhoneNumberHome(issueCardRequest.getCustomer().getMobileNumber());
        clientContactData.setPhoneNumberHome(issueCardRequest.getCustomer().getMobileNumber());

        ClientCompanyData clientCompanyData = new ClientCompanyData();
        clientCompanyData.setCompanyDepartment(companyDepartment);
        clientCompanyData.setCompanyName(companyName);
        clientCompanyData.setCompanyTradeName(companyName);
        clientCompanyData.setPosition("Customer");

        ClientIdentificationData clientIdentificationData = new ClientIdentificationData();
        clientIdentificationData.setIdentificationDocumentType(identificationDocument.getIdentificationDocumentType().getValue());
        clientIdentificationData.setIdentificationDocumentDetails(identificationDocument.getIdentificationDocumentId().toString());
        clientIdentificationData.setIdentificationDocumentNumber(identificationDocument.getIdentificationNumber());

        ClientPersonalData clientPersonalData = new ClientPersonalData();
        clientPersonalData.setBirthDate(df.format(issueCardRequest.getCustomer().getDateOfBirth()));
        clientPersonalData.setGender(customer.getGender().equals(Gender.FEMALE) ? "F" : (customer.getGender().equals(Gender.MALE) ? "M" : null));
        clientPersonalData.setCitizenship(customer.getCountryOfOriginAlfa3Code());
        clientPersonalData.setFirstName(customer.getFirstName());
        clientPersonalData.setLastName(customer.getLastName());
        clientPersonalData.setMiddleName(customer.getMiddleName());
        clientPersonalData.setMaritalStatus(
                customer.getMaritalStatus().equals(MaritalStatus.SINGLE) ? "DS" :
                        (customer.getMaritalStatus().equals(MaritalStatus.MARRIED) ? "DM" :
                                (customer.getMaritalStatus().equals(MaritalStatus.DIVORCED) ? "DD" : "DX")));
        clientPersonalData.setTitle(customer.getTitle().name());


        EmbossedData embossedData = new EmbossedData();
        embossedData.setTitle(customer.getTitle().name());
        embossedData.setFirstName(customer.getFirstName());
        embossedData.setLastName(customer.getLastName());
        embossedData.setCompanyName(null);


        CreateMCClientRequest createMCClientRequest = new CreateMCClientRequest();
        createMCClientRequest.setAdditionalDate01(additionalDate01);
        createMCClientRequest.setAdditionalDate02(additionalDate02);
        createMCClientRequest.setClientCustomData(clientCustomDataList);
        createMCClientRequest.setClientContactData(clientContactData);
        createMCClientRequest.setClientCompanyData(clientCompanyData);
        createMCClientRequest.setClientNumber(issueCardRequest.getCustomer().getCustomerId().toString());
        createMCClientRequest.setClientType(issueCardRequest.getCustomer().getCountryOfOrigin().equals(baseCountryOfOperation) ? "PR" : "PNR");
        createMCClientRequest.setClientExpiryDate(null);
        createMCClientRequest.setClientIdentificationData(clientIdentificationData);
        createMCClientRequest.setClientPersonalData(clientPersonalData);
        createMCClientRequest.setEmbossedData(embossedData);

        IssueCardResponse issueCardResponse = new IssueCardResponse();

        ResponseEntity<IssueCardResponse> response =
                restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(createMCClientRequest),
                        new ParameterizedTypeReference<IssueCardResponse>() {
                        });

        if (response.getBody() != null) {
            issueCardResponse = response.getBody();
        }


        return issueCardResponse;
    }
}
