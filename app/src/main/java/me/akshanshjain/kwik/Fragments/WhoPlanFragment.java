package me.akshanshjain.kwik.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.view.ContextThemeWrapper;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
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
    private List<String> contactsNamesList;

    private static final String ALL_CONTACTS_KEY = "ALL_CONTACTS";
    private static final String REGISTERED_CONTACTS_KEY = "REGISTERED_CONTACTS";
    private static final String WHO_NAMES_KEY = "NAMES";

    private String[] contactsToShow;
    private boolean[] checkedContacts;
    private ArrayList<Integer> userSelected = new ArrayList<>();
    private ArrayList<String> selectedContacts = new ArrayList<>();
    private ArrayList<String> commonContactNamesList = new ArrayList<>();

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

        /*
        Creating the empty list which will compare all contacts and get common contacts.
        */
        allContactsList = new ArrayList<>();
        registeredList = new ArrayList<>();
        commonContactsList = new ArrayList<>();
        contactsNamesList = new ArrayList<>();

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

        addInvitees = view.findViewById(R.id.add_contacts_invite);
        invitedContactsContainer = view.findViewById(R.id.invited_contacts_container);

        contactsToShow = commonContactNamesList.toArray(new String[commonContactNamesList.size()]);
        checkedContacts = new boolean[contactsToShow.length];


        addInvitees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogTheme));
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
                                    userSelected.remove(Integer.valueOf(position));
                                }
                            }
                        });

                builder.setCancelable(false);
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
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
        contactsNamesList = getArguments().getStringArrayList(WHO_NAMES_KEY);

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
                    String name = contactsNamesList.get(i);
                    if (!commonContactsList.contains(phone2)) {
                        commonContactsList.add(phone2);
                    }
                    if (!commonContactNamesList.contains(name)) {
                        commonContactNamesList.add(name);
                        Log.d("ADebug", name);
                    }
                }
            }
        }
    }

    /*
    This function will be called when the next button is clicked.
    So that we can send the list of invited contacts to the activity.
    */
    private void viewOnClick(ArrayList<String> data) {
        if (interactionListener != null) {
            interactionListener.onContactsSelection(data);
        }
    }
}
