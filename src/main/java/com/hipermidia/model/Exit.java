package com.hipermidia.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Exit {
    private String direction;
    
    @SerializedName("targetLocationId")
    private String targetLocationId;
    
    private String description;
    private boolean inactive;

    
}
