package com.hipermidia.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Player {
    
    private Double attack;
    private Double defense;
    private Double life;
    @SerializedName("max_itens")
    private Integer maxItens = 10;
    
    private List<Item> inventory;


    public Player(Double attack, Double defense, Double life, Integer maxItens) {
        this.attack = attack;
        this.defense = defense;
        this.life = life;
        if(maxItens != null) {
            this.maxItens = maxItens;
        }
        this.inventory = new ArrayList<>();
    }

    

    public boolean addItem(Item item) {
        if (inventory.size() < maxItens) {
            inventory.add(item);
            return true;
        }
        return false;

    }

    public boolean removeItem(Item item) {
        return inventory.remove(item);
    }

    public void useItem(String idItem) {
        
        
        Item item = this.getInventory().stream().filter(it -> it.getId().equals(idItem)).findFirst().get();
        if(item.isCanTake()){
            if(!item.isInactive()){
                if(item.getDescription().contains("vida")) {
                    Double valueLife = Double.parseDouble(item.getDescription().replaceAll("[^0-9]", ""));
                    this.life += valueLife;
                    this.getInventory().stream().filter(it -> it.getId().equals(idItem)).findFirst().get().setInactive(true);
                }else if(item.getDescription().contains("ataque")) {
                    Double valueAttack = Double.parseDouble(item.getDescription().replaceAll("[^0-9]", ""));
                    this.attack += valueAttack;
                    this.getInventory().stream().filter(it -> it.getId().equals(idItem)).findFirst().get().setInactive(true);
                }else if(item.getDescription().contains("defesa")){
                    Double valueDefense = Double.parseDouble(item.getDescription().replaceAll("[^0-9]", ""));
                    this.defense += valueDefense;
                    this.getInventory().stream().filter(it -> it.getId().equals(idItem)).findFirst().get().setInactive(true);
                } 
            }else {
                System.out.println("Este item já esta em uso");
            }
        }
    }
}