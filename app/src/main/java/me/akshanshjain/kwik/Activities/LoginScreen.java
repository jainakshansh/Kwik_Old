package me.akshanshjain.kwik.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import me.akshanshjain.kwik.R;

public class LoginScreen extends AppCompatActivity {

    private TextView appName, appDescription;
    private Typeface QLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        Setting up the activity for fullscreen mode.
        */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login_screen);

        //Initializing the typeface.
        QLight = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Light.ttf");

        //Referencing the views from XML layout.
        appName = findViewById(R.id.app_name_login);
        appDescription = findViewById(R.id.app_description_login);
        appName.setTypeface(QLight);
        appDescription.setTypeface(QLight);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }, 1500);
    }
}
