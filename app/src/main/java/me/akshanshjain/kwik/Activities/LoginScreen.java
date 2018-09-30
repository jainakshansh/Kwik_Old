package me.akshanshjain.kwik.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

import java.util.concurrent.TimeUnit;

import me.akshanshjain.kwik.Objects.UserDataItem;
import me.akshanshjain.kwik.R;

public class LoginScreen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private boolean verificationInProgress = false;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    private static final String USER_KEY = "USER";

    private Typeface Lato;
    private TextView appGreeting, appDescription, directingToSignIn, otpInformation;
    private EditText name, phone;
    private Button loginButton;

    private int loginClickCount = 0;

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

        /*
        Checking if the user has already signed in.
        If yes, direct user to MainActivity directly and finish the login one.
        Else, we take the user through the authentication process.
        */
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        //Initializing the typeface.
        Lato = Typeface.createFromAsset(getAssets(), "fonts/Lato.ttf");

        /*
        Initializing and referencing the views from the XML layout.
        */
        appGreeting = findViewById(R.id.greeting_login);
        appDescription = findViewById(R.id.app_description_login);
        directingToSignIn = findViewById(R.id.sign_in_direction_login);
        otpInformation = findViewById(R.id.otp_information_login);

        appGreeting.setTypeface(Lato, Typeface.BOLD);
        appDescription.setTypeface(Lato);
        directingToSignIn.setTypeface(Lato);
        otpInformation.setTypeface(Lato);

        name = findViewById(R.id.user_full_name_login);
        name.setTypeface(Lato);
        phone = findViewById(R.id.user_phone_number_login);
        phone.setTypeface(Lato);

        loginButton = findViewById(R.id.button_login);
        loginButton.setTypeface(Lato, Typeface.BOLD);

        //Initializing Auth.
        firebaseAuth = FirebaseAuth.getInstance();

        //Initializing callbacks.
        setupCallbacks();

        /*
        Setting the listener on the login button which verifies the pair of OTP and phone number to login the user.
        */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                Starting with the phone verification by validating the phone entry field first.
                */
                if (!validatePhoneNumber()) {
                    return;
                }

                if (TextUtils.isEmpty(name.getText().toString())) {
                    name.setError(getString(R.string.required));
                }

                Toast.makeText(LoginScreen.this, R.string.we_are_sending_you_an_otp, Toast.LENGTH_SHORT).show();
                loginButton.setText(getString(R.string.resend_otp));
                startPhoneNumberVerification(phone.getText().toString());
            }
        });
    }

    /*
    Setting up the callbacks for the phone verification.
    */
    private void setupCallbacks() {
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                verificationInProgress = false;
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                verificationInProgress = false;
                phone.setError(getString(R.string.invalid_phone_number_or_code));
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                resendToken = forceResendingToken;
            }
        };
    }

    /*
    Authenticating the sign in with phone.
    */
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(LoginScreen.this, R.string.login_successful, Toast.LENGTH_SHORT).show();

                            UserDataItem userDataItem = new UserDataItem(user.getUid(), name.getText().toString(), user.getEmail(),
                                    user.getPhoneNumber(), String.valueOf(user.getPhotoUrl()));

                            Intent logSuccessIntent = new Intent(getApplicationContext(), MainActivity.class);
                            logSuccessIntent.putExtra(USER_KEY, userDataItem);
                            startActivity(logSuccessIntent);
                        }
                    }
                });
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        phoneNumber = "+91" + phoneNumber;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                callbacks
        );

        verificationInProgress = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (verificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification(phone.getText().toString());
        }
    }

    /*
    Returns either true or false by checking if the user has entered a valid phone number.
    */
    private boolean validatePhoneNumber() {
        String phoneNumber = phone.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            phone.setError(getString(R.string.invalid_phone_number));
            return false;
        } else if (phoneNumber.length() != 10) {
            phone.setError(getString(R.string.invalid_phone_number));
            return false;
        }
        return true;
    }
}