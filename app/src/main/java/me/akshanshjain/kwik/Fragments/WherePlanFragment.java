package me.akshanshjain.kwik.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import me.akshanshjain.kwik.R;

public class WherePlanFragment extends Fragment {

    private Typeface Lora;
    private TextView wheresPlanTv, orTv, chooseLaterTv;
    private EditText planLocationET;

    /*
    Mandatory constructor for instantiating the fragment.
    */
    public WherePlanFragment() {
    }

    /*
    Inflating the fragment layout and performs the required operations or functions.
    */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflating the layout from the XML.
        View view = inflater.inflate(R.layout.fragment_where_plan, container, false);

        //Initializing the typeface for the Fragment.
        Lora = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lora.ttf");

        /*
        Referencing the views from the XML layout.
        */
        wheresPlanTv = view.findViewById(R.id.wheres_the_plan_tv);
        orTv = view.findViewById(R.id.or_tv_where);
        chooseLaterTv = view.findViewById(R.id.choose_later_where);
        planLocationET = view.findViewById(R.id.where_plan_location);

        wheresPlanTv.setTypeface(Lora, Typeface.BOLD);
        orTv.setTypeface(Lora);
        chooseLaterTv.setTypeface(Lora);
        planLocationET.setTypeface(Lora);

        return view;
    }
}
