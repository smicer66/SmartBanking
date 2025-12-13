package com.probase.potzr.SmartBanking.contract;


import com.probase.potzr.SmartBanking.exceptions.ApplicationException;
import com.probase.potzr.SmartBanking.models.enums.CoreBankingType;
import com.probase.potzr.SmartBanking.models.requests.BalanceInquiryRequest;
import com.probase.potzr.SmartBanking.models.responses.balanceinquiry.BalanceInquiryResponse;

public interface ICoreBanking {
    CoreBankingType getCoreBankingType();

    BalanceInquiryResponse getBalanceInquiry(BalanceInquiryRequest balanceInquiryRequest) throws ApplicationException;

}
