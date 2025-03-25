package com.example.myfirstapplication.data.repository;

import com.example.myfirstapplication.model.Pokemon;
import java.util.List;

public interface PokemonRepository {
    List<Pokemon> getAllPokemons();
    Pokemon getPokemonByName(String name);
}
