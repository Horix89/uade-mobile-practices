package com.example.myfirstapplication.data.repository.pokemon;

import com.example.myfirstapplication.model.Pokemon;
import java.util.List;

public interface PokemonRepository {
    void getAllPokemons(PokemonServiceCallBack callback);
    void getPokemonByName(String name, PokemonServiceCallBack callback);
}
