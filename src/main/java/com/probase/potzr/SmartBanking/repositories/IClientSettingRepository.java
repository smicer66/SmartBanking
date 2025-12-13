package com.probase.potzr.SmartBanking.repositories;

import com.probase.potzr.SmartBanking.models.ClientSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;


@Repository
public interface IClientSettingRepository extends JpaRepository<ClientSetting, BigInteger> {

    @Query("SELECT u from ClientSetting u WHERE u.deletedAt IS NULL AND u.clientId = :clientId")
    Collection<ClientSetting> getClientSettingByClientId(BigInteger clientId);
}
