package me.akshanshjain.kwik.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import me.akshanshjain.kwik.R;

public class MainActivity extends AppCompatActivity {

    private RecyclerView eventsRecycler;
    private FloatingActionButton createEvent;
    private TextView upcomingLabel;

    private Typeface QLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting up the toolbar for the activity.
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        //Setting the app name on the Toolbar.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }

        /*
        Initialising and referencing views from the XML.
        */
        eventsRecycler = findViewById(R.id.recycler_view_main);
        createEvent = findViewById(R.id.fab_main);
        upcomingLabel = findViewById(R.id.upcoming_label_main);

        //Setting the typeface on the labels.
        QLight = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Light.ttf");
        upcomingLabel.setTypeface(QLight);

        //Starting the creating event activity on FAB click.
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreateEventActivity.class));
            }
        });
    }
}
