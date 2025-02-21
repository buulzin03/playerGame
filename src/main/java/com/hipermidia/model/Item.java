package com.hipermidia.model;

import com.google.gson.annotations.SerializedName;

public class Item {
    private Integer id;
    private String name;
    private String description;
    @SerializedName("can_take")
    private Boolean canTake = false;
    private Boolean inactive = false;
    
}
