package com.probase.potzr.SmartBanking.models.responses.dummy;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class CasaAccountBalance extends DummyResponse{

    private BigDecimal availableBalance;
    private String custNo;
    private String ccy;
    private BigDecimal openingBalance;
    private BigDecimal currentBalance;
    private BigDecimal blockedAmount;
    private String custAcNo;

    public CasaAccountBalance(BigDecimal availableBalance, String custNo, String ccy, BigDecimal openingBalance, BigDecimal currentBalance, BigDecimal blockedAmount, String custAcNo) {
        this.availableBalance = availableBalance;
        this.custNo = custNo;
        this.ccy = ccy;
        this.openingBalance = openingBalance;
        this.currentBalance = currentBalance;
        this.blockedAmount = blockedAmount;
        this.custAcNo = custAcNo;
    }
}
