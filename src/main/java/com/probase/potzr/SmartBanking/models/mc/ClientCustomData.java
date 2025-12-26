package com.probase.potzr.SmartBanking.models.mc;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientCustomData {
    private boolean removeTag;
    private String tagContainer;
    private String tagName;
    private String tagValue;

}
