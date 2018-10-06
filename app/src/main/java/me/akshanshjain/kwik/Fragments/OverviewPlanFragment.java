package me.akshanshjain.kwik.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import me.akshanshjain.kwik.Objects.EventItem;
import me.akshanshjain.kwik.R;

public class OverviewPlanFragment extends Fragment {

    private TextView whatPlan, whenPlan, wherePlan;
    private ImageView doneButton;
    private EditText descriptionPlan;
    private LinearLayout whoContainer;

    private static final String WHAT_KEY = "WHAT";
    private static final String WHEN_KEY = "WHEN";
    private static final String WHERE_KEY = "WHERE";
    private static final String WHO_KEY = "WHO";
    private String what, when, where;
    private String[] dateTimeSplit = new String[2];
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
        whatPlan = view.findViewById(R.id.what_plan_overview);
        whenPlan = view.findViewById(R.id.when_plan_overview);
        wherePlan = view.findViewById(R.id.where_plan_overview);
        descriptionPlan = view.findViewById(R.id.description_overview);

        whoContainer = view.findViewById(R.id.who_container_overview);

        String dateTime = "";
        if (!when.equals(getString(R.string.decide_later))) {
            dateTimeSplit = when.split("\n");
            dateTime = dateTimeSplit[0] + " ● " + dateTimeSplit[1];
        } else {
            dateTime = getString(R.string.decide_later);
            dateTimeSplit[0] = getString(R.string.not_set);
            dateTimeSplit[1] = getString(R.string.not_set);
        }

        whatPlan.setText(what);
        whenPlan.setText(dateTime);
        wherePlan.setText(where);

        populateWhoContainer();

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

        String key = databaseReference.child("events_list").push().getKey();
        EventItem eventItem = new EventItem(what, descriptionPlan.getText().toString(), dateTimeSplit[0], dateTimeSplit[1],
                where, selectedContacts, true, key);
        databaseReference.child("events_list").child(key).setValue(eventItem);
    }

    /*
    Presenting the list of people who are attending the event.
    This function populates the who's attending container with a few names from the list.
    */
    private void populateWhoContainer() {

        if (selectedContacts.size() > 3) {

            for (int i = 0; i < selectedContacts.size(); i++) {
                addingViewsToContainer(i);
            }

            int remaining = selectedContacts.size() - 3;
            TextView textView = new TextView(getContext());
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
            textView.setText("+ " + remaining);

        } else {

            for (int i = 0; i < selectedContacts.size(); i++) {
                addingViewsToContainer(i);
            }
        }
    }

    private void addingViewsToContainer(int position) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 8, 8, 8);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        imageView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.circular_white_background));
        imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_user));
        imageView.setPadding(16, 16, 16, 16);

        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
        textView.setText(selectedContacts.get(position));

        linearLayout.addView(imageView);
        linearLayout.addView(textView);
        whoContainer.addView(linearLayout);
    }
}
