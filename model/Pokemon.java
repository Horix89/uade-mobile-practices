package com.example.myfirstapplication.model;

import java.util.List;

public class Pokemon {
    private String name;
    private String type;
    private String typea;

    private String typeb;
    private List<String> weaknesses;

    public Pokemon(String name, String type, List<String> weaknesses) {
        this.name = name;
        this.type = type;
        this.weaknesses = weaknesses;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<String> getWeaknesses() {
        return weaknesses;
    }

    public String getWeaknessesAsString() {
        return String.join(", ", weaknesses);
    }
}
