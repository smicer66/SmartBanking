package com.probase.potzr.SmartBanking.repositories.core;


import com.probase.potzr.SmartBanking.models.core.IdentificationDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface IIdentificationDocumentRepository extends JpaRepository<IdentificationDocument, BigInteger> {

    @Query("Select tp from IdentificationDocument tp where tp.identificationDocumentId = :identificationDocumentId")
    public IdentificationDocument getIdentifcationDocumentById(BigInteger identificationDocumentId);
}
