package com.probase.potzr.SmartBanking.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.probase.potzr.SmartBanking.exceptions.ApplicationException;
import com.probase.potzr.SmartBanking.models.requests.FundsTransferRequest;
import com.probase.potzr.SmartBanking.models.responses.dummy.CasaAccountBalance;
import com.probase.potzr.SmartBanking.models.responses.dummy.FlexCubeCustomerDetailCasaResponse;
import com.probase.potzr.SmartBanking.models.responses.fundstransfer.FundsTransferResponse;
import com.probase.potzr.SmartBanking.service.DummyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Random;

@Controller
@RestController
@RequestMapping("/test")
public class DummyController {

    @Value("${casa.account.customerNo}")
    private String customerNo;
    @Value("${casa.account.ccy}")
    private String casaAccountCCY;
    @Value("${casa.account.openingBalance}")
    private BigDecimal openingBalance;
    @Value("${casa.account.accountNo}")
    private String custAcNo;
    //http://localhost:8081/AccountService/v12.3/accounts/casaBalance/

    @Autowired
    private DummyService dDummyService;



//    http://localhost:8080/test/AccountService/v12.3/accounts/casaBalance/8933000121
    @RequestMapping("/AccountService/v12.3/accounts/casaBalance/{accountNumber}")
    public ResponseEntity<CasaAccountBalance> getCasaAccountBalance(@PathVariable(required = true) String accountNumber)
    {
        Random r = new Random();
        float maxValue = (float)100000.00;
        float minValue = (float)1.00;
        BigDecimal blockedAmount = BigDecimal.valueOf((float)0.00);
        BigDecimal randomBalance =  BigDecimal.valueOf((float)maxValue + (maxValue - minValue) * r.nextFloat());

        return
                ResponseEntity.ok().body(
                    new CasaAccountBalance("John Doe", randomBalance, customerNo, casaAccountCCY, openingBalance, randomBalance, blockedAmount, custAcNo)
                );

    }


    @RequestMapping("/FCLiteWeb/FundTransferService/createContract")
    public ResponseEntity<FundsTransferResponse> doFundsTransfer(@RequestBody(required = true)FundsTransferRequest fundsTransferRequest) throws ApplicationException {





        return
                ResponseEntity.ok().body(
                        dDummyService.doFundsTransfer(fundsTransferRequest)
                );

    }

    @RequestMapping("/FCLiteWeb/Customer/get/{customerNumber}/{bankCode}")
    public ResponseEntity<FlexCubeCustomerDetailCasaResponse> getCustomerDetailsByCustomerNo(
            @PathVariable(required = true) String customerNumber,
            @PathVariable(required = true) String bankCode
     ) throws JsonProcessingException {
        return
            ResponseEntity.ok().body(
                    dDummyService.getCustomerDetailsByCustomerNo(customerNumber, bankCode)
            );
    }
}
