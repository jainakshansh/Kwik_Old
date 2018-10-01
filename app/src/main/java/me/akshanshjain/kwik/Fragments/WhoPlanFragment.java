package me.akshanshjain.kwik.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.akshanshjain.kwik.Interfaces.OnFragmentInteractionListener;
import me.akshanshjain.kwik.R;

public class WhoPlanFragment extends Fragment {

    private TextView whosPlanTv, invitedLabel;
    private ImageView nextButton, addInvitees;
    private LinearLayout invitedContactsContainer;

    private OnFragmentInteractionListener interactionListener;

    private List<String> allContactsList;
    private List<String> commonContactsList;
    private List<String> registeredList;

    private static final String ALL_CONTACTS_KEY = "ALL_CONTACTS";
    private static final String REGISTERED_CONTACTS_KEY = "REGISTERED_CONTACTS";

    /*
    Mandatory constructor for instantiating the fragment.
    */
    public WhoPlanFragment() {
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
        View view = inflater.inflate(R.layout.fragment_who_plan, container, false);

        //Initializing the typeface for the Fragment.
        Typeface Lato = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato.ttf");

        /*
        Creating the empty list which will compare all contacts and get common contacts.
        */
        allContactsList = new ArrayList<>();
        commonContactsList = new ArrayList<>();
        registeredList = new ArrayList<>();

        /*
        Getting the arguments passed into the fragment from the activity.
        This will be used to get all the contacts retrieved from the backend database and users contacts.
        */
        if (getArguments() != null) {
            getContactsLists();
        }

        /*
        Referencing the views from the XML layout.
        */
        whosPlanTv = view.findViewById(R.id.whos_invited_plan_tv);
        invitedLabel = view.findViewById(R.id.invited_label_who);
        nextButton = view.findViewById(R.id.next_button_who_fragment);

        whosPlanTv.setTypeface(Lato, Typeface.BOLD);
        invitedLabel.setTypeface(Lato);

        addInvitees = view.findViewById(R.id.add_contacts_invite);
        invitedContactsContainer = view.findViewById(R.id.invited_contacts_container);

        addInvitees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewOnClick("");
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

    private void getContactsLists() {
        allContactsList = getArguments().getStringArrayList(ALL_CONTACTS_KEY);
        registeredList = getArguments().getStringArrayList(REGISTERED_CONTACTS_KEY);

        getCommonContacts();
    }

    private void getCommonContacts() {
        for (int i = 0; i < allContactsList.size(); i++) {
            for (int j = 0; j < registeredList.size(); j++) {
                String phone1 = registeredList.get(j);
                String phone2 = allContactsList.get(i);
                if (PhoneNumberUtils.compare(phone1, phone2)) {
                    if (!commonContactsList.contains(phone2)) {
                        commonContactsList.add(phone2);
                    }
                }
            }
        }
    }
}
