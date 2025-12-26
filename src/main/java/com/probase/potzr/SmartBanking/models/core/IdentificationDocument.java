package com.probase.potzr.SmartBanking.models.core;

import com.probase.potzr.SmartBanking.models.enums.IdentificationDocumentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@Entity
@Table(name = "identification_documents")
public class IdentificationDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger identificationDocumentId;
    @Column(name = "identificationNumber", nullable = false)
    private String identificationNumber;
    @Column(name = "issueDate", nullable = false)
    private String issueDate;
    @Column(name = "identificationDocumentType", nullable = false)
    private IdentificationDocumentType identificationDocumentType;
    @Column(name = "expirationDate", nullable = false)
    private String expirationDate;
    @Column(name = "issuingAuthority", nullable = false)
    private String issuingAuthority;
    @Column(name = "placeOfIssue", nullable = false)
    private String placeOfIssue;
}
