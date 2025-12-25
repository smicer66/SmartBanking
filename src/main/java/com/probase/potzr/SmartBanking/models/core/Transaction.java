package com.probase.potzr.SmartBanking.models.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.probase.potzr.SmartBanking.deserializers.TimestampDeserializer;
import com.probase.potzr.SmartBanking.models.enums.Channel;
import com.probase.potzr.SmartBanking.models.enums.ServiceType;
import com.probase.potzr.SmartBanking.models.enums.SmartBankingCurrency;
import com.probase.potzr.SmartBanking.models.enums.TransactionStatus;
import com.probase.potzr.SmartBanking.serializers.JsonDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger transactionId;

    @Column(name = "transactionRef", nullable = false)
    private String transactionRef;

    @Column(name = "clientId", nullable = false)
    private BigInteger clientId;

    @Column(name = "transactionAmount", nullable = false)
    private BigDecimal transactionAmount;

    @Column(name = "channel", nullable = false)
    private Channel channel;

    @Column(name = "transactionType", nullable = false)
    private String transactionType;

    @Column(name = "bankPaymentReference")
    String bankPaymentReference;

    @Column(name = "orderRef", nullable = false)
    String orderRef;

    @Column(name = "otp", nullable = true)
    String otp;

    @Column(nullable = false)
    ServiceType serviceType;

    @Column(nullable = true)
    String transactionSourceName;

    @Column(nullable = true)
    String transactionRecipientName;

    @Column(nullable = false)
    TransactionStatus status;

    @Column(nullable = false)
    SmartBankingCurrency smartBankingCurrency;

    @Column(nullable = false)
    String messageRequest;

    @Column(nullable = true)
    String messageResponse;

    @Column(nullable = true)
    BigDecimal fixedCharge;

    @Column(nullable = true)
    BigDecimal transactionCharge;

    @Column(nullable = true)
    BigDecimal transactionPercentage;

    @Column(nullable = true)
    BigDecimal schemeTransactionCharge;

    @Column(nullable = true)
    BigDecimal schemeTransactionPercentage;

    @Column(nullable = true)
    Boolean isReversed;

    @Column(nullable = true)
    String recipientDetails;

    @Column(nullable = true)
    String summary;




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
