package com.probase.potzr.SmartBanking.repositories.bridge;


import com.probase.potzr.SmartBanking.models.bridge.BridgeFundsTransfer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public class IBridgeFundsTransferRepository //extends JpaRepository<BridgeFundsTransfer, BigInteger>
{
//    extends CrudRepository<BridgeFundsTransfer, BigInteger>
//    @Query("Select FT FROM BridgeFundsTransfer FT WHERE FT.deletedAt IS NULL")
//    BridgeFundsTransfer getAllBridgeFundsTransfer();

    private final JdbcTemplate jdbcTemplate;

    public IBridgeFundsTransferRepository(@Qualifier("bridgeFundsTransferJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public BridgeFundsTransfer findById(Long userId) {
        String sql = "select * from BridgeFundsTransfer FT where WHERE FT.id = ?";

        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(BridgeFundsTransfer.class), userId);
    }
}
