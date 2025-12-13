package com.probase.potzr.SmartBanking.factory;


import com.probase.potzr.SmartBanking.models.enums.FundsTransferType;
import org.springframework.stereotype.Component;

@Component
public class FundsTransferFactory {

    private Map<FundsTransferType, IFundsTransferClient> = new HashMap<FundsTransferType, IFundsTransferClient>();

}
