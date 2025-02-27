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

    // public Game(String title, String description, String author, Integer startLocationId, Integer maxItems,
    //         Integer maxTurnsEasy, Integer maxTurnsNormal, Integer maxTurnsHard, Integer currentTurn, List<Location> locations,
    //         Player player) {
    //     this.title = title;
    //     this.description = description;
    //     this.author = author;
    //     this.startLocationId = startLocationId;
    //     if(maxItems != null){
    //         this.player.setMaxItens(maxItems);
    //     }
    //     if(maxTurnsEasy != null){
    //         this.maxTurnsEasy = maxTurnsEasy;
    //     }
    //     if(maxTurnsNormal != null){
    //         this.maxTurnsNormal = maxTurnsNormal;
    //     }
    //     if(maxTurnsHard != null){
    //         this.maxTurnsHard = maxTurnsHard;
    //     }
    //     this.currentTurn = 1;
    //     this.locations = locations;
    //     this.player = player;
    // }

    
    
}
