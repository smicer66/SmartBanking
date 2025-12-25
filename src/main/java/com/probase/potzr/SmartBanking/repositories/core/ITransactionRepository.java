package com.probase.potzr.SmartBanking.repositories.core;

import com.probase.potzr.SmartBanking.models.core.Client;
import com.probase.potzr.SmartBanking.models.core.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, BigInteger> {

    @Query("Select u FROM Transaction u WHERE u.transactionRef = :transactionRef")
    Client getTransactionByTransactionRef(String transactionRef);

}
