package com.example.myfirstapplication.data.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PokemonDetailResponse {
    @SerializedName("name")
    private String name;

    @SerializedName("types")
    private List<TypeSlot> types;

    public String getName() {
        return name;
    }

    public List<TypeSlot> getTypes() {
        return types;
    }

    public static class TypeSlot {
        @SerializedName("type")
        private Type type;

        public Type getType() {
            return type;
        }
    }

    public static class Type {
        @SerializedName("name")
        private String name;

        public String getName() {
            return name;
        }
    }
}
