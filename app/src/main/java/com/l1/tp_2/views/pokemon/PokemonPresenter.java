package com.l1.tp_2.views.pokemon;

import com.l1.tp_2.entities.PokemonResponse;

public class PokemonPresenter implements PokemonContract.Presenter {

    private PokemonModel pokemonModel;
    private Integer MAX_POKEMON = 100;

    public PokemonPresenter(PokemonContract.View view) {
        this.pokemonModel = new PokemonModel(view::onSuccess, view::onError);
    }

    @Override
    public void onButtonClick() {
        int pokemonId = generatePokemonRandom();

        pokemonModel.getPokemon(pokemonId);
    }

    @Override
    public void registerPokemonEvent(PokemonResponse response) {

    }

    @Override
    public void registerAccelerometerEvent(PokemonResponse response) {

    }

    private int generatePokemonRandom() {
        return (int) (Math.random() * MAX_POKEMON) + 1;
    }
}
