package com.probase.potzr.SmartBanking.repositories.core;


import com.probase.potzr.SmartBanking.models.core.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface IUserRepository extends JpaRepository<User, BigInteger> {

    @Query("Select tp from User tp WHERE tp.deletedAt IS NULL AND tp.username = :username")
    User getUserByUsername(String username);
}
