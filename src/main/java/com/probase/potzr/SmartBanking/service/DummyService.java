package com.probase.potzr.SmartBanking.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.probase.potzr.SmartBanking.exceptions.ApplicationException;
import com.probase.potzr.SmartBanking.models.bridge.BridgeFundsTransfer;
import com.probase.potzr.SmartBanking.models.core.Client;
import com.probase.potzr.SmartBanking.models.core.User;
import com.probase.potzr.SmartBanking.models.enums.Gender;
import com.probase.potzr.SmartBanking.models.enums.MaritalStatus;
import com.probase.potzr.SmartBanking.models.requests.FundsTransferRequest;
import com.probase.potzr.SmartBanking.models.responses.dummy.FlexCubeCustomerDetailCasaResponse;
import com.probase.potzr.SmartBanking.models.responses.fundstransfer.FundsTransferResponse;
import com.probase.potzr.SmartBanking.repositories.bridge.IBridgeFundsTransferRepository;
import com.probase.potzr.SmartBanking.repositories.core.IClientRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DummyService {


    @Autowired
    private IBridgeFundsTransferRepository bridgeFundsTransferRepository;

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private HttpServletRequest httpServletRequest;


    public FundsTransferResponse doFundsTransfer(FundsTransferRequest fundsTransferRequest) throws ApplicationException {

        //Client sourceClient = "001";
        Client recipientClient = clientRepository.getClientByBankCode(fundsTransferRequest.getToBankCode());
        System.out.println("recipientClient ..." + recipientClient.getClientId());

        String transactionRef = RandomStringUtils.randomAlphanumeric(16).toUpperCase();

        String sourceBankCode = fundsTransferRequest.getFrombankCode();
        Client sourceClient = clientRepository.getClientByBankCode(fundsTransferRequest.getFrombankCode());
        BridgeFundsTransfer bridgeFundsTransfer = new BridgeFundsTransfer();
        BeanUtils.copyProperties(fundsTransferRequest, bridgeFundsTransfer);
        bridgeFundsTransfer.setFundsTransferType(fundsTransferRequest.getFundsTransferType().name());
        bridgeFundsTransfer.setTransactionRef(transactionRef);
        bridgeFundsTransfer.setCreatedAt(LocalDateTime.now());
        bridgeFundsTransfer.setUpdatedAt(LocalDateTime.now());
        bridgeFundsTransfer.setUltimateBeneficiaryCode(bridgeFundsTransfer.getToBankCode());
        bridgeFundsTransfer = bridgeFundsTransferRepository.createBridgeFundsTransfer(bridgeFundsTransfer);
        System.out.println("recipientClient ..." + bridgeFundsTransfer.getBridgeFundsTransferId());


        FundsTransferResponse fundsTransferResponse = new FundsTransferResponse();
        BeanUtils.copyProperties(bridgeFundsTransfer, fundsTransferResponse);
        fundsTransferResponse.setStatus("Success");
        fundsTransferResponse.setToBranch(recipientClient.getClientName());
        fundsTransferResponse.setFromBranch(sourceClient.getClientName());
        fundsTransferResponse.setTransactionRef(bridgeFundsTransfer.getTransactionRef());



        return fundsTransferResponse;
    }

    public FlexCubeCustomerDetailCasaResponse getCustomerDetailsByCustomerNo(String custmerNumber, String bankCode) throws JsonProcessingException {
        //FlexCubeCustomerDetailCasaResponse
        Client client = clientRepository.getClientByBankCode(bankCode);
        User user = tokenService.getUserFromToken(this.httpServletRequest);

        FlexCubeCustomerDetailCasaResponse flexCubeCustomerDetailCasaResponse = new FlexCubeCustomerDetailCasaResponse();
        flexCubeCustomerDetailCasaResponse.setCUSTOMER_ID(custmerNumber);
        flexCubeCustomerDetailCasaResponse.setCUSTOMER_TYPE("I");
        flexCubeCustomerDetailCasaResponse.setADDRESS_LINE1("4 Lukanga Road");
        flexCubeCustomerDetailCasaResponse.setADDRESS_LINE2("Roma");
        flexCubeCustomerDetailCasaResponse.setADDRESS_LINE3("Lusaka");
        flexCubeCustomerDetailCasaResponse.setADDRESS_COUNTRY("Zambia");
        flexCubeCustomerDetailCasaResponse.setCATEGORY("INDIVIDUAL");
        flexCubeCustomerDetailCasaResponse.setEMAIL(user.getEmailAddress());
        flexCubeCustomerDetailCasaResponse.setGENDER(Gender.MALE.name());
        flexCubeCustomerDetailCasaResponse.setDATE_OF_BIRTH("1953-01-01");
        flexCubeCustomerDetailCasaResponse.setFIRST_NAME("Charles");
        flexCubeCustomerDetailCasaResponse.setLAST_NAME("Nchimunya");
        flexCubeCustomerDetailCasaResponse.setHOME_PHONE_NO("08094073705");
        flexCubeCustomerDetailCasaResponse.setMOBILE_NO("08094073705");
        flexCubeCustomerDetailCasaResponse.setPHONE_NO("08094073705");
        flexCubeCustomerDetailCasaResponse.setMARITAL_STATUS(MaritalStatus.SINGLE.name());
        flexCubeCustomerDetailCasaResponse.setIS_VERIFIED("Y");
        flexCubeCustomerDetailCasaResponse.setBRANCH_CODE(client.getBankCode());
        flexCubeCustomerDetailCasaResponse.setNATIONALITY("ZM");

        return flexCubeCustomerDetailCasaResponse;


    }
}
