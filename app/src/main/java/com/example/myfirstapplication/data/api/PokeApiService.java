package com.example.myfirstapplication.data.api;

import com.example.myfirstapplication.data.api.model.PokemonDetailResponse;
import com.example.myfirstapplication.data.api.model.PokemonListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeApiService {
    @GET("pokemon")
    Call<PokemonListResponse> getPokemonList(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{id}")
    Call<PokemonDetailResponse> getPokemonDetail(@Path("id") String id);
}
