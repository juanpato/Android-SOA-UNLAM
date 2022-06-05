package com.l1.tp_2.views.pokemon;

import com.l1.tp_2.clients.PokemonClient;
import com.l1.tp_2.clients.SendEventClient;
import com.l1.tp_2.entities.Event;
import com.l1.tp_2.entities.PokemonResponse;
import com.l1.tp_2.entities.TypeEvent;
import com.l1.tp_2.utils.Run;

import java.util.function.Consumer;

public class PokemonModel implements PokemonContract.Model {

    private PokemonClient pokemonClient;
    private SendEventClient sendEventClient;

    public PokemonModel(Consumer<PokemonResponse> onSuccess, Run onFailure) {
        this.pokemonClient = new PokemonClient(onSuccess, onFailure);
        this.sendEventClient = new SendEventClient(r -> System.out.print("Event sent"), () -> System.out.print("Fail sending event"));
    }

    @Override
    public void getPokemon(Integer id) {
        pokemonClient.getPokemon(id);
    }

    @Override
    public void registerPokemonEvent(PokemonResponse response, String token) {
        Event event = new Event()
                .setDescription(String.format("Pokemon found with name %s", response.getName()))
                .setTypeEvents(TypeEvent.SEARCH_POKEMON);
        sendEventClient.sendEvent(token, event);
    }

    @Override
    public void registerAccelerometerEvent(PokemonResponse response, String token) {
        Event event = new Event()
                .setDescription("The user shook the cell phone")
                .setTypeEvents(TypeEvent.SHAKE);
        sendEventClient.sendEvent(token, event);
    }
}
