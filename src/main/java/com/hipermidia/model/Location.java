package com.hipermidia.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {
    private String id;
    private String name;
    private String description;
    private List<Exit> exits;
    private List<Item> items;
    private List<Npc> npcs;
    private List<Puzzle> puzzles;
    private List<Enemy> enemies;

    public Location(Integer id, String name, String description, List<Exit> exits, List<Item> items, List<Npc> npcs, List<Puzzle> puzzles, List<Enemy> enemies) {
        this.id = id.toString();
        this.name = name;
        this.description = description;
        this.exits = exits;
        this.items = items;
        this.npcs = npcs;
        this.puzzles = puzzles;
        this.enemies = enemies;
    }

    public Integer getId() {
        return Integer.parseInt(id);
    }

    public void setId(Integer id) {
        this.id = id.toString();
    }
   
}
