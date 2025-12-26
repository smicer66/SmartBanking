package com.probase.potzr.SmartBanking.models.core;

import com.probase.potzr.SmartBanking.models.enums.IdentificationDocumentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdentificationDocument {

    private String identificationNumber;
    private String issueDate;
    private IdentificationDocumentType identificationDocumentType;
    private String expirationDate;
    private String issuingAuthority;
    private String placeOfIssue;
}
