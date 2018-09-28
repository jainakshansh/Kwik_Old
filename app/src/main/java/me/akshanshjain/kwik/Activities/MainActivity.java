package me.akshanshjain.kwik.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import me.akshanshjain.kwik.Adapters.EventsAdapter;
import me.akshanshjain.kwik.Objects.EventItem;
import me.akshanshjain.kwik.Objects.UserDataItem;
import me.akshanshjain.kwik.R;

public class MainActivity extends AppCompatActivity {

    private RecyclerView eventsRecycler;
    private FloatingActionButton createEvent;
    private TextView upcomingLabel;

    private List<EventItem> eventItemList;
    private EventsAdapter eventsAdapter;

    private Typeface Lato;

    private UserDataItem userDataItem;
    private static final String USER_KEY = "USER";

    private static final int PERMISSION_CONSTANT = 200;
    private static final int REQUEST_PERMISSION_SETTING = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing the Typeface for the activity.
        Lato = Typeface.createFromAsset(getAssets(), "fonts/Lato.ttf");

        //Setting up the toolbar for the activity.
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        //Setting the app name on the Toolbar.
        if (getSupportActionBar() != null) {
            //Firstly removing the default name that's added to the toolbar automatically.
            getSupportActionBar().setTitle("");
        }
        ((TextView) findViewById(R.id.app_name_main)).setTypeface(Lato, Typeface.BOLD);

        /*
        Getting the phone number of the user from the intent.
        This will be stored in the database which will show all the registered numbers.
        */
        Intent loginSuccessIntent = getIntent();
        if (loginSuccessIntent.getExtras() != null) {
            userDataItem = loginSuccessIntent.getExtras().getParcelable(USER_KEY);

            /*
            Sending the data about the current user to the Firebase Database.
            */
            pushUserDataToFirebase(userDataItem);
        }

        /*
        Initialising and referencing views from the XML.
        */
        eventsRecycler = findViewById(R.id.recycler_view_main);
        createEvent = findViewById(R.id.fab_main);
        upcomingLabel = findViewById(R.id.upcoming_label_main);

        //Setting the typeface on the labels.
        upcomingLabel.setTypeface(Lato);

        //Starting the creating event activity on FAB click.
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Checking if the users has provided the runtime permission for Reading Contacts.
                If provided, we allow user to Create Event directly.
                Else, we request for the permission using the run-time permissions.
                */
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    /*
                    Showing the user why we need the permissions since it's not already granted.
                    */
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Need Contacts Permission");
                    builder.setMessage("This app needs contacts to get other registered users.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_CONSTANT);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                } else {
                    /*
                    If the user has permission already, we just go ahead with the work.
                    */
                    startActivity(new Intent(getApplicationContext(), CreateEventActivity.class));
                }
            }
        });

        //Setting up the Recycler View.
        eventItemList = new ArrayList<>();
        eventsAdapter = new EventsAdapter(this, eventItemList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        eventsRecycler.setLayoutManager(layoutManager);
        eventsRecycler.setItemAnimator(new DefaultItemAnimator());
        eventsRecycler.setAdapter(eventsAdapter);

        /*
        This centers the child elements w.r.t. the screen on scroll.
        Makes sure that an item is always fully visible and never partial.
        */
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(eventsRecycler);

        /*
        Checking if the mobile is connected to the internet.
        If connected, we retrieve the data from the Firebase RealTime Database.
        */
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            //Calling the function to get the data.
            //getEventsData();
        }
    }

    /*
    This function gets a reference to the firebase database.
    Then is called every time the data changes.
    The code for checking network connectivity is included in the onCreate function.
    */
    private void getEventsData() {
        //Getting an instance of the database.
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();

        /*
        Loading the data from the database whenever any value changes.
        */
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                EventItem eventItem = dataSnapshot.getValue(EventItem.class);
                eventItemList.add(eventItem);
                eventsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                eventsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                eventsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                eventsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Error connecting. Please check your internet connection!", Toast.LENGTH_SHORT).show();
            }
        });

        //Notifying the adapter that the data set has changed and to refresh the list.
        eventsAdapter.notifyDataSetChanged();
    }

    /*
    This function creates a child node if not present and uses the User ID as the unique identifier.
    It then creates a child node with the User data.
    */
    private void pushUserDataToFirebase(UserDataItem userDataItem) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("registered_users").child(userDataItem.getUserPhoneNumber()).setValue(userDataItem);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CONSTANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(getApplicationContext(), CreateEventActivity.class));
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)) {
                    /*
                    Showing the user why we need the permissions since it's not already granted.
                    */
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Need Contacts Permission");
                    builder.setMessage("This app needs contacts to get other registered users.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_CONSTANT);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(getBaseContext(), "Unable to get permission.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(getApplicationContext(), CreateEventActivity.class));
            }
        }
    }
}
