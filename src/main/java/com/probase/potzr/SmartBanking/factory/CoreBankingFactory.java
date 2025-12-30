package com.probase.potzr.SmartBanking.factory;

import com.probase.potzr.SmartBanking.contract.ICoreBanking;
import com.probase.potzr.SmartBanking.exceptions.ApplicationException;
import com.probase.potzr.SmartBanking.models.core.Client;
import com.probase.potzr.SmartBanking.models.core.ClientSetting;
import com.probase.potzr.SmartBanking.models.enums.CoreBankingType;
import com.probase.potzr.SmartBanking.models.requests.AddBankAccountRequest;
import com.probase.potzr.SmartBanking.models.requests.BalanceInquiryRequest;
import com.probase.potzr.SmartBanking.models.responses.account.AddBankAccountResponse;
import com.probase.potzr.SmartBanking.models.responses.balanceinquiry.BalanceInquiryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

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


    public BalanceInquiryResponse getAccountBalanceInquiry(Collection<ClientSetting> clientSettings, CoreBankingType coreBankingType, BalanceInquiryRequest balanceInquiryRequest ) throws ApplicationException {
        ICoreBanking iCoreBanking =  this.coreBankingMap.get(coreBankingType);
        return iCoreBanking.getBalanceInquiry(clientSettings, balanceInquiryRequest);
    }

    public List<CoreBankingType> getCoreBankingTypes() {
        return new ArrayList<>(coreBankingMap.keySet());
    }

    public AddBankAccountResponse getCustomerDetailByAccountNo(Client client, Collection<ClientSetting> clientSettings, CoreBankingType coreBankingType, AddBankAccountRequest addBankAccountRequest) {
        ICoreBanking iCoreBanking =  this.coreBankingMap.get(coreBankingType);
        return iCoreBanking.getCustomerDetailByAccountNo(client, clientSettings, addBankAccountRequest);
    }
}
