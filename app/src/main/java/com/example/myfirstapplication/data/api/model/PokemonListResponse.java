package com.example.myfirstapplication.data.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PokemonListResponse {
    @SerializedName("count")
    private int count;

    @SerializedName("results")
    private List<PokemonListItem> results;

    public List<PokemonListItem> getResults() {
        return results;
    }

    public static class PokemonListItem {
        @SerializedName("name")
        private String name;

        @SerializedName("url")
        private String url;

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }
}
