package me.akshanshjain.kwik.Fragments;


import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import me.akshanshjain.kwik.Adapters.UpdatesAdapter;
import me.akshanshjain.kwik.Objects.EventItem;
import me.akshanshjain.kwik.R;

public class EventUpdatesFragment extends Fragment {

    private TextView etaUpdates;
    private ImageView addButton;

    private List<String> updatesList;
    private RecyclerView updatesRecycler;
    private UpdatesAdapter adapter;
    private Typeface Lato;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private EventItem eventItem;
    private String eventKey;
    private static final String EVENT_KEY = "EVENT";

    /*
    Mandatory constructor for instantiating the fragment.
    */
    public EventUpdatesFragment() {
    }

    /*
    Inflating the fragment layout and performs the required operations or functions.
    */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflating the layout from the XML.
        View view = inflater.inflate(R.layout.fragment_event_updates, container, false);

        if (getArguments() != null) {
            eventItem = getArguments().getParcelable(EVENT_KEY);
            eventKey = eventItem.getEventKey();
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        etaUpdates = view.findViewById(R.id.eta_updates);
        addButton = view.findViewById(R.id.add_updates_button);

        String eta = calcTimeDifference() + getString(R.string.hours);
        etaUpdates.setText(eta);

        updatesList = new ArrayList<>();
        updatesRecycler = view.findViewById(R.id.recycler_view_updates);
        adapter = new UpdatesAdapter(getActivity(), updatesList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        updatesRecycler.setLayoutManager(layoutManager);
        updatesRecycler.setItemAnimator(new DefaultItemAnimator());
        updatesRecycler.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                Creating an Alert Dialog to take in user input for the
                */
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogTheme));
                builder.setTitle(getString(R.string.whats_the_update));

                final EditText input = new EditText(getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(16, 8, 16, 8);
                input.setLayoutParams(lp);
                input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                builder.setView(input);

                builder.setPositiveButton(getString(R.string.done), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!input.getText().toString().isEmpty()) {
                            String update = input.getText().toString();
                            pushUpdateToFirebase(update);
                        }
                    }
                });

                builder.setNegativeButton(getString(R.string.dismiss), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setCancelable(false);
                builder.create().show();
            }
        });

        populateUpdatesList();

        return view;
    }

    /*
    Pushing the newly added update to the firebase so that consequent container can be updated.
    */
    private void pushUpdateToFirebase(String update) {
        updatesList.add(update);
        databaseReference.child("events_list").child(eventKey).child("updates").setValue(updatesList);
    }

    /*
    Retrieving the data from the Updates node inside the particular event node from the Firebase Database.
    */
    private void populateUpdatesList() {
        databaseReference.child("events_list").child(eventKey).child("updates").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String update = dataSnapshot.getValue(String.class);
                updatesList.add(update);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), getString(R.string.error_connecting_please_check_internet_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
    Calculating the ETA in hours to be displayed in the TextView.
    Here we find the different between the event date and the current date.
    This way we get approximate number of hours as ETA.
    */
    private String calcTimeDifference() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US);
        Date now = new Date();
        String currentDateFormat = sdf.format(now);
        String eventDateFormat = eventItem.getEventDate() + " " + eventItem.getEventTime();

        Date currentDate = null, eventDate = null;

        try {
            currentDate = sdf.parse(currentDateFormat);
            eventDate = sdf.parse(eventDateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = 0;
        if (!TextUtils.isEmpty(eventItem.getEventDate())) {
            if (!eventItem.getEventDate().equals(getString(R.string.not_set)) &&
                    !eventItem.getEventTime().equals(getString(R.string.not_set))) {

                long differenceInMillis = Math.abs(eventDate.getTime() - currentDate.getTime());
                diff = TimeUnit.HOURS.convert(differenceInMillis, TimeUnit.MILLISECONDS);
            }
        }

        return String.valueOf(diff);
    }
}
