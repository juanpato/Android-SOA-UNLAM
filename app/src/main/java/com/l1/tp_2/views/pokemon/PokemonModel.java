package com.l1.tp_2.views.pokemon;

import com.l1.tp_2.clients.PokemonClient;
import com.l1.tp_2.entities.PokemonResponse;
import com.l1.tp_2.utils.Run;

import java.util.function.Consumer;

public class PokemonModel implements PokemonContract.Model {

    private PokemonClient pokemonClient;

    public PokemonModel(Consumer<PokemonResponse> onSuccess, Run onFailure) {
        this.pokemonClient = new PokemonClient(onSuccess, onFailure);
    }

    @Override
    public void getPokemon(Integer id) {
        pokemonClient.getPokemon(id);
    }

    @Override
    public void registerPokemonEvent(PokemonResponse response) {

    }

    @Override
    public void registerAccelerometerEvent(PokemonResponse response) {

    }
}
