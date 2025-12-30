package com.probase.potzr.SmartBanking.models.responses.balanceinquiry;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BalanceInquiryCasaResponse {

    private String availableBalance;
    private String custNo;
    private String ccy;
    private String openingBalance;
    private String currentBalance;
    private String blockedAmount;
    private String custAcNo;
    private String custAcDescription;

}
