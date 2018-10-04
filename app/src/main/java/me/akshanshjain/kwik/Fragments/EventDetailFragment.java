package me.akshanshjain.kwik.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.akshanshjain.kwik.Activities.EventDetailsActivity;
import me.akshanshjain.kwik.Objects.EventItem;
import me.akshanshjain.kwik.R;

public class EventDetailFragment extends Fragment implements EventDetailsActivity.EventListener {

    private TextView eventName;

    private EventItem eventItem;
    private static final String EVENT_KEY = "EVENT";

    /*
    Mandatory constructor for instantiating the fragment.
    */
    public EventDetailFragment() {
    }

    /*
    Inflating the fragment layout and performs the required operations or functions.
    */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflating the layout from the XML.
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_event_detail, container, false);

        return view;
    }

    @Override
    public void onFragmentInstantiated(Bundle bundle) {
        if (bundle != null) {
            eventItem = bundle.getParcelable(EVENT_KEY);

            Log.d("ADebug", eventItem.getEventName());
        }
    }
}
