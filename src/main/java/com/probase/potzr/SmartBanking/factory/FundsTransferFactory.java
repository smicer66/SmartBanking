package com.probase.potzr.SmartBanking.factory;


import com.probase.potzr.SmartBanking.contract.IFundsTransferClient;
import com.probase.potzr.SmartBanking.models.enums.FundsTransferType;
import com.probase.potzr.SmartBanking.models.requests.FundsTransferRequest;
import com.probase.potzr.SmartBanking.models.responses.fundstransfer.FundsTransferResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FundsTransferFactory {

    private Map<FundsTransferType, IFundsTransferClient> fundsTransferClientMap = new HashMap<FundsTransferType, IFundsTransferClient>();


    @Autowired
    public FundsTransferFactory(List<IFundsTransferClient> fundsTransferClientList)
    {
        fundsTransferClientList.forEach(ftc -> {
            fundsTransferClientMap.put(ftc.getFundsTransferType(), ftc);
        });
    }

    public FundsTransferResponse doFundsTransfer(FundsTransferRequest fundsTransferRequest) {
        FundsTransferType fundsTransferType = fundsTransferRequest.getFundsTransferType();
        IFundsTransferClient iFundsTransferClient = fundsTransferClientMap.get(fundsTransferType);

        return iFundsTransferClient.doFundsTransfer(fundsTransferRequest);
    }
}
