package com.probase.potzr.SmartBanking.service;


import com.probase.potzr.SmartBanking.contract.ICoreBanking;
import com.probase.potzr.SmartBanking.exceptions.ApplicationException;
import com.probase.potzr.SmartBanking.factory.CoreBankingFactory;
import com.probase.potzr.SmartBanking.factory.FundsTransferFactory;
import com.probase.potzr.SmartBanking.models.enums.CoreBankingType;
import com.probase.potzr.SmartBanking.models.enums.FundsTransferType;
import com.probase.potzr.SmartBanking.models.requests.BalanceInquiryRequest;
import com.probase.potzr.SmartBanking.models.requests.FundsTransferRequest;
import com.probase.potzr.SmartBanking.models.responses.balanceinquiry.BalanceInquiryResponse;
import com.probase.potzr.SmartBanking.models.responses.fundstransfer.FundsTransferResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoreBankingService {

    @Autowired
    private CoreBankingFactory coreBankingFactory;
    @Autowired
    private FundsTransferFactory fundsTransferFactory;


    public BalanceInquiryResponse getAccountBalanceInquiry(BalanceInquiryRequest balanceInquiryRequest) throws ApplicationException {
        CoreBankingType coreBankingType = CoreBankingType.FLEXCUBE;

        return coreBankingFactory.getAccountBalanceInquiry(coreBankingType, balanceInquiryRequest);//.getCoreBankingType().name();
    }

    public FundsTransferResponse doFundsTransfer(FundsTransferRequest fundsTransferRequest) throws ApplicationException {

        return fundsTransferFactory.doFundsTransfer(fundsTransferRequest);
    }
}
