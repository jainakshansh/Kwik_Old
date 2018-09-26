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

public class WhatPlanFragment extends Fragment {

    private Typeface QLight;

    private TextView whatsPlanTv;
    private TextView eatingTv, nightOutTv, movieTv, customPlanTv;
    private ImageView next;

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
        QLight = Typeface.createFromAsset(getContext().getAssets(), "fonts/Quicksand-Light.ttf");

        whatsPlanTv = view.findViewById(R.id.whats_the_plan_tv);
        eatingTv = view.findViewById(R.id.eating_out_tv);
        nightOutTv = view.findViewById(R.id.night_out_tv);
        movieTv = view.findViewById(R.id.movie_tv);
        customPlanTv = view.findViewById(R.id.custom_plan_what);

        whatsPlanTv.setTypeface(QLight, Typeface.BOLD);
        eatingTv.setTypeface(QLight);
        nightOutTv.setTypeface(QLight);
        movieTv.setTypeface(QLight);
        customPlanTv.setTypeface(QLight);

        return view;
    }
}
