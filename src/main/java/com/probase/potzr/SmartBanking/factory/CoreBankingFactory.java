package com.probase.potzr.SmartBanking.factory;

import com.probase.potzr.SmartBanking.contract.ICoreBanking;
import com.probase.potzr.SmartBanking.contract.IFundsTransferClient;
import com.probase.potzr.SmartBanking.exceptions.ApplicationException;
import com.probase.potzr.SmartBanking.models.enums.CoreBankingType;
import com.probase.potzr.SmartBanking.models.requests.BalanceInquiryRequest;
import com.probase.potzr.SmartBanking.models.requests.FundsTransferRequest;
import com.probase.potzr.SmartBanking.models.responses.balanceinquiry.BalanceInquiryResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CoreBankingFactory {


    private Map<CoreBankingType, ICoreBanking> coreBankingMap = new HashMap<>();

    @Autowired
    public CoreBankingFactory(List<ICoreBanking> iCoreBankingList)
    {
        System.out.println("size = " + iCoreBankingList.size());
        iCoreBankingList.forEach(icb -> {
            this.coreBankingMap.put(icb.getCoreBankingType(), icb);
        });
    }


    public BalanceInquiryResponse getAccountBalanceInquiry(CoreBankingType coreBankingType, BalanceInquiryRequest balanceInquiryRequest ) throws ApplicationException {
        ICoreBanking iCoreBanking =  this.coreBankingMap.get(coreBankingType);
        return iCoreBanking.getBalanceInquiry(balanceInquiryRequest);
    }

    public List<CoreBankingType> getCoreBankingTypes() {
        return new ArrayList<>(coreBankingMap.keySet());
    }

}
