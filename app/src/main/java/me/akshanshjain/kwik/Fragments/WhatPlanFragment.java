package me.akshanshjain.kwik.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.akshanshjain.kwik.Interfaces.OnFragmentInteractionListener;
import me.akshanshjain.kwik.R;

public class WhatPlanFragment extends Fragment {

    private Typeface Lato;

    private TextView whatsPlanTv;
    private TextView eatingTv, nightOutTv, movieTv, customPlanTv;
    private LinearLayout eatingContainer, nightOutContainer, movieContainer;
    private ImageView nextButton;

    private OnFragmentInteractionListener interactionListener;

    /*
    Mandatory constructor for instantiating the fragment.
    */
    public WhatPlanFragment() {
    }

    /*
    Attaching the interaction listener to the fragment.
    */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            interactionListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
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
        Lato = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato.ttf");

        /*
        Referencing the views from the XML layout.
        */
        whatsPlanTv = view.findViewById(R.id.whats_the_plan_tv);
        eatingTv = view.findViewById(R.id.eating_out_tv);
        nightOutTv = view.findViewById(R.id.night_out_tv);
        movieTv = view.findViewById(R.id.movie_tv);
        customPlanTv = view.findViewById(R.id.custom_plan_what);
        nextButton = view.findViewById(R.id.next_button_what_fragment);

        whatsPlanTv.setTypeface(Lato, Typeface.BOLD);
        eatingTv.setTypeface(Lato);
        nightOutTv.setTypeface(Lato);
        movieTv.setTypeface(Lato);
        customPlanTv.setTypeface(Lato);

        eatingContainer = view.findViewById(R.id.eating_option_container);
        nightOutContainer = view.findViewById(R.id.night_out_container);
        movieContainer = view.findViewById(R.id.movie_option_container);

        /*
        Setting the on click listeners on the containers to pass data to the Activity.
        */
        eatingContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewOnClick(getString(R.string.eating));
            }
        });

        nightOutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewOnClick(getString(R.string.night_out));
            }
        });

        movieContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewOnClick(getString(R.string.movie));
            }
        });

        customPlanTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCustomPlan();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(customPlanTv.getText().toString())) {
                    viewOnClick(customPlanTv.getText().toString());
                }
            }
        });

        return view;
    }

    /*
    This function is called when the view items are clicked and thus corresponding data is passed into it.
    This data will be available in the activity for use.
    */
    private void viewOnClick(String data) {
        if (interactionListener != null) {
            interactionListener.onFragmentInteraction(data);
        }
    }

    /*
    This is called when the custom plan text view is clicked.
    The function builds a pop-up which allows user to enter the custom plan details.
    */
    private void addCustomPlan() {
    }
}
