package com.probase.potzr.SmartBanking.models.responses.balanceinquiry;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BalanceInquiryResponse {
    private String bankAccountNumber;
    private String currency;
    private BigDecimal accountBalance;
    private BigDecimal currentBalance;

}
