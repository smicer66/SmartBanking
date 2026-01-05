package com.probase.potzr.SmartBanking.models.mc;


import com.probase.potzr.SmartBanking.models.enums.IdentificationDocumentType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ClientIdentificationData implements Serializable {
    private String identificationDocumentDetails;
    private String identificationDocumentNumber;
    private String identificationDocumentType;
    private String socialNumber;
    private String taxPosition;
    private String taxpayerIdentifier;


}
