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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.akshanshjain.kwik.Adapters.UpdatesAdapter;
import me.akshanshjain.kwik.R;

public class EventUpdatesFragment extends Fragment {

    private TextView etaUpdates;

    private List<String> updatesList;
    private RecyclerView updatesRecycler;
    private UpdatesAdapter adapter;

    public EventUpdatesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_updates, container, false);

        etaUpdates = view.findViewById(R.id.eta_updates);

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

    /*
    TODO Retrieve data from Firebase and update the list.
    */
    private void populateUpdatesList() {
    }
}
