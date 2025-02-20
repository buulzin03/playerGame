package com.hipermidia.model;

import java.util.List;

public class Location {
    private Integer id;
    private String name;
    private String description;
    private List<Exit> exits;
    private List<Item> items;
    private List<Npc> npcs;
    private List<Puzzle> puzzles;
    private List<Enemy> enemies;
}
