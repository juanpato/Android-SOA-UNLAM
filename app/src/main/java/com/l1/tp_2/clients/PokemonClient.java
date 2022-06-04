package com.l1.tp_2.clients;

import android.annotation.SuppressLint;

import com.l1.tp_2.clients.interfaces.Pokemon;
import com.l1.tp_2.entities.PokemonResponse;
import com.l1.tp_2.utils.Run;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonClient extends RetrofitClient<Pokemon> {

    private Consumer<PokemonResponse> onSuccess;
    private Run onFailure;

    public PokemonClient(Consumer<PokemonResponse> onSuccess, Run onFailure) {
        super(Pokemon.class);
        this.onSuccess = onSuccess;
        this.onFailure = onFailure;
    }

    @Override
    protected String getBaseUrl() {
        return "https://pokeapi.co";
    }

    public void getPokemon(Integer id) {
        Call<PokemonResponse> call = getClient().getPokemon(id);

        call.enqueue(new Callback<PokemonResponse>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {

                if (response.isSuccessful()) {
                    onSuccess.accept(response.body());
                } else {
                    onFailure.run();
                }

            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                onFailure.run();
            }
        });
    }
}
