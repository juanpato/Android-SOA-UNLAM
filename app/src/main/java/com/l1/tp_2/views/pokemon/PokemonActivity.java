package com.l1.tp_2.views.pokemon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.l1.tp_2.R;
import com.l1.tp_2.entities.PokemonResponse;
import com.l1.tp_2.utils.ShakeDetector;

import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PokemonActivity extends AppCompatActivity implements PokemonContract.View {

    private PokemonPresenter pokemonPresenter;
    private ImageView image;
    private TextView name;
    private TextView data;
    private Button button;

    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        this.pokemonPresenter = new PokemonPresenter(this);

        this.image = findViewById(R.id.image);
        this.name = findViewById(R.id.pokemonName);
        this.data = findViewById(R.id.pokemonData);
        this.button = findViewById(R.id.pokemonButton);

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(count -> pokemonPresenter.onButtonClick());

        button.setOnClickListener(click -> {
            pokemonPresenter.onButtonClick();
        });

    }

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
            this.data.setText(String.format("Altura: %s - Peso: %s", pokemonResponse.getHeight(), pokemonResponse.getWeight()));
        } catch (ExecutionException | InterruptedException e) {
            onError();
        }
    }

    @Override
    public void onError() {

    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

}