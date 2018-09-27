package me.akshanshjain.kwik.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.akshanshjain.kwik.R;

public class WhatPlanFragment extends Fragment {

    private Typeface Lora;

    private TextView whatsPlanTv;
    private TextView eatingTv, nightOutTv, movieTv, customPlanTv;

    private LinearLayout eatingContainer, nightOutContainer, movieContainer;

    /*
    Mandatory constructor for instantiating the fragment.
    */
    public WhatPlanFragment() {
    }

    /*
    Inflating the fragment layout and performs the required operations or functions.
    */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflating the layout from the XML.
        View view = inflater.inflate(R.layout.fragment_what_plan, container, false);

        //Initializing the typeface for the Fragment.
        Lora = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lora.ttf");

        /*
        Referencing the views from the XML layout.
        */
        whatsPlanTv = view.findViewById(R.id.whats_the_plan_tv);
        eatingTv = view.findViewById(R.id.eating_out_tv);
        nightOutTv = view.findViewById(R.id.night_out_tv);
        movieTv = view.findViewById(R.id.movie_tv);
        customPlanTv = view.findViewById(R.id.custom_plan_what);

        whatsPlanTv.setTypeface(Lora, Typeface.BOLD);
        eatingTv.setTypeface(Lora);
        nightOutTv.setTypeface(Lora);
        movieTv.setTypeface(Lora);
        customPlanTv.setTypeface(Lora);

        eatingContainer = view.findViewById(R.id.eating_option_container);
        nightOutContainer = view.findViewById(R.id.night_out_container);
        movieContainer = view.findViewById(R.id.movie_option_container);

        /*
        Setting the on click listeners on the containers to pass data to the Activity.
        */
        eatingContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whatPlanListener whatPlan = null;
                whatPlan.whatPlanData("Eating Out");
            }
        });

        nightOutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whatPlanListener whatPlan = null;
                whatPlan.whatPlanData("Night Out");
            }
        });

        movieContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whatPlanListener whatPlan = null;
                whatPlan.whatPlanData("Movie");
            }
        });

        customPlanTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whatPlanListener whatPlan = null;
                whatPlan.whatPlanData("Custom Plan");
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    /*
    This interface passes the selected string to the activity.
    */
    public interface whatPlanListener {
        void whatPlanData(String whatPlan);
    }
}
