package com.hipermidia.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Result {
    private List<String> active;
    
    @SerializedName("lose_item")
    private List<Item> loseItem;
    
    @SerializedName("lose_life")
    private int loseLife;

    // Getters e Setters
}
