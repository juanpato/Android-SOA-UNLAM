package com.l1.tp_2.views.pokemon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.l1.tp_2.R;
import com.l1.tp_2.entities.PokemonResponse;
import com.l1.tp_2.utils.ShakeDetector;
import com.l1.tp_2.views.BasicActivity;
import com.l1.tp_2.views.login_historic.LoginHistoricActivity;

import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PokemonActivity extends BasicActivity implements PokemonContract.View {

    public static final int DECIMETER_TO_CENTIMETER_FACTOR = 10;
    public static final double HECTOGRAM_TO_KILOGRAM_FACTOR = 0.1;
    private PokemonPresenter pokemonPresenter;
    private ImageView image;
    private TextView name;
    private TextView data;
    private TextView error;
    private Button button;
    private FloatingActionButton historyButton;

    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    private boolean isShake = false;
    private String token;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        this.pokemonPresenter = new PokemonPresenter(this);

        this.image = findViewById(R.id.image);
        this.name = findViewById(R.id.pokemonName);
        this.error = findViewById(R.id.error);
        this.data = findViewById(R.id.pokemonData);
        this.button = findViewById(R.id.pokemonButton);
        historyButton = findViewById(R.id.historyButton);
        historyButton.setOnClickListener(v -> goToHistory());

        token = (String) Optional.ofNullable(this.getIntent().getExtras())
                .map(extras -> extras.get(TOKEN_KEY))
                .orElse("token");

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(count -> {
            isShake = true;
            pokemonPresenter.onShake();
        });

        button.setOnClickListener(click -> {
            error.setTextColor(Color.WHITE);
            if (!checkInternetConnection()) {
                setErrorMessage(error, "No hay conexión a internet");
                return;
            }

            pokemonPresenter.onButtonClick();
        });

    }

    @SuppressLint("DefaultLocale")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSuccess(PokemonResponse pokemonResponse) {
        try {
            Bitmap bitmap = CompletableFuture.supplyAsync(() -> {
                String imageLink = pokemonResponse.getSprites().getOther().getOfficialArtwork().getFrontDefault();
                try {
                    InputStream inputStream = new URL(imageLink).openStream();
                    return BitmapFactory.decodeStream(inputStream);
                } catch (Exception e) {
                    return null;
                }
            }).get();
            this.image.setImageBitmap(bitmap);
            this.name.setText(StringUtils.capitalize(pokemonResponse.getName()));
            this.data.setText(String.format("Altura: %dCm - Peso:%.0fKg", calculateCentimeters(pokemonResponse), calculateGrams(pokemonResponse)));

            if (isShake) {
                pokemonPresenter.registerShakeEvent(pokemonResponse, token);
            }

            pokemonPresenter.registerPokemonEvent(pokemonResponse, token);
        } catch (ExecutionException | InterruptedException e) {
            onError();
        }
        isShake = false;
    }

    @Override
    public void onError() {
        setErrorMessage(error, "Error obteniendo datos del pokemon");
    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    public void goToHistory() {
        Intent intent = new Intent(this, LoginHistoricActivity.class);
        intent.putExtra(TOKEN_KEY, token);
        startActivity(intent);
    }

    /**
     * The response has the height in decimeters
     * Transform it to centimeters
     *
     * @param pokemonResponse
     * @return
     */
    private Integer calculateCentimeters(PokemonResponse pokemonResponse) {
        return pokemonResponse.getHeight() * DECIMETER_TO_CENTIMETER_FACTOR;
    }

    /**
     * The response has the weight in hectograms
     * Transform it to grams
     *
     * @param pokemonResponse
     * @return
     */
    private double calculateGrams(PokemonResponse pokemonResponse) {
        return pokemonResponse.getWeight() * HECTOGRAM_TO_KILOGRAM_FACTOR;
    }


}