package com.probase.potzr.SmartBanking.repositories.core;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ITokenRepository extends JpaRepository<Token, BigInteger> {

    @Query("Select tp from Token tp WHERE tp.deletedAt IS NULL AND " +
            "tp.tokenOwnedByUserId = :tokenOwnedByUserId AND " +
            "tp.usedAt IS NULL AND tp.token = :token AND " +
            "tp.expiredAt > CURRENT_TIMESTAMP")
    Token getValidToken(BigInteger tokenOwnedByUserId, String token);
}
