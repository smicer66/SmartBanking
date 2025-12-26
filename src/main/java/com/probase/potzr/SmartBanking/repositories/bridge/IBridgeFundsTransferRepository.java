package com.probase.potzr.SmartBanking.repositories.bridge;


import com.probase.potzr.SmartBanking.mapper.BridgeFundsTransferRowMapper;
import com.probase.potzr.SmartBanking.models.bridge.BridgeFundsTransfer;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class IBridgeFundsTransferRepository //extends JpaRepository<BridgeFundsTransfer, BigInteger>
{
//    extends CrudRepository<BridgeFundsTransfer, BigInteger>
//    @Query("Select FT FROM BridgeFundsTransfer FT WHERE FT.deletedAt IS NULL")
//    BridgeFundsTransfer getAllBridgeFundsTransfer();

    private final JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(IBridgeFundsTransferRepository.class);

    private SimpleJdbcCall createBridgeFundsTransfer;

    public IBridgeFundsTransferRepository(@Qualifier("bridgeFundsTransferJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        BridgeFundsTransferRowMapper bridgeFundsTransferRowMapper = new BridgeFundsTransferRowMapper();
        this.createBridgeFundsTransfer = new SimpleJdbcCall(this.jdbcTemplate)
                .withProcedureName("CreateBridgeFundsTransfer")
                .returningResultSet("#result-set-1", bridgeFundsTransferRowMapper);

    }

    public BridgeFundsTransfer findById(Long userId) {
        String sql = "select * from bridge_funds_transfer WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(BridgeFundsTransfer.class), userId);
    }


    public BridgeFundsTransfer createBridgeFundsTransfer(BridgeFundsTransfer bridgeFundsTransfer)
    {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(bridgeFundsTransfer);
        String[] pn = sqlParameterSource.getParameterNames();
        System.out.println(sqlParameterSource.getParameterNames()[0]);
        System.out.println(bridgeFundsTransfer.getCreatedAt());
        Map<String, Object> m = this.createBridgeFundsTransfer.execute(sqlParameterSource);
        System.out.println("m... " + m) ;
//        m.keySet().stream().map(t -> {
//            System.out.println("keys12... " + t) ;
//            return t;
//        });
        System.out.println("k...." + m.get("#result-set-1"));
        List<BridgeFundsTransfer> a = (List<BridgeFundsTransfer>)m.get("#result-set-1");
        System.out.println(a) ;
        BridgeFundsTransfer bft = a.get(0);
        System.out.println("keys... " + bft) ;
        return (BridgeFundsTransfer)a.get(0);


    }
}
