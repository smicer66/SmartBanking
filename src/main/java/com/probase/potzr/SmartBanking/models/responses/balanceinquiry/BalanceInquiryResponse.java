package com.probase.potzr.SmartBanking.models.responses.balanceinquiry;


import com.probase.potzr.SmartBanking.models.responses.dummy.DummyResponse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BalanceInquiryResponse extends DummyResponse {
    private String bankAccountNumber;
    private String currency;
    private BigDecimal accountBalance;
    private BigDecimal currentBalance;
    private String customerNumber;
    private String bankAccountName;

}
