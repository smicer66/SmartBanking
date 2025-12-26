package com.probase.potzr.SmartBanking.models.requests;

import com.probase.potzr.SmartBanking.models.mc.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class CreateMCClientRequest {

    private List<ClientCustomData> clientCustomData;
    private String clientNumber;
    private String clientType;
    private String orderDepartment;
    private String serviceGroupCode;
    private String additionalDate01;
    private String additionalDate02;
    private ClientBaseAddressData clientBaseAddressData;
    private ClientCompanyData clientCompanyData;
    private ClientContactData clientContactData;
    private ClientIdentificationData clientIdentificationData;
    private ClientPersonalData clientPersonalData ;
    private String clientExpiryDate;
    private EmbossedData embossedData;

}
