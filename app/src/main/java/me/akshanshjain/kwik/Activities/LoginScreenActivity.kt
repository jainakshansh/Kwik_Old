package me.akshanshjain.kwik.Activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import me.akshanshjain.kwik.Objects.UserDataItem
import java.util.concurrent.TimeUnit

class LoginScreenActivity : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth? = null
    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var verificationInProgress = false

    private lateinit var name: EditText
    private lateinit var phone: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
        Setting up the activity for fullscreen mode.
        */
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_login_screen)

        /*
        Checking if the user has already signed in.
        If yes, direct user to MainActivity directly and finish the Login one.
        Else, we take the user through the authentication process.
        */
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }

        /*
        Initializing and referencing the views from the XML layout.
        */
        name = findViewById(R.id.user_full_name_login)
        phone = findViewById(R.id.user_phone_number_login)

        loginButton = findViewById(R.id.button_login)

        //Initializing Auth.
        firebaseAuth = FirebaseAuth.getInstance()

        //Initializing callbacks.
        setupCallbacks()

        /*
        Setting the listener on the login button which verifies the pair of OTP and phone number to login the user.
        */
        loginButton.setOnClickListener(View.OnClickListener {
            /*
                Starting with the phone verification by validating the phone entry field first.
                */
            if (!validatePhoneNumber()) {
                return@OnClickListener
            }

            if (TextUtils.isEmpty(name.text.toString().trim { it <= ' ' })) {
                name.error = getString(R.string.required)
            }

            Toast.makeText(this@LoginScreenActivity, R.string.we_are_sending_you_an_otp, Toast.LENGTH_SHORT).show()
            loginButton.text = getString(R.string.resend_otp)
            startPhoneNumberVerification(phone.text.toString().trim { it <= ' ' })
        })
    }

    /*
    Setting up the callbacks for the phone verification.
    */
    private fun setupCallbacks() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                verificationInProgress = false
                signInWithPhoneAuthCredential(phoneAuthCredential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                verificationInProgress = false
                phone.error = getString(R.string.invalid_phone_number_or_code)
            }

            override fun onCodeSent(s: String?, forceResendingToken: PhoneAuthProvider.ForceResendingToken?) {}
        }
    }

    /*
    Authenticating the sign in with phone.
    */
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = task.result!!.user
                        Toast.makeText(this@LoginScreenActivity, R.string.login_successful, Toast.LENGTH_SHORT).show()

                        val userDataItem = UserDataItem(user.uid, name.text.toString().trim { it <= ' ' }, user.email,
                                user.phoneNumber, user.photoUrl.toString())

                        val logSuccessIntent = Intent(applicationContext, MainActivity::class.java)
                        logSuccessIntent.putExtra(USER_KEY, userDataItem)
                        startActivity(logSuccessIntent)

                        //Adding transition from the Login Activity to the Main Activity screen.
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
                        finish()
                    }
                }
    }

    /*
    Using PhoneAuthProvider to verify the phone number.
    */
    private fun startPhoneNumberVerification(phoneNumber: String) {
        var phoneNumber = phoneNumber
        phoneNumber = "+91$phoneNumber"
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                callbacks!!
        )

        verificationInProgress = true
    }

    override fun onStart() {
        super.onStart()
        if (verificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification(phone.text.toString())
        }
    }

    /*
    Returns either true or false by checking if the user has entered a valid phone number.
    */
    private fun validatePhoneNumber(): Boolean {
        val phoneNumber = phone.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(phoneNumber)) {
            phone.error = getString(R.string.invalid_phone_number)
            return false
        } else if (phoneNumber.length != 10) {
            phone.error = getString(R.string.invalid_phone_number)
            return false
        }
        return true
    }

    companion object {
        private val USER_KEY = "USER"
    }
}