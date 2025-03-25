package com.example.myfirstapplication;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapplication.data.repository.PokemonInMemoryRepository;
import com.example.myfirstapplication.model.Pokemon;
import com.example.myfirstapplication.data.repository.PokemonRepository;
import com.example.myfirstapplication.services.SimpleContadorService;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ActivityLifecycle";

    @Inject
    PokemonRepository pokemonRepository;

    private ListView listView;
    private List<String> pokemonDisplayList;
    private ArrayAdapter<String> adapter;
    private TextView txtContador;
    private SimpleContadorService contadorService;
    private boolean servicioVinculado = false;

    private ServiceConnection conexion = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SimpleContadorService.ContadorBinder binder = (SimpleContadorService.ContadorBinder) service;
            contadorService = binder.getService();
            servicioVinculado = true;
            actualizarContador();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            servicioVinculado = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "⭐ onCreate: La Activity está siendo creada");
        setContentView(R.layout.activity_main);

        // Inicializar el contador
        txtContador = findViewById(R.id.txtContador);

        // Vincular el servicio
        Intent intent = new Intent(this, SimpleContadorService.class);
        bindService(intent, conexion, Context.BIND_AUTO_CREATE);

        listView = findViewById(R.id.listView);

        List<Pokemon> pokemons = pokemonRepository.getAllPokemons();
        pokemonDisplayList = pokemons.stream()
            .map(pokemon -> pokemon.getName() + " - " + pokemon.getType())
            .collect(Collectors.toList());

        adapter = new ArrayAdapter<>(this, 
                                   android.R.layout.simple_list_item_1,
                                   pokemonDisplayList);

        listView.setAdapter(adapter);



        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedPokemon = pokemonDisplayList.get(position);
            String pokemonName = selectedPokemon.split(" - ")[0];

            Pokemon pokemon = pokemonRepository.getPokemonByName(pokemonName);
            
            new AlertDialog.Builder(this)
                .setTitle(pokemon.getName())
                .setMessage("Tipo: " + pokemon.getType() + "\n" +
                          "Debilidades: " + pokemon.getWeaknessesAsString())
                .setPositiveButton("OK", null)
                .show();
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
        if (servicioVinculado) {
            unbindService(conexion);
            servicioVinculado = false;
        }
        Log.d(TAG, "⭐ onDestroy: La Activity está siendo destruida");
    }

    private void actualizarContador() {
        if (servicioVinculado) {
            contadorService.incrementarContador();
            txtContador.setText("Contador: " + contadorService.getContadorActual());
            txtContador.postDelayed(this::actualizarContador, 1000);
        }
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