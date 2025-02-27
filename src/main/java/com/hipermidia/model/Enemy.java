package com.hipermidia.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Enemy {
    
    private String id;
    private int attack;
    private int defense;
    private Result result;
    @Override
    public String toString() {
        return String.format("👾 Inimigo [ID: %s] ⚔️ Ataque: %d 🛡️ Defesa: %d", 
            this.getId(), this.getAttack(), this.getDefense());
    }
    

    
}
