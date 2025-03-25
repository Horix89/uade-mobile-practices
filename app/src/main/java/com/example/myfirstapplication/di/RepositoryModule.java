package com.example.myfirstapplication.di;

import com.example.myfirstapplication.data.repository.PokemonRetrofitRepository;
import com.example.myfirstapplication.data.repository.PokemonRepository;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {
    
    @Binds
    @Singleton
    public abstract PokemonRepository providePokemonRepository(PokemonRetrofitRepository implementation);
}
