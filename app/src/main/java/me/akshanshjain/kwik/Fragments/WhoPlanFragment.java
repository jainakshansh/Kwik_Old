package me.akshanshjain.kwik.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.akshanshjain.kwik.R;

public class WhoPlanFragment extends Fragment {

    private Typeface Lato;
    private TextView whosPlanTv, suggestedLabel;

    /*
    Mandatory constructor for instantiating the fragment.
    */
    public WhoPlanFragment() {
    }

    /*
    Inflating the fragment layout and performs the required operations or functions.
    */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflating the layout from the XML.
        View view = inflater.inflate(R.layout.fragment_who_plan, container, false);

        //Initializing the typeface for the Fragment.
        Lato = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato.ttf");

        /*
        Referencing the views from the XML layout.
        */
        whosPlanTv = view.findViewById(R.id.whos_invited_plan_tv);
        suggestedLabel = view.findViewById(R.id.suggested_label_who);

        whosPlanTv.setTypeface(Lato, Typeface.BOLD);
        suggestedLabel.setTypeface(Lato);

        return view;
    }
}
