package com.probase.potzr.SmartBanking.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.probase.potzr.SmartBanking.exceptions.ApplicationException;
import com.probase.potzr.SmartBanking.models.requests.AddBankAccountRequest;
import com.probase.potzr.SmartBanking.models.responses.account.AddBankAccountResponse;
import com.probase.potzr.SmartBanking.service.CoreBankingService;
import com.probase.potzr.SmartBanking.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    private CoreBankingService coreBankingService;

    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/add-bank-account", method = RequestMethod.POST)
    public ResponseEntity<AddBankAccountResponse> addBankAccountResponseResponse(@RequestBody AddBankAccountRequest addBankAccountRequest)
    {
        try {
            AddBankAccountResponse addBankAccountResponse = coreBankingService.
                    addBankAccountToCustomer(addBankAccountRequest);
            return ResponseEntity.ok().body(addBankAccountResponse);
        } catch (ApplicationException e) {
            throw new RuntimeException(e);
        }
    }



    @RequestMapping(value = "/confirm-add-account/{token}", method = RequestMethod.GET)
    public ResponseEntity<AddBankAccountResponse> validateToken(@PathVariable String token)
    {
        try {
            AddBankAccountResponse validateTokenResponse = coreBankingService.
                    confirmAddAccount(token);
            return ResponseEntity.ok().body(validateTokenResponse);
        } catch (ApplicationException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }



    @RequestMapping(value = "/update-customer-identification", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UpdateCustomerIdentificationResponse> updateCustomerIdentification(UpdateCustomerIdentificationRequest updateCustomerIdentificationRequest)
    {
        try {
            AddBankAccountResponse validateTokenResponse = coreBankingService.
                    confirmAddAccount(token);
            return ResponseEntity.ok().body(validateTokenResponse);
        } catch (ApplicationException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
