package com.probase.potzr.SmartBanking.models.core;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.probase.potzr.SmartBanking.deserializers.TimestampDeserializer;
import com.probase.potzr.SmartBanking.models.enums.UserStatus;
import com.probase.potzr.SmartBanking.serializers.JsonDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger userId;

    @Column(name="username", nullable = false)
    private String username;

    @Column(name="mobileNumber", nullable = false)
    private String mobileNumber;

    @Column(name="emailAddress", nullable = true)
    private String emailAddress;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="userStatus", nullable = false)
    private UserStatus userStatus;

    @Column(name="createdAt", nullable = false)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    private LocalDateTime createdAt;

    @Column(name="updatedAt", nullable = false)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    private LocalDateTime updatedAt;

    @Column(name="deletedAt", nullable = true)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    private LocalDateTime deletedAt;


    @PrePersist
    public void onCreate()
    {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
