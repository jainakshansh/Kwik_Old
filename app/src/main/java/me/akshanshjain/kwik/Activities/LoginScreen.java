package me.akshanshjain.kwik.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import me.akshanshjain.kwik.R;

public class LoginScreen extends AppCompatActivity {

    private Typeface QLight;
    private TextView appName, appDescription;
    private EditText name, phone;
    private Button loginButton;

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

        name = findViewById(R.id.user_full_name_login);
        phone = findViewById(R.id.user_phone_number_login);
        name.setTypeface(QLight);
        phone.setTypeface(QLight);

        loginButton = findViewById(R.id.button_login);
        loginButton.setTypeface(QLight);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}