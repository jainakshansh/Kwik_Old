package me.akshanshjain.kwik.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import me.akshanshjain.kwik.R;

public class AccountSettingsActivity extends AppCompatActivity {

    private Drawable[] backgroundDrawables;
    private ImageView backgroundView, profileImage;

    private TextView userName;
    private TextView eventsAttendedLabel, eventsOrganizedLabel;
    private TextView eventsAttended, eventsOrganized;

    private Typeface Lato;

    private static final int PERMISSION_CALLBACK_CONSTANT = 9;
    private static final int REQUEST_PERMISSION = 7;
    private String[] permissionsRequired;
    private SharedPreferences accountPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        //Setting up the typeface for the activity.
        Lato = Typeface.createFromAsset(getAssets(), "fonts/Lato.ttf");

        /*
        Getting all the drawables into an array to set as dynamic background for account page.
        */
        backgroundDrawables = new Drawable[]{ContextCompat.getDrawable(this, R.drawable.beer_celebration),
                ContextCompat.getDrawable(this, R.drawable.celebration),
                ContextCompat.getDrawable(this, R.drawable.chilling),
                ContextCompat.getDrawable(this, R.drawable.eating_together),
                ContextCompat.getDrawable(this, R.drawable.group_selfie),
                ContextCompat.getDrawable(this, R.drawable.hang_out),
                ContextCompat.getDrawable(this, R.drawable.refreshing)};

        backgroundView = findViewById(R.id.background_account_settings);

        /*
        Setting the background as a random drawable image.
        */
        Random random = new Random();
        int bg = random.nextInt(backgroundDrawables.length);
        backgroundView.setImageDrawable(backgroundDrawables[bg]);

        /*
        Referencing all the views from the XML layout.
        */
        profileImage = findViewById(R.id.profile_image_account_settings);
        userName = findViewById(R.id.profile_name_account_settings);
        eventsOrganizedLabel = findViewById(R.id.organized_label_account_settings);
        eventsAttendedLabel = findViewById(R.id.attended_label_account_settings);
        eventsOrganized = findViewById(R.id.number_organized_account_settings);
        eventsAttended = findViewById(R.id.number_attended_account_settings);

        /*
        Applying the typeface to all the possible views.
        */
        userName.setTypeface(Lato, Typeface.BOLD);
        eventsOrganizedLabel.setTypeface(Lato);
        eventsAttendedLabel.setTypeface(Lato);
        eventsOrganized.setTypeface(Lato);
        eventsAttended.setTypeface(Lato);

        permissionsRequired = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        /*
        Allowing users to customize their profile images.
        */
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissions();
            }
        });
    }

    private void checkPermissions() {

        if (ActivityCompat.checkSelfPermission(AccountSettingsActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(AccountSettingsActivity.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(AccountSettingsActivity.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(AccountSettingsActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);

        } else {

            proceedAfterPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            boolean allGranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allGranted = true;
                } else {
                    allGranted = false;
                    break;
                }
            }

            if (allGranted) {
                proceedAfterPermission();

            } else if (ActivityCompat.shouldShowRequestPermissionRationale(AccountSettingsActivity.this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(AccountSettingsActivity.this, permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(AccountSettingsActivity.this, permissionsRequired[2])) {

                ActivityCompat.requestPermissions(AccountSettingsActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PERMISSION) {
            if (ActivityCompat.checkSelfPermission(AccountSettingsActivity.this,
                    permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {

                proceedAfterPermission();
            }
        }
    }

    private void proceedAfterPermission() {
        Toast.makeText(this, "All permissions granted!", Toast.LENGTH_SHORT).show();
    }
}
