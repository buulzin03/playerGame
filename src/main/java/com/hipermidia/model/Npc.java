package com.hipermidia.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Npc {
    private String id;
    private String name;
    private String description;
    private boolean inactive;
    private List<Dialogue> dialogues;

    // Getters e Setters
}
