package com.probase.potzr.SmartBanking.repositories.core;

import com.probase.potzr.SmartBanking.models.core.ClientSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Collection;


@Repository
public interface IClientSettingRepository extends JpaRepository<ClientSetting, BigInteger> {

    @Query("SELECT u from ClientSetting u WHERE u.deletedAt IS NULL AND u.clientId = :clientId")
    Collection<ClientSetting> getClientSettingByClientId(BigInteger clientId);
}
