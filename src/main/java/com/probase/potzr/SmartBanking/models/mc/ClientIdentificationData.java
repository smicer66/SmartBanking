package com.probase.potzr.SmartBanking.models.mc;


import com.probase.potzr.SmartBanking.models.enums.IdentificationDocumentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientIdentificationData {
    private String identificationDocumentDetails;
    private String identificationDocumentNumber;
    private IdentificationDocumentType identificationDocumentType;
    private String socialNumber;
    private String taxPosition;
    private String taxpayerIdentifier;


}
