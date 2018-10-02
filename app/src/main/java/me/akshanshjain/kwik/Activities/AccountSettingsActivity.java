package me.akshanshjain.kwik.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import me.akshanshjain.kwik.R;

public class AccountSettingsActivity extends AppCompatActivity {

    private Drawable[] backgroundDrawables;
    private ImageView backgroundView, profileImage;

    private TextView userName;
    private TextView eventsAttendedLabel, eventsOrganizedLabel;
    private TextView eventsAttended, eventsOrganized;

    private static final int PERMISSION_CALLBACK_CONSTANT = 9;
    private static final int REQUEST_PERMISSION = 7;
    private String[] permissionsRequired;

    private static final int IMAGE_PICK = 5;

    private SharedPreferences display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        /*
        Getting all the drawables into an array to set as dynamic background for account page.
        */
        backgroundDrawables = new Drawable[]{ContextCompat.getDrawable(this, R.drawable.celebration),
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
        Getting user's profile image from preferences, if any, and setting it into the image view.
        */
        getImageFromPref();

        permissionsRequired = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        /*
        Allowing users to customize their profile images.
        */
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Initially checking if the user has provided required permissions.
                */
                checkPermissions();
            }
        });
    }

    private void checkPermissions() {

        /*
        Checking if the permissions have been granted.
        If no, we request for the permissions.
        Else, we move forward to the next steps.
        */
        if (ActivityCompat.checkSelfPermission(AccountSettingsActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(AccountSettingsActivity.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(AccountSettingsActivity.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(AccountSettingsActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);

        } else {

            proceedAfterPermission();
        }
    }

    /*
    This function runs a loop to check if all the permissions have been granted,
    in the case of multiple permissions being requested from the user.
    */
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

    /*
    This method looks at the type of intent coming in.
    Based on the type, it classifies as permission or image picking and then performs required operations.
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PERMISSION) {
            if (ActivityCompat.checkSelfPermission(AccountSettingsActivity.this,
                    permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {

                proceedAfterPermission();
            }
        } else if (requestCode == IMAGE_PICK && resultCode == RESULT_OK && data != null) {

            pickImage(data);
        }
    }

    /*
    This function is called if we know that the user has granted the app all the required permissions.
    In this, we direct the user to the image picker apps
    */
    private void proceedAfterPermission() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK);
    }

    /*
    Asking the user to pick the image they want to use.
    After choosing, we set the image as their profile picture.
    */
    private void pickImage(Intent data) {
        try {
            Uri uri = data.getData();
            String[] file = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(uri, file, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(file[0]);
            String image = cursor.getString(columnIndex);
            cursor.close();

            //Storing the path of the selected image in Shared Preferences.
            storeImageInPref(image);

            //Setting the selected image as the profile image.
            Bitmap bitmap = BitmapFactory.decodeFile(image);
            profileImage.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Storing the path of the user selected image in the Shared Preferences to retrieve it later.
    */
    private void storeImageInPref(String imagePath) {
        SharedPreferences.Editor editor = getSharedPreferences("IMAGE", MODE_PRIVATE).edit();
        editor.putString("imagepath", imagePath);
        editor.apply();
    }

    /*
    Retrieving the path of the user selected image from Shared Preferences.
    We then set the image as the profile image.
    */
    private void getImageFromPref() {
        String imagePath = "";
        display = getSharedPreferences("IMAGE", MODE_PRIVATE);
        if (display.contains("imagepath")) {
            imagePath = display.getString("imagepath", null);

            if (!imagePath.isEmpty()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                profileImage.setImageBitmap(bitmap);
            }
        }
    }
}
