package com.l1.tp_2.views.pokemon;

import com.l1.tp_2.entities.PokemonResponse;

public interface PokemonContract {

    interface View {
        void onSuccess(PokemonResponse pokemonResponse);

        void onError();
    }

    interface Model {
        void getPokemon(Integer id);

        void registerPokemonEvent(PokemonResponse response);
        void registerAccelerometerEvent(PokemonResponse response);
    }

    interface Presenter {
        void onButtonClick();

        void registerPokemonEvent(PokemonResponse response);
        void registerAccelerometerEvent(PokemonResponse response);
    }

}
