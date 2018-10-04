package me.akshanshjain.kwik.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    public EventUpdatesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_updates, container, false);

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

        populateUpdatesList();

        return view;
    }

    private void addUpdate() {
        databaseReference.child("events_list").child(/*Add key here*/"").child("event_updates").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), getString(R.string.error_connecting_please_check_internet_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
    TODO Retrieve data from Firebase and update the list.
    */
    private void populateUpdatesList() {
    }
}
