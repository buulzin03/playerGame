package com.hipermidia.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Game {
    
    private String title;
    private String description;
    private String author;
    private Integer startLocationId;
    @SerializedName("max_itens")
    private Integer maxItens;
    @SerializedName("max_turns_easy")
    private Integer maxTurnsEasy;
    @SerializedName("max_turns_normal")
    private Integer maxTurnsNormal;
    @SerializedName("max_turns_hard")
    private Integer maxTurnsHard;
    private Player player;
    private List<Location> locations;




}
