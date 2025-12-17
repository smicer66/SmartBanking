package com.probase.potzr.SmartBanking.repositories.bridge;


import com.probase.potzr.SmartBanking.models.bridge.BridgeFundsTransfer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface IBridgeFundsTransferRepository extends CrudRepository<BridgeFundsTransfer, BigInteger> {

    @Query("Select FT FROM BridgeFundsTransfer WHERE deletedAt IS NULL")
    BridgeFundsTransfer getAllBridgeFundsTransfer();

}
