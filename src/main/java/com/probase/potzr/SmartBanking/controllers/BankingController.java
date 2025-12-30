package com.probase.potzr.SmartBanking.controllers;

import com.probase.potzr.SmartBanking.exceptions.ApplicationException;
import com.probase.potzr.SmartBanking.models.requests.BalanceInquiryRequest;
import com.probase.potzr.SmartBanking.models.requests.FundsTransferRequest;
import com.probase.potzr.SmartBanking.models.responses.balanceinquiry.BalanceInquiryResponse;
import com.probase.potzr.SmartBanking.models.responses.fundstransfer.FundsTransferResponse;
import com.probase.potzr.SmartBanking.service.CoreBankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiImplicitParam;

@Controller
@RestController
@RequestMapping("/api/v1/banking")
public class BankingController {

    @Autowired
    private CoreBankingService coreBankingService;

    @RequestMapping(value = "/account-balance-inquiry", method = RequestMethod.POST)
    public ResponseEntity<BalanceInquiryResponse> getAccountBalanceInquiry(@RequestBody(required = true) BalanceInquiryRequest balanceInquiryRequest) throws ApplicationException {
        BalanceInquiryResponse balanceInquiryResponse= coreBankingService.getAccountBalanceInquiry(balanceInquiryRequest);

        return ResponseEntity.ok().body(balanceInquiryResponse);
    }


    @CrossOrigin
    @RequestMapping(value = "/funds-transfer", method = RequestMethod.POST)
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer <Token>")
    public ResponseEntity<FundsTransferResponse> doFundsTransfer(@RequestBody(required = true) FundsTransferRequest fundsTransferRequest) throws ApplicationException {
        FundsTransferResponse fundsTransferResponse= coreBankingService.doFundsTransfer(fundsTransferRequest);

        return ResponseEntity.ok().body(fundsTransferResponse);
    }
}
