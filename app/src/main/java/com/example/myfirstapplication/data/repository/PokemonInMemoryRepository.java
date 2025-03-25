package com.example.myfirstapplication.data.repository;

import com.example.myfirstapplication.model.Pokemon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PokemonInMemoryRepository implements PokemonRepository {
    private final List<Pokemon> pokemons;

    @Inject
    public PokemonInMemoryRepository() {
        pokemons = new ArrayList<>();
        initializePokemons();
    }

    private void initializePokemons() {
        pokemons.add(new Pokemon("Pikachu", "Electric", 
            Arrays.asList("Ground")));
        pokemons.add(new Pokemon("Charizard", "Fire/Flying", 
            Arrays.asList("Rock", "Electric", "Water")));
        pokemons.add(new Pokemon("Bulbasaur", "Grass/Poison", 
            Arrays.asList("Fire", "Flying", "Ice", "Psychic")));
        pokemons.add(new Pokemon("Squirtle", "Water", 
            Arrays.asList("Electric", "Grass")));
        pokemons.add(new Pokemon("Gengar", "Ghost/Poison", 
            Arrays.asList("Ghost", "Dark", "Psychic")));
        pokemons.add(new Pokemon("Dragonite", "Dragon/Flying", 
            Arrays.asList("Ice", "Rock", "Dragon")));
        pokemons.add(new Pokemon("Snorlax", "Normal", 
            Arrays.asList("Fighting")));
        pokemons.add(new Pokemon("Mewtwo", "Psychic", 
            Arrays.asList("Dark", "Ghost", "Bug")));
        pokemons.add(new Pokemon("Gyarados", "Water/Flying", 
            Arrays.asList("Electric", "Rock")));
        pokemons.add(new Pokemon("Machamp", "Fighting", 
            Arrays.asList("Flying", "Psychic", "Fairy")));
    }

    @Override
    public void getAllPokemons(PokemonServiceCallBack callback) {
        callback.onSuccess(pokemons);
    }

    @Override
    public void getPokemonByName(String name, PokemonServiceCallBack callback) {
        callback.onSuccess(pokemons);
    }
}
