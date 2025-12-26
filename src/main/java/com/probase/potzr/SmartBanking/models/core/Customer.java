package com.probase.potzr.SmartBanking.models.core;

import com.probase.potzr.SmartBanking.models.enums.CustomerTitle;
import com.probase.potzr.SmartBanking.models.enums.Gender;
import com.probase.potzr.SmartBanking.models.enums.MaritalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger customerId;
    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Column(name = "middleName", nullable = true)
    private String middleName;
    @Column(name = "emailAddress", nullable = true)
    private String emailAddress;
    @Column(name = "mobileNumber", nullable = false)
    private String mobileNumber;
    @Column(name = "dateOfBirth", nullable = false)
    private Date dateOfBirth;
    @Column(name = "countryOfOrigin", nullable = false)
    private Date countryOfOrigin;
    @Column(name = "countryOfOriginAlfa3Code", nullable = false)
    private String countryOfOriginAlfa3Code;
    @Column(name = "gender", nullable = false)
    private Gender gender;
    @Column(name = "maritalStatus", nullable = false)
    private MaritalStatus maritalStatus;
    @Column(name = "title", nullable = false)
    private CustomerTitle title;
    @Column(name = "currentAddressId", nullable = true)
    private BigInteger currentAddressId;
    @Column(name = "identificationDocumentId", nullable = true)
    private BigInteger identificationDocumentId;
}
