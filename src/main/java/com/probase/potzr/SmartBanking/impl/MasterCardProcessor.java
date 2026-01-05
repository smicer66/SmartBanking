package com.probase.potzr.SmartBanking.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.developer.oauth.OAuth;
import com.probase.potzr.SmartBanking.contract.IPaymentCardIssuerProcessor;
import com.probase.potzr.SmartBanking.models.core.*;
import com.probase.potzr.SmartBanking.models.enums.*;
import com.probase.potzr.SmartBanking.models.mc.*;
import com.probase.potzr.SmartBanking.models.requests.CreateMCClientRequest;
import com.probase.potzr.SmartBanking.models.requests.IssueCardRequest;
import com.probase.potzr.SmartBanking.models.responses.mc.IssueCardResponse;
import com.probase.potzr.SmartBanking.repositories.core.*;
import com.probase.potzr.SmartBanking.util.MCUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.security.PrivateKey;
import com.mastercard.developer.utils.AuthenticationUtils;



@Component
public class MasterCardProcessor implements IPaymentCardIssuerProcessor {

    private static PaymentCardIssuer paymentCardIssuer = PaymentCardIssuer.MASTERCARD;
    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    private IClientRepository clientRepository;
    @Autowired
    private IAddressRepository addressRepository;
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
        return paymentCardIssuer;
    }

    @Override
    public IssueCardResponse issueCard(
            Client client,
            Collection<ClientSetting> clientSettings,
            IssueCardRequest issueCardRequest
    ) throws UnrecoverableKeyException, CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException {

        BigInteger customerId = issueCardRequest.getCustomerId();
        Customer customer = this.customerRepository.getCustomerByCustomerId(issueCardRequest.getCustomerId());

        String endppoint = null;
        Optional<ClientSetting> clientSetting = clientSettings.stream().filter(cs -> {
            return cs.getSettingName().equals(ClientSettingName.MASTERCARD_ISSUE_CARD_ENDPOINT.name());
        }).findFirst();

        IdentificationDocument identificationDocument = identificationDocumentRepository.
                getIdentifcationDocumentById(customer.getIdentificationDocumentId());



        String uri = UriComponentsBuilder
                .fromUriString(clientSetting.get().getSettingValue())
                .build()
                .toString();


//        String additionalDate01 = issueCardRequest.getRequestDate().toString();
//        String additionalDate02 = LocalDateTime.now().format(dtf);
        String additionalDate01 = "2026-01-04T03:25:50";
        String additionalDate02 = "2026-01-04T03:25:50";
        List<ClientCustomData> clientCustomDataList = new ArrayList<ClientCustomData>();
        ClientCustomData clientCustomData = new ClientCustomData();
        clientCustomData.setRemoveTag(false);
        clientCustomData.setTagContainer("ADD_INFO_01");
        clientCustomData.setTagName(customer.getFirstName() + " " + customer.getLastName());
        clientCustomData.setTagValue(issueCardRequest.getCustomerId().toString());
        clientCustomDataList.add(clientCustomData);

        ClientContactData clientContactData = new ClientContactData();
        clientContactData.setEmail(customer.getEmailAddress());
        clientContactData.setPhoneNumberMobile(customer.getMobileNumber());
        clientContactData.setPhoneNumberHome(customer.getMobileNumber());
        clientContactData.setPhoneNumberHome(customer.getMobileNumber());

        ClientCompanyData clientCompanyData = new ClientCompanyData();
        clientCompanyData.setCompanyDepartment(companyDepartment);
        clientCompanyData.setCompanyName(companyName);
        clientCompanyData.setCompanyTradeName(companyName);
        clientCompanyData.setPosition("Customer");

        ClientIdentificationData clientIdentificationData = new ClientIdentificationData();
        clientIdentificationData.setIdentificationDocumentType(identificationDocument.getIdentificationDocumentType().value);
        clientIdentificationData.setIdentificationDocumentDetails(identificationDocument.getIdentificationDocumentId().toString());
        clientIdentificationData.setIdentificationDocumentNumber(identificationDocument.getIdentificationNumber());

        ClientPersonalData clientPersonalData = new ClientPersonalData();
        clientPersonalData.setBirthDate(customer.getDateOfBirth().toString());
        clientPersonalData.setGender(customer.getGender().equals(Gender.FEMALE) ? "F" : (customer.getGender().equals(Gender.MALE) ? "M" : null));
        clientPersonalData.setCitizenship(customer.getCountryOfOriginAlfa3Code());
        clientPersonalData.setFirstName(customer.getFirstName());
        clientPersonalData.setLastName(customer.getLastName());
        clientPersonalData.setMiddleName(customer.getMiddleName());
        clientPersonalData.setBirthName(customer.getFirstName() + " " + customer.getLastName());
        clientPersonalData.setMaritalStatus(
                customer.getMaritalStatus().equals(MaritalStatus.SINGLE) ? "DS" :
                        (customer.getMaritalStatus().equals(MaritalStatus.MARRIED) ? "DM" :
                                (customer.getMaritalStatus().equals(MaritalStatus.DIVORCED) ? "DD" : "DX")));

        if(customer.getTitle()!=null)
            clientPersonalData.setTitle(customer.getTitle().name());


        EmbossedData embossedData = new EmbossedData();
        if(customer.getTitle()!=null)
            embossedData.setTitle(customer.getTitle().name());

        embossedData.setFirstName(customer.getFirstName());
        embossedData.setLastName(customer.getLastName());
        embossedData.setCompanyName(null);


        Address address = this.addressRepository.getAddressById(customer.getCurrentAddressId());
        ClientBaseAddressData clientBaseAddressData = new ClientBaseAddressData();
        BeanUtils.copyProperties(address, clientBaseAddressData);
        clientBaseAddressData.setAddressLine1(address.getAddressLine1());
        clientBaseAddressData.setCity("Lusaka");
        clientBaseAddressData.setPostalCode("1111");
        clientBaseAddressData.setState("Lusaka");
        clientBaseAddressData.setCountry("ZMB");



        CreateMCClientRequest createMCClientRequest = new CreateMCClientRequest();
        createMCClientRequest.setAdditionalDate01(additionalDate01);
        createMCClientRequest.setAdditionalDate02(additionalDate02);
        createMCClientRequest.setClientCustomData(clientCustomDataList);
        createMCClientRequest.setClientContactData(clientContactData);
        createMCClientRequest.setClientCompanyData(clientCompanyData);
        createMCClientRequest.setClientNumber(customer.getCustomerId().toString());
        createMCClientRequest.setClientType(customer.getCountryOfOrigin().equals(baseCountryOfOperation) ? "PR" : "PNR");
        createMCClientRequest.setClientExpiryDate(null);
        createMCClientRequest.setClientIdentificationData(clientIdentificationData);
        createMCClientRequest.setClientPersonalData(clientPersonalData);
        createMCClientRequest.setEmbossedData(embossedData);
        createMCClientRequest.setOrderDepartment("Smart Banking");
        createMCClientRequest.setServiceGroupCode("Smart Banking");
        createMCClientRequest.setClientBaseAddressData(clientBaseAddressData);

        IssueCardResponse issueCardResponse = new IssueCardResponse();


        MCUtil mcUtil = new MCUtil();
//        ObjectMapper objectMapper = new ObjectMapper();
//            //String ds = "{\"clientCustomData\":[{\"removeTag\":false,\"tagContainer\":\"ADD_INFO_01\",\"tagName\":\"Charles Nchimunya\",\"tagValue\":\"1\"}],\"clientNumber\":\"1\",\"clientType\":\"PNR\",\"orderDepartment\":\"Smart Banking\",\"serviceGroupCode\":\"Smart Banking\",\"additionalDate01\":\"2026-01-01T02:49\",\"additionalDate02\":\"2026-01-01T23:01:25\",\"clientBaseAddressData\":{\"addressLine1\":\"4 Lukanga Road\",\"addressLine2\":\"Roma\",\"addressLine3\":\"Lusaka\",\"city\":\"Lusaka\",\"country\":\"ZMB\",\"postalCode\":\"1111\",\"state\":\"Lusaka\"},\"clientCompanyData\":{\"companyDepartment\":\"SmartBanking\",\"companyName\":\"Probase\",\"companyTradeName\":\"Probase\",\"position\":\"Customer\"},\"clientContactData\":{\"email\":\"smicer66@gmail.com\",\"phoneNumberHome\":\"08094073705\",\"phoneNumberMobile\":\"08094073705\"},\"clientIdentificationData\":{\"identificationDocumentDetails\":\"1\",\"identificationDocumentNumber\":\"ZM-891821910AB\",\"identificationDocumentType\":\"Passport\"},\"clientPersonalData\":{\"birthDate\":\"1953-01-01\",\"birthName\":\"Charles Nchimunya\",\"citizenship\":\"ZM\",\"firstName\":\"Charles\",\"gender\":\"M\",\"lastName\":\"Nchimunya\",\"maritalStatus\":\"DS\"},\"embossedData\":{\"firstName\":\"Charles\",\"lastName\":\"Nchimunya\"}}";
//
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//            //ds = "{\"clientCustomData\": [{\"removeTag\": false,\"tagContainer\": \"ADD_INFO_01\",\"tagName\": \"TAG_01\",\"tagValue\": \"TAG_01_VALUE\"}],\"clientNumber\": \"ABC_5698521931\",\"clientType\": \"PR\",\"orderDepartment\": \"Department\",\"serviceGroupCode\": \"021\",\"additionalDate01\": \"2021-01-27T09:59:44Z\",\"additionalDate02\": \"2021-02-15T20:58:39Z\",\"clientBaseAddressData\": {\"addressLine1\": \"Mrs. Alice Smith Apartment\",\"addressLine2\": \"1c 213\",\"addressLine3\": \"Derrick Street\",\"addressLine4\": \"2nd floor\",\"city\": \"Boston\",\"country\": \"USA\",\"postalCode\": \"02130\",\"state\": \"MA\"},\"clientCompanyData\": {\"companyDepartment\": \"Department\",\"companyName\": \"Company\",\"companyTradeName\": \"Company Trade\",\"position\": \"Employee\"},\"clientContactData\": {\"email\": \"johndoe@example.com\",\"fax\": \"0048123456777\",\"faxHome\": \"0048123456888\",\"phoneNumberHome\": \"0048123456999\",\"phoneNumberMobile\": \"0048123456778\",\"phoneNumberWork\": \"0048123456789\"},\"clientIdentificationData\": {\"identificationDocumentDetails\": \"161235698529429\",\"identificationDocumentNumber\": \"161235698529328\",\"identificationDocumentType\": \"Passport\",\"socialNumber\": \"161235698529227\",\"taxPosition\": \"Tax position\",\"taxpayerIdentifier\": \"161235698529531\"},\"clientPersonalData\": {\"birthDate\": \"2021-06-25\",\"birthName\": \"Doe\",\"birthPlace\": \"Warsaw\",\"citizenship\": \"USA\",\"firstName\": \"John\",\"gender\": \"M\",\"language\": \"en\",\"lastName\": \"Doe\",\"maritalStatus\": \"DS\",\"middleName\": \"Carl\",\"secretPhrase\": \"secret\",\"shortName\": \"Madley\",\"suffix\": \"PhD\",\"title\": \"MR\"},\"clientExpiryDate\": \"2029-06-25\",\"embossedData\": {\"companyName\": \"COMPANY\",\"firstName\": \"JOHN\",\"lastName\": \"DOE\",\"title\": \"MR\"}}";
//            //createMCClientRequest = objectMapper.readValue(ds, CreateMCClientRequest.class);
//        String authSign =
//                mcUtil.sign(createMCClientRequest);
//
//        System.out.println(authSign);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authorization", authSign);
//        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//        ResponseEntity<IssueCardResponse> response =
//                restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(createMCClientRequest, httpHeaders),
//                        new ParameterizedTypeReference<IssueCardResponse>() {
//                        });
//
//        if (response.getBody() != null) {
//            issueCardResponse = response.getBody();
//        }
//        return issueCardResponse;

        mcUtil.check(createMCClientRequest);


        return new IssueCardResponse();

    }

}
