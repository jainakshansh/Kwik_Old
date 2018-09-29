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

import me.akshanshjain.kwik.R;

public class OverviewPlanFragment extends Fragment {

    private Typeface Lato;

    private TextView whatPlanLabel, whenPlanLabel, wherePlanLabel, whoPlanLabel;
    private TextView whatPlan, whenPlan, wherePlan, whoPlan;

    private ImageView doneButton;

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

        doneButton = view.findViewById(R.id.done_button_overview);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Add the event to the list and push it to the Firebase database.
            }
        });

        return view;
    }
}
