package com.probase.potzr.SmartBanking.repositories.bridge;


import com.probase.potzr.SmartBanking.models.bridge.BridgeFundsTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface IBridgeFundsTransferRepository extends JpaRepository<BridgeFundsTransfer, BigInteger> {
//    extends CrudRepository<BridgeFundsTransfer, BigInteger>
    @Query("Select FT FROM BridgeFundsTransfer FT WHERE FT.deletedAt IS NULL")
    BridgeFundsTransfer getAllBridgeFundsTransfer();

}
