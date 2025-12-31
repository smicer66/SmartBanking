package com.probase.potzr.SmartBanking.repositories.core;


import com.probase.potzr.SmartBanking.models.core.SMS;
import com.probase.potzr.SmartBanking.models.core.Token;
import com.probase.potzr.SmartBanking.models.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ISMSRepository extends JpaRepository<SMS, BigInteger> {

}
