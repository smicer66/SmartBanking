package com.probase.potzr.SmartBanking.repositories.core;


import com.probase.potzr.SmartBanking.models.core.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface IAddressRepository extends JpaRepository<Address, BigInteger> {

    @Query("Select tp from Address tp where tp.addressId = :addressId")
    public Address getAddressById(BigInteger addressId);
}
