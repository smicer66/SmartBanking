package com.probase.potzr.SmartBanking.contract;

import com.probase.potzr.SmartBanking.exceptions.ApplicationException;
import com.probase.potzr.SmartBanking.models.core.Client;
import com.probase.potzr.SmartBanking.models.core.ClientSetting;
import com.probase.potzr.SmartBanking.models.enums.FundsTransferType;
import com.probase.potzr.SmartBanking.models.requests.FundsTransferRequest;
import com.probase.potzr.SmartBanking.models.responses.fundstransfer.FundsTransferResponse;

import java.util.Collection;

public interface IFundsTransferClient {

    FundsTransferType getFundsTransferType();
    FundsTransferResponse doFundsTransfer(Client client,
          Collection<ClientSetting> clientSettings, FundsTransferRequest fundsTransferRequest) throws ApplicationException;
}
