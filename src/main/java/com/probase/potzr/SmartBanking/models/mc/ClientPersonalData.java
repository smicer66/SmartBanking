package com.probase.potzr.SmartBanking.models.mc;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ClientPersonalData implements Serializable {
    private String birthDate;
    private String birthName;
    private String birthPlace;
    private String citizenship;
    private String firstName;
    private String gender;
    private String language;
    private String lastName;
    private String maritalStatus;
    private String middleName;
    private String secretPhrase;
    private String shortName;
    private String suffix;
    private String title;
}
