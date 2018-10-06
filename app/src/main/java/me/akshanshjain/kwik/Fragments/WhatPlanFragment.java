package me.akshanshjain.kwik.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.akshanshjain.kwik.Interfaces.OnFragmentInteractionListener;
import me.akshanshjain.kwik.R;

public class WhatPlanFragment extends Fragment {

    private TextView customPlanTv;
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

        /*
        Referencing the views from the XML layout.
        */
        customPlanTv = view.findViewById(R.id.custom_plan_what);
        nextButton = view.findViewById(R.id.next_button_what_fragment);

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
    private String addCustomPlan() {

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogTheme));
        builder.setTitle(getString(R.string.what_s_the_plan));

        final EditText input = new EditText(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(16, 8, 16, 8);
        input.setLayoutParams(lp);
        input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        builder.setView(input);

        builder.setPositiveButton(getString(R.string.done), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!input.getText().toString().trim().isEmpty()) {
                    String update = input.getText().toString();
                    customPlanTv.setText(update);
                }
            }
        });

        builder.setNegativeButton(getString(R.string.dismiss), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setCancelable(false);
        builder.create().show();

        if (!input.getText().toString().isEmpty()) {
            return input.getText().toString();
        } else {
            return getString(R.string.custom_plan);
        }
    }
}
