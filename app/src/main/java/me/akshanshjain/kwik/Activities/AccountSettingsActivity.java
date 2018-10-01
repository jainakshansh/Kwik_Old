package me.akshanshjain.kwik.Activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.util.Random;

import me.akshanshjain.kwik.R;

public class AccountSettingsActivity extends AppCompatActivity {

    private Drawable[] backgroundDrawables;
    private ImageView backgroundView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        /*
        Getting all the drawables into an array to set as dynamic background for account page.
        */
        backgroundDrawables = new Drawable[]{ContextCompat.getDrawable(this, R.drawable.beer_celebration),
                ContextCompat.getDrawable(this, R.drawable.celebration),
                ContextCompat.getDrawable(this, R.drawable.chilling),
                ContextCompat.getDrawable(this, R.drawable.eating_together),
                ContextCompat.getDrawable(this, R.drawable.group_selfie),
                ContextCompat.getDrawable(this, R.drawable.hang_out),
                ContextCompat.getDrawable(this, R.drawable.refreshing),
                ContextCompat.getDrawable(this, R.drawable.selfie)};

        backgroundView = findViewById(R.id.background_account_settings);
        Random random = new Random();
        int bg = random.nextInt(backgroundDrawables.length);
        backgroundView.setImageDrawable(backgroundDrawables[bg]);
    }
}
