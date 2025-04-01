package com.example.myfirstapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapplication.data.repository.pokemon.PokemonRepository;
import com.example.myfirstapplication.data.repository.pokemon.PokemonServiceCallBack;
import com.example.myfirstapplication.model.Pokemon;

import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PokemonDetailActivity extends AppCompatActivity {
    public static final String EXTRA_POKEMON_NAME = "pokemon_name";
    private static final String TAG = "ActivityLifecycle";
    @Inject
    PokemonRepository pokemonRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);
        Log.d(TAG, "⭐ onCreate: La Activity PokemonDetailActivity está siendo creada");
        String pokemonName = getIntent().getStringExtra(EXTRA_POKEMON_NAME);
        if (pokemonName != null) {
            loadPokemonDetails(pokemonName);
        } else {
            Toast.makeText(this, "Error: No se recibió el nombre del Pokemon", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadPokemonDetails(String pokemonName) {
        pokemonRepository.getPokemonByName(pokemonName, new PokemonServiceCallBack() {
            @Override
            public void onSuccess(List<Pokemon> pokemons) {
                if (!pokemons.isEmpty()) {
                    Pokemon pokemon = pokemons.get(0);
                    runOnUiThread(() -> new AlertDialog.Builder(PokemonDetailActivity.this)
                        .setTitle(pokemon.getName())
                        .setMessage("Tipo: " + pokemon.getType() + "\n")
                        .setPositiveButton("Marcar como favorito", (dialog, which) -> {
                            // Ejemplo: Devolver resultado a MainActivity
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("POKEMON_FAVORITE", pokemon.getName());
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        })
                        .setNegativeButton("Cancelar", (dialog, which) -> {
                            setResult(RESULT_CANCELED);
                            finish();
                        })
                        .show());
                }
            }

            @Override
            public void onError(Throwable error) {
                runOnUiThread(() -> {
                    Toast.makeText(PokemonDetailActivity.this,
                        "Error al cargar el Pokemon: " + error.getMessage(),
                        Toast.LENGTH_LONG).show();
                    finish();
                });
            }
        });
    }
}
