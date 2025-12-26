package com.probase.potzr.SmartBanking.models.requests;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    private String mobileNumber;
    private String emailAddress;
    private String password;

}
