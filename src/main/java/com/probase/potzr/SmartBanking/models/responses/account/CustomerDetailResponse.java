package com.probase.potzr.SmartBanking.models.responses.account;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDetailResponse {
    private String mobileNumber;
    private String homePhoneNumber;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String customerCategory;
    private String customerType;
    private String email;
}
