package com.probase.potzr.SmartBanking.contract;

public interface IFundsTransferClient {

    FundsTransferResponse doFundsTransfer(FundsTransferRequest fundsTransferRequest);
}
