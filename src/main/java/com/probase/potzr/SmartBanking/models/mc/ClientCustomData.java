package com.probase.potzr.SmartBanking.models.mc;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ClientCustomData implements Serializable {
    private boolean removeTag;
    private String tagContainer;
    private String tagName;
    private String tagValue;

}
