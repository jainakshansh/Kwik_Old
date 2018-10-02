package me.akshanshjain.kwik.Activities;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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

    private Typeface Lato;

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
                ContextCompat.getDrawable(this, R.drawable.refreshing),
                ContextCompat.getDrawable(this, R.drawable.selfie)};

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

    }
}
