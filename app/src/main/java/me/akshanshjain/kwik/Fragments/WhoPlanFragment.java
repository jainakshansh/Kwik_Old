package me.akshanshjain.kwik.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import me.akshanshjain.kwik.Interfaces.WhoFragmentInteractionListener;
import me.akshanshjain.kwik.R;

public class WhoPlanFragment extends Fragment {

    private TextView whosPlanTv, invitedLabel;
    private ImageView nextButton, addInvitees;
    private LinearLayout invitedContactsContainer;

    private WhoFragmentInteractionListener interactionListener;

    private List<String> allContactsList;
    private List<String> commonContactsList;
    private List<String> registeredList;

    private static final String ALL_CONTACTS_KEY = "ALL_CONTACTS";
    private static final String REGISTERED_CONTACTS_KEY = "REGISTERED_CONTACTS";

    private String[] contactsToShow;
    private boolean[] checkedContacts;
    private ArrayList<Integer> userSelected = new ArrayList<>();
    private ArrayList<String> selectedContacts = new ArrayList<>();

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
            interactionListener = (WhoFragmentInteractionListener) context;
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
        registeredList = new ArrayList<>();
        commonContactsList = new ArrayList<>();

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

        contactsToShow = commonContactsList.toArray(new String[commonContactsList.size()]);
        checkedContacts = new boolean[contactsToShow.length];


        addInvitees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select contacts to invite.");

                builder.setMultiChoiceItems(contactsToShow, checkedContacts,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                                if (isChecked) {
                                    if (!userSelected.contains(position)) {
                                        userSelected.add(position);
                                    }
                                } else if (userSelected.contains(position)) {
                                    userSelected.remove(position);
                                }
                            }
                        });

                builder.setCancelable(false);
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < userSelected.size(); i++) {
                            selectedContacts.add(contactsToShow[userSelected.get(i)]);
                        }
                    }
                });

                builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedContacts.length; i++) {
                            checkedContacts[i] = false;
                            userSelected.clear();
                        }
                    }
                });

                builder.create().show();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewOnClick(selectedContacts);
            }
        });

        return view;
    }

    /*
    Getting both the list of contacts from the activity as arguments.
    */
    private void getContactsLists() {
        allContactsList = getArguments().getStringArrayList(ALL_CONTACTS_KEY);
        registeredList = getArguments().getStringArrayList(REGISTERED_CONTACTS_KEY);

        getCommonContacts();
    }

    /*
    Finding out the intersection of the common contacts from both the lists,
    and creating a common list of these.
    */
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

    private void viewOnClick(ArrayList<String> data) {
        if (interactionListener != null) {
            interactionListener.onContactsSelection(data);
        }
    }
}
