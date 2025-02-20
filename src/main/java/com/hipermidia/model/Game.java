package com.hipermidia.model;

import java.util.List;

public class Game {
    
    private String title;
    private String description;
    private String author;
    private Integer startLocationId;
    private Integer maxItens;
    private Integer maxTurnsEasy;
    private Integer maxTurnsNormal;
    private Integer maxTurnsHard;
    private Double attack = 10.0;
    private Double defense = 8.0;
    private Double life = 22.0;
    private List<Location> locations;
}
