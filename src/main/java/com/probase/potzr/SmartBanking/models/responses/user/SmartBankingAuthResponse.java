package com.probase.potzr.SmartBanking.models.responses.user;


import com.probase.potzr.SmartBanking.models.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SmartBankingAuthResponse {
    private String subject;
    private Integer status;
    private String token;
    private UserRole role;
//    private List<UserRolePermission> permissionList;
//    private List<AuthMerchantData> merchantList;
    private String message;
}
