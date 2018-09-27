package me.akshanshjain.kwik.Fragments;

import android.content.Context;
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

import me.akshanshjain.kwik.Interfaces.OnFragmentInteractionListener;
import me.akshanshjain.kwik.R;

public class WhenPlanFragment extends Fragment {

    private Typeface Lora;

    private TextView whensPlanTv;
    private TextView customTime, decideLater;
    private TextView tonightDate, tonightTime;
    private TextView tomorrowDate, tomorrowTime;
    private LinearLayout tonightContainer, tomorrowContainer;

    private OnFragmentInteractionListener interactionListener;

    /*
    Mandatory constructor for instantiating the fragment.
    */
    public WhenPlanFragment() {
    }

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
        View view = inflater.inflate(R.layout.fragment_when_plan, container, false);

        //Initializing the typeface for the Fragment.
        Lora = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lora.ttf");

        /*
        Referencing the views from the XML layout.
        */
        whensPlanTv = view.findViewById(R.id.whens_the_plan_tv);
        customTime = view.findViewById(R.id.custom_time_when);
        decideLater = view.findViewById(R.id.decide_later_when);
        whensPlanTv.setTypeface(Lora, Typeface.BOLD);
        customTime.setTypeface(Lora);
        decideLater.setTypeface(Lora);

        tonightDate = view.findViewById(R.id.tonight_option_date);
        tonightTime = view.findViewById(R.id.tonight_option_time);
        tomorrowDate = view.findViewById(R.id.tomorrow_option_date);
        tomorrowTime = view.findViewById(R.id.tomorrow_option_time);
        tonightDate.setTypeface(Lora);
        tonightTime.setTypeface(Lora);
        tomorrowDate.setTypeface(Lora);
        tomorrowTime.setTypeface(Lora);

        tonightContainer = view.findViewById(R.id.tonight_option_container);
        tomorrowContainer = view.findViewById(R.id.tomorrow_option_container);

        tonightContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interactionListener != null) {
                    interactionListener.onFragmentInteraction("Tonight 8PM");
                }
            }
        });

        tomorrowContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interactionListener != null) {
                    interactionListener.onFragmentInteraction("Tomorrow 6PM");
                }
            }
        });

        customTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Implement the Date Picker dialog.
            }
        });

        decideLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interactionListener != null) {
                    interactionListener.onFragmentInteraction("Decide Later");
                }
            }
        });

        return view;
    }
}
