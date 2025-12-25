package com.probase.potzr.SmartBanking.mapper;

import com.probase.potzr.SmartBanking.models.bridge.BridgeFundsTransfer;
import org.jspecify.annotations.Nullable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;

public class BridgeFundsTransferRowMapper implements RowMapper {


    @Override
    public @Nullable Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        BridgeFundsTransfer bridgeFundsTransfer = new BridgeFundsTransfer();
        bridgeFundsTransfer.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
        bridgeFundsTransfer.setUpdatedAt(rs.getTimestamp("updatedAt").toLocalDateTime());
        bridgeFundsTransfer.setDeletedAt(rs.getTimestamp("deletedAt")==null ? null : rs.getTimestamp("deletedAt").toLocalDateTime());
        bridgeFundsTransfer.setBridgeFundsTransferId(BigInteger.valueOf(rs.getLong("bridgeFundsTransferId")));
        bridgeFundsTransfer.setTransferAmount(rs.getBigDecimal("transferAmount"));
        bridgeFundsTransfer.setTransactionRef(rs.getString("transactionRef"));
        bridgeFundsTransfer.setFromAccount(rs.getString("fromAccount"));
        bridgeFundsTransfer.setFrombankCode(rs.getString("frombankCode"));
        bridgeFundsTransfer.setFromBranchCode(rs.getString("fromBranchCode"));
        bridgeFundsTransfer.setFromCurrency(rs.getString("fromCurrency"));
        bridgeFundsTransfer.setToAccount(rs.getString("toAccount"));
        bridgeFundsTransfer.setToBankCode(rs.getString("toBranchCode"));
        bridgeFundsTransfer.setToBankCode(rs.getString("toBankCode"));
        bridgeFundsTransfer.setToCurrency(rs.getString("toCurrency"));
        bridgeFundsTransfer.setUltimateBeneficiaryCode(rs.getString("ultimateBeneficiaryCode"));
        bridgeFundsTransfer.setFundsTransferType(rs.getString("fundsTransferType"));
        return bridgeFundsTransfer;
    }
}
