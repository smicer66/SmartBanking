package com.probase.potzr.SmartBanking.models.mc;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class EmbossedData implements Serializable {

    private String companyName;
    private String firstName;
    private String lastName;
    private String title;
}
