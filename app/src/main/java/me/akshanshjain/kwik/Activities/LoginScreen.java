package me.akshanshjain.kwik.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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

import me.akshanshjain.kwik.R;

public class LoginScreen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private boolean verificationInProgress = false;
    private String verificationId;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    private static final String VERIFICATION_PROGRESS = "VERIFICATION_IN_PROGRESS";

    private Typeface QLight;
    private TextView appGreeting, appDescription, directingToSignIn, otpInformation;
    private EditText phone, otp;
    private Button sendOTPButton, loginButton;

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
        QLight = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Light.ttf");

        /*
        Initializing and referencing the views from the XML layout.
        */
        appGreeting = findViewById(R.id.greeting_login);
        appDescription = findViewById(R.id.app_description_login);
        directingToSignIn = findViewById(R.id.sign_in_direction_login);
        otpInformation = findViewById(R.id.otp_information_login);

        appGreeting.setTypeface(QLight, Typeface.BOLD);
        appDescription.setTypeface(QLight);
        directingToSignIn.setTypeface(QLight);
        otpInformation.setTypeface(QLight);

        phone = findViewById(R.id.user_phone_number_login);
        phone.setTypeface(QLight);

        otp = findViewById(R.id.user_verify_otp);
        otp.setTypeface(QLight);

        sendOTPButton = findViewById(R.id.sending_otp_login);
        sendOTPButton.setTypeface(QLight, Typeface.BOLD);

        loginButton = findViewById(R.id.button_login);
        loginButton.setTypeface(QLight, Typeface.BOLD);

        //Initializing Auth.
        firebaseAuth = FirebaseAuth.getInstance();

        //Initializing callbacks.
        setupCallbacks();

        /*
        Setting the listener on the send OTP button which sends an OTP to the user.
        This is implemented through Firebase.
        */
        sendOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Starting with the phone verification by validating the phone entry field first.
                */
                if (!validatePhoneNumber()) {
                    return;
                }

                Toast.makeText(LoginScreen.this, "We are sending you an OTP!", Toast.LENGTH_SHORT).show();
                startPhoneNumberVerification(phone.getText().toString());
            }
        });

        /*
        Setting the listener on the login button which verifies the pair of OTP and phone number to login the user.
        */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                This is called when the user has entered OTP and we need to verify that.
                */
                if (!validateCode()) {
                    return;
                }

                verifyPhoneWithCode(verificationId, otp.getText().toString());
            }
        });
    }

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
                phone.setError("Invalid phone number or code.");
                Log.d("ADebug", e + "");
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                verificationId = s;
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
                            Toast.makeText(LoginScreen.this, "Login successful!", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    }
                });
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        phoneNumber = "+91" + phoneNumber;
        Log.d("ADebug", phoneNumber);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                callbacks
        );

        verificationInProgress = true;
    }

    private void verifyPhoneWithCode(String verificationId, String code) {
        //Starting the code verification.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        //Phone verification ends.
        signInWithPhoneAuthCredential(credential);
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
            phone.setError("Invalid phone number.");
            return false;
        } else if (phoneNumber.length() != 10) {
            phone.setError("Invalid phone number.");
            return false;
        }
        return true;
    }

    /*
    Returns either true or false by checking if the user has entered the OTP.
    */
    private boolean validateCode() {
        String code = otp.getText().toString();
        if (TextUtils.isEmpty(code)) {
            otp.setError("Invalid code.");
            return false;
        }
        return true;
    }

    /*
    This method saves instance state variables.
    */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(VERIFICATION_PROGRESS, verificationInProgress);
    }

    /*
    This method restores instance state variables.
    */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            verificationInProgress = savedInstanceState.getBoolean(VERIFICATION_PROGRESS);
            if (verificationInProgress) {
                //Need to call verify Phone number again.
            }
        }
    }
}