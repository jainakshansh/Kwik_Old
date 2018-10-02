package me.akshanshjain.kwik.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import me.akshanshjain.kwik.Interfaces.OnFragmentInteractionListener;
import me.akshanshjain.kwik.R;

public class WherePlanFragment extends Fragment {

    private TextView chooseLaterTv;
    private EditText planLocationET;
    private ImageView nextButton;

    private OnFragmentInteractionListener interactionListener;

    /*
    Mandatory constructor for instantiating the fragment.
    */
    public WherePlanFragment() {
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
        View view = inflater.inflate(R.layout.fragment_where_plan, container, false);

        /*
        Referencing the views from the XML layout.
        */
        chooseLaterTv = view.findViewById(R.id.choose_later_where);
        planLocationET = view.findViewById(R.id.where_plan_location);
        nextButton = view.findViewById(R.id.next_button_where_fragment);

        chooseLaterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewOnClick(getString(R.string.choose_later));
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Getting the location that the user has entered from the edit text.
                */
                String location = planLocationET.getText().toString();
                if (!TextUtils.isEmpty(location)) {
                    viewOnClick(location);
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
}
