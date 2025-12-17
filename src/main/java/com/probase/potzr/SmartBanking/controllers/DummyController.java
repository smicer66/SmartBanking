package com.probase.potzr.SmartBanking.controllers;

import com.probase.potzr.SmartBanking.models.bridge.BridgeFundsTransfer;
import com.probase.potzr.SmartBanking.models.Client;
import com.probase.potzr.SmartBanking.models.requests.FundsTransferRequest;
import com.probase.potzr.SmartBanking.models.responses.dummy.CasaAccountBalance;
import com.probase.potzr.SmartBanking.models.responses.fundstransfer.FundsTransferResponse;
import com.probase.potzr.SmartBanking.repositories.IClientRepository;
import com.probase.potzr.SmartBanking.repositories.bridge.IBridgeFundsTransferRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
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
    private IClientRepository clientRepository;

    @Autowired
    private IBridgeFundsTransferRepository bridgeFundsTransferRepository;


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
                    new CasaAccountBalance(randomBalance, customerNo, casaAccountCCY, openingBalance, randomBalance, blockedAmount, custAcNo)
                );

    }


    @RequestMapping("/FCLiteWeb/FundTransferService/createContract")
    public ResponseEntity<FundsTransferResponse> doFundsTransfer(@RequestBody(required = true)FundsTransferRequest fundsTransferRequest)
    {
        Client sourceClient = clientRepository.getClientByBankCode(fundsTransferRequest.getFrombankCode());
        Client recipientClient = clientRepository.getClientByBankCode(fundsTransferRequest.getToBankCode());
        String transactionRef = RandomStringUtils.randomAlphanumeric(16).toUpperCase();


        BridgeFundsTransfer bridgeFundsTransfer = new BridgeFundsTransfer();
        BeanUtils.copyProperties(fundsTransferRequest, bridgeFundsTransfer);
        bridgeFundsTransfer.setFundsTransferType(fundsTransferRequest.getFundsTransferType().name());
        bridgeFundsTransfer.setTransactionRef(transactionRef);
        bridgeFundsTransferRepository.save(bridgeFundsTransfer);

        FundsTransferResponse fundsTransferResponse = new FundsTransferResponse();
        BeanUtils.copyProperties(fundsTransferRequest, fundsTransferResponse);
        fundsTransferResponse.setStatus("Success");
        fundsTransferResponse.setFromBranch(sourceClient.getClientName());
        fundsTransferResponse.setToBranch(recipientClient.getClientName());

        return
                ResponseEntity.ok().body(
                        fundsTransferResponse
                );

    }
}
