package com.probase.potzr.SmartBanking.contract;

import com.probase.potzr.SmartBanking.models.enums.FundsTransferType;
import com.probase.potzr.SmartBanking.models.requests.FundsTransferRequest;
import com.probase.potzr.SmartBanking.models.responses.fundstransfer.FundsTransferResponse;

public interface IFundsTransferClient {

    FundsTransferType getFundsTransferType();
    FundsTransferResponse doFundsTransfer(FundsTransferRequest fundsTransferRequest);
}
