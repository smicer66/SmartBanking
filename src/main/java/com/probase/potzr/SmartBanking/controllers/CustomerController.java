package com.probase.potzr.SmartBanking.controllers;

import com.probase.potzr.SmartBanking.models.requests.AddBankAccountRequest;
import com.probase.potzr.SmartBanking.models.responses.account.AddBankAccountResponse;
import com.probase.potzr.SmartBanking.service.CoreBankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    private CoreBankingService coreBankingService;


    @RequestMapping(value = "/add-bank-account", method = RequestMethod.POST)
    public ResponseEntity<AddBankAccountResponse> addBankAccountResponseResponse(@RequestBody AddBankAccountRequest addBankAccountRequest)
    {

    }
}
