package com.probase.potzr.SmartBanking.service;


import com.probase.potzr.SmartBanking.exceptions.ApplicationException;
import com.probase.potzr.SmartBanking.models.bridge.BridgeFundsTransfer;
import com.probase.potzr.SmartBanking.models.core.Client;
import com.probase.potzr.SmartBanking.models.requests.FundsTransferRequest;
import com.probase.potzr.SmartBanking.models.responses.fundstransfer.FundsTransferResponse;
import com.probase.potzr.SmartBanking.repositories.bridge.IBridgeFundsTransferRepository;
import com.probase.potzr.SmartBanking.repositories.core.IClientRepository;
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



        return fundsTransferResponse;
    }

}
