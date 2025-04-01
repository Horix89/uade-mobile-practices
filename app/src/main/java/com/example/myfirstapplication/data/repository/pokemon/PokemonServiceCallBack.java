package com.example.myfirstapplication.data.repository.pokemon;

import com.example.myfirstapplication.model.Pokemon;

import java.util.List;

public interface PokemonServiceCallBack {
    void onSuccess(List<Pokemon> pokemons);
    void onError(Throwable error);
}
