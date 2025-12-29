package com.probase.potzr.SmartBanking.models.responses.user;


import com.probase.potzr.SmartBanking.models.core.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String username;
    private String token;
    private String message;
}
