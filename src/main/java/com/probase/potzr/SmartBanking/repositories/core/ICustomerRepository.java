package com.probase.potzr.SmartBanking.repositories.core;


import com.probase.potzr.SmartBanking.models.core.Address;
import com.probase.potzr.SmartBanking.models.core.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, BigInteger> {

    @Query("Select tp from Customer tp where tp.customerId = :customerId")
    public Address getCustomerByCustomerId(BigInteger customerId);
}
