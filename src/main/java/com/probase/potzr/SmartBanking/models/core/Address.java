package com.probase.potzr.SmartBanking.models.core;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "addresses")
public class Address {

    @Column(name = "addressLine1", nullable = false)
    private String addressLine1;
    @Column(name = "addressLine2", nullable = true)
    private String addressLine2;
    @Column(name = "addressLine3", nullable = true)
    private String addressLine3;
    @Column(name = "addressLine4", nullable = true)
    private String addressLine4;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "country", nullable = false)
    private String country;
    @Column(name = "postalCode", nullable = true)
    private String postalCode;
    @Column(name = "state", nullable = false)
    private String state;
}
