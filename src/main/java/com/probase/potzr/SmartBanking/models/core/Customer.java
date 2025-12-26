package com.probase.potzr.SmartBanking.models.core;

import com.probase.potzr.SmartBanking.models.enums.CustomerTitle;
import com.probase.potzr.SmartBanking.models.enums.Gender;
import com.probase.potzr.SmartBanking.models.enums.MaritalStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {
    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Column(name = "middleName", nullable = true)
    private String middleName;
    @Column(name = "dateOfBirth", nullable = false)
    private Date dateOfBirth;
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
