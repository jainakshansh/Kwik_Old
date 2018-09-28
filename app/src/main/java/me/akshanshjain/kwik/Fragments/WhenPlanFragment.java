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

    private Typeface Lato;

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
        Lato = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato.ttf");

        /*
        Referencing the views from the XML layout.
        */
        whensPlanTv = view.findViewById(R.id.whens_the_plan_tv);
        customTime = view.findViewById(R.id.custom_time_when);
        decideLater = view.findViewById(R.id.decide_later_when);
        whensPlanTv.setTypeface(Lato, Typeface.BOLD);
        customTime.setTypeface(Lato);
        decideLater.setTypeface(Lato);

        tonightDate = view.findViewById(R.id.tonight_option_date);
        tonightTime = view.findViewById(R.id.tonight_option_time);
        tomorrowDate = view.findViewById(R.id.tomorrow_option_date);
        tomorrowTime = view.findViewById(R.id.tomorrow_option_time);
        tonightDate.setTypeface(Lato);
        tonightTime.setTypeface(Lato);
        tomorrowDate.setTypeface(Lato);
        tomorrowTime.setTypeface(Lato);

        tonightContainer = view.findViewById(R.id.tonight_option_container);
        tomorrowContainer = view.findViewById(R.id.tomorrow_option_container);

        tonightContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewOnClick("Tonight 8PM");
            }
        });

        tomorrowContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewOnClick("Tomorrow 6PM");
            }
        });

        customTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Implement the Date Picker dialog.
                viewOnClick("Custom Time");
            }
        });

        decideLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewOnClick("Decide Later");
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
}
