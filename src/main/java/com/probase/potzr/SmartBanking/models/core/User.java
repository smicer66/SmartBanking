package com.probase.potzr.SmartBanking.models.core;


import com.probase.potzr.SmartBanking.models.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger userId;

    @Column(name="username")
    private String username;

    @Column(name="mobileNumber")
    private String mobileNumber;

    @Column(name="emailAddress")
    private String emailAddress;

    @Column(name="password")
    private String password;

    @Column(name="userStatus")
    private UserStatus userStatus;

    @Column(name="createdAt")
    private LocalDateTime createdAt;

    @Column(name="updatedAt")
    private LocalDateTime updatedAt;

    @Column(name="deletedAt")
    private LocalDateTime deletedAt;
}
