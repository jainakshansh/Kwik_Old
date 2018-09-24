package me.akshanshjain.kwik.Activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import me.akshanshjain.kwik.R;

public class CreateEventActivity extends AppCompatActivity {

    private Typeface QLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        //Initializing the Typeface for the activity.
        QLight = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Light.ttf");

        //Setting up the toolbar for the activity.
        Toolbar toolbar = findViewById(R.id.toolbar_create_event);
        setSupportActionBar(toolbar);

        //Setting the up button in the toolbar.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //Setting up the toolbar title.
        TextView titleToolbar = findViewById(R.id.toolbar_title_create_event);
        titleToolbar.setTypeface(QLight, Typeface.BOLD);
        titleToolbar.setText(getString(R.string.create_event));
    }
}
