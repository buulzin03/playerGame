package com.hipermidia.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Game {
    
    private String title;
    private String description;
    private String author;
    @SerializedName("startLocationId")
    private Integer startLocationId;
    @SerializedName("max_itens")
    private Integer maxItems;
    @SerializedName("max_turns_easy")
    private Integer maxTurnsEasy;
    @SerializedName("max_turns_normal")
    private Integer maxTurnsNormal;
    @SerializedName("max_turns_hard")
    private Integer maxTurnsHard;
    private Integer currentTurn = 1;
    private List<Location> locations;
    private Player player;

    public void increaseTurn() {
        this.currentTurn += 1;
    }
    
}
