package com.l1.tp_2.views.pokemon;

import com.l1.tp_2.entities.PokemonResponse;

public class PokemonPresenter implements PokemonContract.Presenter {

    private PokemonModel pokemonModel;
    private Integer MAX_POKEMON = 151;

    public PokemonPresenter(PokemonContract.View view) {
        this.pokemonModel = new PokemonModel(view::onSuccess, view::onError);
    }

    @Override
    public void onButtonClick() {
        int pokemonId = generatePokemonRandom();

        pokemonModel.getPokemon(pokemonId);
    }

    @Override
    public void onShake() {
        int pokemonId = generatePokemonRandom();

        pokemonModel.getPokemon(pokemonId);
    }

    @Override
    public void registerShakeEvent(PokemonResponse response, String token) {
        pokemonModel.registerAccelerometerEvent(response, token);
    }

    @Override
    public void registerPokemonEvent(PokemonResponse response, String token) {
        pokemonModel.registerPokemonEvent(response, token);
    }

    private int generatePokemonRandom() {
        return (int) (Math.random() * MAX_POKEMON) + 1;
    }
}
