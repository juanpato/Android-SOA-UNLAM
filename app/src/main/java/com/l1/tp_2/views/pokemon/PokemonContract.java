package com.l1.tp_2.views.pokemon;

import com.l1.tp_2.entities.PokemonResponse;

public interface PokemonContract {

    interface View {
        void onSuccess(PokemonResponse pokemonResponse);

        void onError();
    }

    interface Model {
        void getPokemon(Integer id);

        void registerPokemonEvent(PokemonResponse response, String token);
        void registerAccelerometerEvent(PokemonResponse response, String token);
    }

    interface Presenter {
        void onButtonClick();
        void onShake();

        void registerShakeEvent(PokemonResponse response, String token);
        void registerPokemonEvent(PokemonResponse response, String token);
    }

}
