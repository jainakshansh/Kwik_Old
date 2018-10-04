package me.akshanshjain.kwik.Fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import me.akshanshjain.kwik.Adapters.UpdatesAdapter;
import me.akshanshjain.kwik.Objects.EventItem;
import me.akshanshjain.kwik.R;

public class EventUpdatesFragment extends Fragment {

    private ImageView addButton;

    private List<String> updatesList;
    private RecyclerView updatesRecycler;
    private UpdatesAdapter adapter;

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

        addButton = view.findViewById(R.id.add_updates_button);

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

                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogTheme));
                builder.setTitle("What's the update?");

                final EditText input = new EditText(getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                lp.setMargins(16, 8, 16, 8);
                input.setLayoutParams(lp);
                builder.setView(input);

                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String update = input.getText().toString();
                        pushUpdateToFirebase(update);
                    }
                });
                builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
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

    private void pushUpdateToFirebase(String update) {
        updatesList.add(update);
        databaseReference.child("events_list").child(eventKey).child("updates").setValue(updatesList);
    }

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
}
