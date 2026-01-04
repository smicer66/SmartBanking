package com.probase.potzr.SmartBanking.controllers;


import com.mastercard.developer.mdes_digital_enablement_client.ApiException;
import com.probase.potzr.SmartBanking.models.requests.IssueCardRequest;
import com.probase.potzr.SmartBanking.models.responses.mc.IssueCardResponse;
import com.probase.potzr.SmartBanking.service.PaymentCardIssuerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@Controller
@RestController
@RequestMapping("/api/v1/card")
public class CardController {


    @Autowired
    private PaymentCardIssuerService cardService;

    @RequestMapping(value = "/issue-card", method = RequestMethod.POST)
    public ResponseEntity<IssueCardResponse> postIssueCard(@RequestBody IssueCardRequest issueCardRequest) throws UnrecoverableKeyException, CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, ApiException {
        IssueCardResponse issueCardResponse = cardService.issueCard(issueCardRequest);
        return ResponseEntity.ok().body(issueCardResponse);
    }

}
