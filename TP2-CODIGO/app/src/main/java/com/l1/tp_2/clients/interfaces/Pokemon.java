package com.l1.tp_2.clients.interfaces;

import com.l1.tp_2.entities.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Pokemon {

    @GET("api/v2/pokemon/{id}")
    Call<PokemonResponse> getPokemon(@Path("id") Integer id);

}
