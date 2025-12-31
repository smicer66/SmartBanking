package com.probase.potzr.SmartBanking.repositories.core;


import com.probase.potzr.SmartBanking.models.core.Address;
import com.probase.potzr.SmartBanking.models.core.BankAccount;
import com.probase.potzr.SmartBanking.models.core.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface IBankAccountRepository extends JpaRepository<BankAccount, BigInteger> {

    @Query("Select tp from BankAccount tp where tp.bankAccountId = :bankAccountId")
    public Address getBankAccountById(BigInteger bankAccountId);
}
