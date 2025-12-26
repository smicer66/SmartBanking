package com.probase.potzr.SmartBanking.repositories.core;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
@Getter
@Setter
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger tokenId;

    @Column(name = "token")
    private String token;

    @Column(name = "expiredAt")
    private LocalDateTime expiredAt;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

    @Column(name = "usedAt")
    private LocalDateTime usedAt;

    @Column(name = "tokenOwnedByUserId")
    private BigInteger tokenOwnedByUserId;
}
