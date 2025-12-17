package com.probase.potzr.SmartBanking.models.bridge;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.probase.potzr.SmartBanking.deserializers.TimestampDeserializer;
import com.probase.potzr.SmartBanking.serializers.JsonDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "bridge_funds_transfer")
public class BridgeFundsTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger bridgeFundsTransferId;

    @Column(name = "fromAccount", nullable = false)
    private String fromAccount;

    @Column(name = "frombankCode", nullable = false)
    private String frombankCode;

    @Column(name = "fromBranchCode", nullable = false)
    private String fromBranchCode;

    @Column(name = "fromCurrency", nullable = false)
    private String fromCurrency;

    @Column(name = "toAccount", nullable = false)
    private String toAccount;

    @Column(name = "transactionRef", nullable = false)
    private String transactionRef;

    @Column(name = "toBranchCode", nullable = false)
    private String toBranchCode;

    @Column(name = "toBankCode", nullable = false)
    private String toBankCode;

    @Column(name = "toCurrency", nullable = false)
    private String toCurrency;

    @Column(name = "transferAmount", nullable = false)
    private BigDecimal transferAmount;

    @Column(name = "ultimateBeneficiaryCode", nullable = false)
    private String ultimateBeneficiaryCode;

    @Column(name = "fundsTransferType", nullable = false)
    private String fundsTransferType;

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
