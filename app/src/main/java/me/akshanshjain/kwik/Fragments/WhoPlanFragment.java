package me.akshanshjain.kwik.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.akshanshjain.kwik.R;

public class WhoPlanFragment extends Fragment {

    private Typeface QLight;

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
        QLight = Typeface.createFromAsset(getContext().getAssets(), "fonts/Quicksand-Light.ttf");

        return view;
    }
}
