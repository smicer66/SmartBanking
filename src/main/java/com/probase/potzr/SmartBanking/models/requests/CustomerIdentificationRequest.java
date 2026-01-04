package com.probase.potzr.SmartBanking.models.requests;


import com.probase.potzr.SmartBanking.models.enums.IdentificationDocumentType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerIdentificationRequest {
    private MultipartFile identificationDocument;
    private String identificationDocumentType;
    private String identificationNumber;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private String issuingAuthority;
    private String placeOfIssue;

}
