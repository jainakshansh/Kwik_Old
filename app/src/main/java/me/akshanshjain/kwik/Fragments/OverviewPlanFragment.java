package me.akshanshjain.kwik.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import me.akshanshjain.kwik.Objects.EventItem;
import me.akshanshjain.kwik.R;

public class OverviewPlanFragment extends Fragment {

    private Typeface Lato;

    private TextView whatPlanLabel, whenPlanLabel, wherePlanLabel, whoPlanLabel;
    private TextView whatPlan, whenPlan, wherePlan;
    private ImageView doneButton;

    private static final String WHAT_KEY = "WHAT";
    private static final String WHEN_KEY = "WHEN";
    private static final String WHERE_KEY = "WHERE";
    private static final String WHO_KEY = "WHO";
    private String what, when, where;
    private ArrayList<String> selectedContacts;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    /*
    Mandatory constructor for instantiating the fragment.
    */
    public OverviewPlanFragment() {
    }

    /*
    Inflating the fragment layout and performs the required operations or functions.
    */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflating the layout from the XML.
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_overview_plan, container, false);

        //Initializing the typeface for the Fragment.
        Lato = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato.ttf");

        /*
        Getting the arguments passed into the fragment from the activity.
        This will be used to populate the views.
        */
        if (getArguments() != null) {
            getOverviewData();
        }

        /*
        Referencing all the views from the XML and setting the typeface on them.
        */
        whatPlanLabel = view.findViewById(R.id.what_plan_label_overview);
        whenPlanLabel = view.findViewById(R.id.when_plan_label_overview);
        wherePlanLabel = view.findViewById(R.id.where_plan_label_overview);
        whatPlanLabel.setTypeface(Lato);
        whenPlanLabel.setTypeface(Lato);
        wherePlanLabel.setTypeface(Lato);

        whatPlan = view.findViewById(R.id.what_plan_overview);
        whenPlan = view.findViewById(R.id.when_plan_overview);
        wherePlan = view.findViewById(R.id.where_plan_overview);
        whatPlan.setTypeface(Lato);
        whenPlan.setTypeface(Lato);
        wherePlan.setTypeface(Lato);

        whatPlan.setText(what);
        whenPlan.setText(when);
        wherePlan.setText(where);

        doneButton = view.findViewById(R.id.done_button_overview);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                When user confirms, we push the event data to the backend database.
                */
                pushEventToFirebase();
                Toast.makeText(getContext(), "Event creation successful!", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

        return view;
    }

    /*
    We've been storing all the details about the event creation in variables in the activity.
    So, this function helps get all the data passed to this fragment as an argument from the activity.
    We will then populate this data into the views before user confirms event creation.
    */
    private void getOverviewData() {
        what = getArguments().getString(WHAT_KEY);
        when = getArguments().getString(WHEN_KEY);
        where = getArguments().getString(WHERE_KEY);
        selectedContacts = getArguments().getStringArrayList(WHO_KEY);
    }

    /*
    This function pushes the created event to Firebase Database.
    The events are saved and can be found under the child node "events_list".
    */
    private void pushEventToFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        EventItem eventItem = new EventItem(what, "", when, where, null, true);
        databaseReference.child("events_list").push().setValue(eventItem);
    }
}
