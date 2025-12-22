package com.probase.potzr.SmartBanking.models.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.probase.potzr.SmartBanking.deserializers.TimestampDeserializer;
import com.probase.potzr.SmartBanking.serializers.JsonDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "clients")
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger clientId;

    @Column(name = "bankCode", nullable = false)
    private String bankCode;

    @Column(name = "clientName", nullable = false)
    private String clientName;

    @Column(name = "primaryAddressLine1", nullable = false)
    private String primaryAddressLine1;

    @Column(name = "primaryAddressLine2", nullable = false)
    private String primaryAddressLine2;

    @Column(name = "districtName", nullable = false)
    private String districtName;

    @Column(name = "provinceName", nullable = false)
    private String provinceName;

    @Column(name = "countryName", nullable = false)
    private String countryName;

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @Column(nullable= false)
    private LocalDateTime createdAt;

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @Column(nullable= true)
    private LocalDateTime deletedAt;

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @Column(nullable= false)
    private LocalDateTime updatedAt;



}
