package me.akshanshjain.kwik.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import me.akshanshjain.kwik.Interfaces.OnFragmentInteractionListener;
import me.akshanshjain.kwik.R;
import me.akshanshjain.kwik.Utils.Utils;

public class WhoPlanFragment extends Fragment {

    private TextView whosPlanTv, invitedLabel;
    private ImageView nextButton, addInvitees;
    private LinearLayout invitedContactsContainer;

    private OnFragmentInteractionListener interactionListener;

    private DatabaseReference registeredUsers;

    private List<String> allContactsList;
    private List<String> commonContactsList;

    private String[] commonContactsArray;
    private boolean[] checkedContacts;
    private List<Integer> userItems;
    private List<String> invitedContacts;

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
        Referencing the views from the XML layout.
        */
        whosPlanTv = view.findViewById(R.id.whos_invited_plan_tv);
        invitedLabel = view.findViewById(R.id.invited_label_who);
        nextButton = view.findViewById(R.id.next_button_who_fragment);

        whosPlanTv.setTypeface(Lato, Typeface.BOLD);
        invitedLabel.setTypeface(Lato);

        addInvitees = view.findViewById(R.id.add_contacts_invite);
        invitedContactsContainer = view.findViewById(R.id.invited_contacts_container);

        /*
        Creating the empty list which will contain all contacts and common contacts.
        */
        allContactsList = new ArrayList<>();
        commonContactsList = new ArrayList<>();

        GetNormalizeContactsTask asyncTask = new GetNormalizeContactsTask();
        asyncTask.execute();

        /*
        Getting the reference from the Firebase Database and getting all the registered numbers.
        These are then checked against the contacts.
        The final contacts are then added to the common contacts list.
        */
        registeredUsers = FirebaseDatabase.getInstance().getReference().child("registered_numbers");
        registeredUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot contact : dataSnapshot.getChildren()) {
                    for (int i = 0; i < allContactsList.size(); i++) {
                        if (allContactsList.get(i).equals(contact.getKey())) {
                            commonContactsList.add(contact.getKey());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
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

    private class GetNormalizeContactsTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*
            We get all contacts from a Utils class containing common functions.
            */
            Utils utils = new Utils();
            allContactsList = utils.getAllContactsFromPhone(getContext());
        }

        @Override
        protected List<String> doInBackground(Void... params) {

            /*
            Normalizing all the strings by removing any extra spaces that might pop up during contact entries.
            We replace all the spaces with nothing to remove them.
            Then finally, we replace the current value with the new string at the same index to avoid duplication.
            */
            for (int c = 0; c < allContactsList.size(); c++) {
                String contact = allContactsList.get(c);
                contact = contact.replaceAll("\\s+", "");
                allContactsList.add(c, contact);
            }

            for (int i = 0; i < allContactsList.size(); i++) {
                Log.d("ADebug", allContactsList.get(i));
            }

            return allContactsList;
        }

    }
}
