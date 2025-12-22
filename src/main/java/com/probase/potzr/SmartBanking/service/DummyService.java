package com.probase.potzr.SmartBanking.service;


import com.probase.potzr.SmartBanking.exceptions.ApplicationException;
import com.probase.potzr.SmartBanking.models.bridge.BridgeFundsTransfer;
import com.probase.potzr.SmartBanking.models.requests.FundsTransferRequest;
import com.probase.potzr.SmartBanking.models.responses.fundstransfer.FundsTransferResponse;
import com.probase.potzr.SmartBanking.repositories.bridge.IBridgeFundsTransferRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DummyService {

//
//    @Autowired
//    private IBridgeFundsTransferRepository bridgeFundsTransferRepository;
//
//    public FundsTransferResponse doFundsTransfer(FundsTransferRequest fundsTransferRequest) throws ApplicationException {
//
//        //Client sourceClient = "001";
////        Client recipientClient = clientRepository.getClientByBankCode(fundsTransferRequest.getToBankCode());
//        String transactionRef = RandomStringUtils.randomAlphanumeric(16).toUpperCase();
//
//        String sourceBankCode = fundsTransferRequest.getFrombankCode();
//        BridgeFundsTransfer bridgeFundsTransfer = new BridgeFundsTransfer();
//        BeanUtils.copyProperties(fundsTransferRequest, bridgeFundsTransfer);
//        bridgeFundsTransfer.setFundsTransferType(fundsTransferRequest.getFundsTransferType().name());
//        bridgeFundsTransfer.setTransactionRef(transactionRef);
//        bridgeFundsTransfer.setCreatedAt(LocalDateTime.now());
//        bridgeFundsTransfer.setUpdatedAt(LocalDateTime.now());
//        bridgeFundsTransferRepository.save(bridgeFundsTransfer);
//
//        FundsTransferResponse fundsTransferResponse = new FundsTransferResponse();
//        BeanUtils.copyProperties(fundsTransferRequest, fundsTransferResponse);
//        fundsTransferResponse.setStatus("Success");
//        fundsTransferResponse.setFromBranch("ZICB");
//        fundsTransferResponse.setToBranch("ZICB");
//
//
//        return fundsTransferResponse;
//    }

}
