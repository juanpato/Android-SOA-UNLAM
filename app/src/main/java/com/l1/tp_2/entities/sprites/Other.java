package com.l1.tp_2.entities.sprites;

import com.google.gson.annotations.SerializedName;

public class Other {
    @SerializedName("official-artwork")
    private OfficialArtwork officialArtwork;

    public OfficialArtwork getOfficialArtwork() {
        return officialArtwork;
    }
}
