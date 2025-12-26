package com.probase.potzr.SmartBanking.models.requests;


import com.probase.potzr.SmartBanking.models.core.Address;
import com.probase.potzr.SmartBanking.models.core.Customer;
import com.probase.potzr.SmartBanking.models.core.IdentificationDocument;
import com.probase.potzr.SmartBanking.models.enums.CardType;
import com.probase.potzr.SmartBanking.models.enums.PaymentCardIssuer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueCardRequest {
    private Customer customer;
    private Address address;
    private IdentificationDocument identificationDocument;
    private CardType cardType;
    private PaymentCardIssuer paymentCardIssuer;
}
