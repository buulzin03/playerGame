package com.hipermidia.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    private String id;
    private String name;
    private String description;
    private Integer quantity;

    @SerializedName("can_take")
    private boolean canTake;
    
    private boolean inactive;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCanTake() {
        return canTake;
    }

    public boolean isInactive() {
        return inactive;
    }

    @Override
    public String toString() {
        return "Item [id=" + id + ", name=" + name + ", canTake=" + canTake
                + ", inactive=" + inactive + "]";
    }
    
}
