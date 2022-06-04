package com.l1.tp_2.entities;

import com.l1.tp_2.entities.sprites.Sprites;

public class PokemonResponse {
    private String name;
    private Integer height;
    private Integer weight;
    private Sprites sprites;

    public String getName() {
        return name;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWeight() {
        return weight;
    }

    public Sprites getSprites() {
        return sprites;
    }
}
