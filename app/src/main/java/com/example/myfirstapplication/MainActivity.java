package com.example.myfirstapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapplication.data.repository.pokemon.PokemonServiceCallBack;
import com.example.myfirstapplication.model.Pokemon;
import com.example.myfirstapplication.data.repository.pokemon.PokemonRepository;
import com.example.myfirstapplication.data.repository.token.TokenRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ActivityLifecycle";

    @Inject
    PokemonRepository pokemonRepository;

    @Inject
    TokenRepository tokenRepository;

    private ListView listView;
    private List<String> pokemonDisplayList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //RetrofitBuilder

        //Apiservice.getUserById()

        super.onCreate(savedInstanceState);
        Log.d(TAG, "⭐ onCreate: La Activity está siendo creada");
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        pokemonDisplayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, 
                                   android.R.layout.simple_list_item_1,
                                   pokemonDisplayList);
        listView.setAdapter(adapter);
        loadPokemons();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedPokemon = pokemonDisplayList.get(position);
            String pokemonName = selectedPokemon.split(" - ")[0];

            pokemonRepository.getPokemonByName(pokemonName, new PokemonServiceCallBack() {
                @Override
                public void onSuccess(List<Pokemon> pokemons) {
                    if (!pokemons.isEmpty()) {
                        Pokemon pokemon = pokemons.get(0);
                        runOnUiThread(() -> new AlertDialog.Builder(MainActivity.this)
                            .setTitle(pokemon.getName())
                            .setMessage("Tipo: " + pokemon.getType() + "\n")
                            .setPositiveButton("OK", null)
                            .show());
                    }
                }

                @Override
                public void onError(Throwable error) {
                    runOnUiThread(() -> Toast.makeText(MainActivity.this,
                        "Error al cargar el Pokemon: " + error.getMessage(),
                        Toast.LENGTH_LONG).show());
                }
            });
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "⭐ onStart: La Activity está a punto de hacerse visible");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "⭐ onResume: La Activity es visible y tiene el foco");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "⭐ onPause: La Activity está perdiendo el foco");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "⭐ onStop: La Activity ya no es visible");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "⭐ onRestart: La Activity está volviendo a empezar después de detenerse");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "⭐ onDestroy: La Activity está siendo destruida");
    }

    private void loadPokemons() {
        pokemonRepository.getAllPokemons(new PokemonServiceCallBack() {
            @Override
            public void onSuccess(List<Pokemon> pokemons) {
                pokemonDisplayList.clear();
                pokemonDisplayList.addAll(pokemons.stream()
                    .map(pokemon -> pokemon.getName() + " - " + pokemon.getType())
                    .collect(Collectors.toList()));
                runOnUiThread(() -> adapter.notifyDataSetChanged());
            }

            @Override
            public void onError(Throwable error) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this,
                    "Error al cargar los Pokemon: " + error.getMessage(),
                    Toast.LENGTH_LONG).show());
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "⭐ onSaveInstanceState: Guardando el estado de la Activity");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "⭐ onRestoreInstanceState: Restaurando el estado guardado de la Activity");
    }
}