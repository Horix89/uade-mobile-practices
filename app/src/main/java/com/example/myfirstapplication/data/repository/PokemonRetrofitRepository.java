package com.example.myfirstapplication.data.repository;

import com.example.myfirstapplication.data.api.PokeApiService;
import com.example.myfirstapplication.data.api.model.PokemonDetailResponse;
import com.example.myfirstapplication.data.api.model.PokemonListResponse;
import com.example.myfirstapplication.model.Pokemon;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class PokemonRetrofitRepository implements PokemonRepository {
    private final PokeApiService pokeApiService;

    @Inject
    public PokemonRetrofitRepository(PokeApiService pokeApiService) {
        this.pokeApiService = pokeApiService;
    }

    @Override
    public void getAllPokemons(final PokemonServiceCallBack callback) {
        pokeApiService.getPokemonList(20, 0).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PokemonListResponse.PokemonListItem> results = response.body().getResults();
                    List<Pokemon> pokemons = new ArrayList<>();

                    for (PokemonListResponse.PokemonListItem item : results) {
                        String id = extractPokemonId(item.getUrl());
                        pokemons.add(new Pokemon(
                                item.getName(),
                                "#" + id,
                                new ArrayList<>()
                        ));
                    }
                    callback.onSuccess(pokemons);
                } else {
                    callback.onError(new Exception("Error fetching Pokemon list"));
                }
            }

            @Override
            public void onFailure(Call<PokemonListResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    @Override
    public void getPokemonByName(String name, final PokemonServiceCallBack callback) {
        pokeApiService.getPokemonDetail(name.toLowerCase()).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<PokemonDetailResponse> call, Response<PokemonDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PokemonDetailResponse pokemon = response.body();
                    String type = pokemon.getTypes().get(0).getType().getName();
                    List<Pokemon> result = new ArrayList<>();
                    result.add(new Pokemon(
                            pokemon.getName(),
                            type,
                            new ArrayList<>()
                    ));
                    callback.onSuccess(result);
                } else {
                    callback.onError(new Exception("Pokemon not found"));
                }
            }

            @Override
            public void onFailure(Call<PokemonDetailResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    private String extractPokemonId(String url) {
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }
}
