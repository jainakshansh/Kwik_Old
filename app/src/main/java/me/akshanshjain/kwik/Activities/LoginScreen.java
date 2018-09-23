package me.akshanshjain.kwik.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import me.akshanshjain.kwik.R;

public class LoginScreen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    private Typeface QLight;
    private TextView appGreeting, appDescription, directingToSignIn, signUp, otpInformation;
    private EditText phone;
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
        appGreeting = findViewById(R.id.greeting_login);
        appDescription = findViewById(R.id.app_description_login);
        directingToSignIn = findViewById(R.id.sign_in_direction_login);
        signUp = findViewById(R.id.sign_up_text_login);
        otpInformation = findViewById(R.id.otp_information_login);

        appGreeting.setTypeface(QLight, Typeface.BOLD);
        appDescription.setTypeface(QLight);
        directingToSignIn.setTypeface(QLight);
        signUp.setTypeface(QLight, Typeface.BOLD);
        otpInformation.setTypeface(QLight);

        phone = findViewById(R.id.user_phone_number_login);
        phone.setTypeface(QLight);

        loginButton = findViewById(R.id.button_login);
        loginButton.setTypeface(QLight, Typeface.BOLD);

        //Initializing Auth.
        firebaseAuth = FirebaseAuth.getInstance();

        //Initializing callbacks.
        setupCallbacks();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Implement the Firebase Phone Authentication here.
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    private void setupCallbacks() {
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                phone.setError("Invalid phone number or code.");
            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(LoginScreen.this, "Login successful!", Toast.LENGTH_SHORT).show();

                            Intent toMain = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(toMain);
                        }
                    }
                });
    }
}