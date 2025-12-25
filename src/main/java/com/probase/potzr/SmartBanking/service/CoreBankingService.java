package com.probase.potzr.SmartBanking.service;


import com.probase.potzr.SmartBanking.exceptions.ApplicationException;
import com.probase.potzr.SmartBanking.factory.CoreBankingFactory;
import com.probase.potzr.SmartBanking.factory.FundsTransferFactory;
import com.probase.potzr.SmartBanking.models.core.Client;
import com.probase.potzr.SmartBanking.models.core.ClientSetting;
import com.probase.potzr.SmartBanking.models.enums.CoreBankingType;
import com.probase.potzr.SmartBanking.models.requests.BalanceInquiryRequest;
import com.probase.potzr.SmartBanking.models.requests.FundsTransferRequest;
import com.probase.potzr.SmartBanking.models.responses.balanceinquiry.BalanceInquiryResponse;
import com.probase.potzr.SmartBanking.models.responses.fundstransfer.FundsTransferResponse;
//import com.probase.potzr.SmartBanking.repositories.core.IClientRepository;
//import com.probase.potzr.SmartBanking.repositories.core.IClientSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CoreBankingService {

    @Autowired
    private CoreBankingFactory coreBankingFactory;
    @Autowired
    private FundsTransferFactory fundsTransferFactory;

//    @Autowired
//    private IClientSettingRepository clientSettingRepository;
//    @Autowired
//    private IClientRepository clientRepository;

    public BalanceInquiryResponse getAccountBalanceInquiry(BalanceInquiryRequest balanceInquiryRequest) throws ApplicationException {
//        CoreBankingType coreBankingType = CoreBankingType.FLEXCUBE;
//
//        String bankCode = balanceInquiryRequest.getBankCode();
//        Client client = clientRepository.getClientByBankCode(bankCode);
//        Collection<ClientSetting> clientSettings = clientSettingRepository.getClientSettingByClientId(client.getClientId());
//        return coreBankingFactory.getAccountBalanceInquiry(clientSettings, coreBankingType, balanceInquiryRequest);//.getCoreBankingType().name();
        return new BalanceInquiryResponse();
    }

    public FundsTransferResponse doFundsTransfer(FundsTransferRequest fundsTransferRequest) throws ApplicationException {

//        String sourceBankCode = fundsTransferRequest.getFrombankCode();
//        Client client = clientRepository.getClientByBankCode(sourceBankCode);
//        Collection<ClientSetting> clientSettings = clientSettingRepository.getClientSettingByClientId(client.getClientId());
//        System.out.println("client settings .." + clientSettings.size());
//        return fundsTransferFactory.doFundsTransfer(client, clientSettings, fundsTransferRequest);
        return new FundsTransferResponse();
    }

}
