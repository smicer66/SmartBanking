package com.probase.potzr.SmartBanking.models.mc;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ClientCompanyData implements Serializable {
    private String companyDepartment;
    private String companyName;
    private String companyTradeName;
    private String position;

}
