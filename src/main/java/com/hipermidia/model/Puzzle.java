package com.hipermidia.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Puzzle {
    private String id;
    private String description;
    private Solution solution;
    private Result result;

    // Getters e Setters
}
