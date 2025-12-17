package com.probase.potzr.SmartBanking.contract;


import com.probase.potzr.SmartBanking.exceptions.ApplicationException;
import com.probase.potzr.SmartBanking.models.ClientSetting;
import com.probase.potzr.SmartBanking.models.enums.CoreBankingType;
import com.probase.potzr.SmartBanking.models.requests.BalanceInquiryRequest;
import com.probase.potzr.SmartBanking.models.responses.balanceinquiry.BalanceInquiryResponse;

import java.util.Collection;

public interface ICoreBanking {
    CoreBankingType getCoreBankingType();

    BalanceInquiryResponse getBalanceInquiry(Collection<ClientSetting> clientSettings, BalanceInquiryRequest balanceInquiryRequest) throws ApplicationException;

}
