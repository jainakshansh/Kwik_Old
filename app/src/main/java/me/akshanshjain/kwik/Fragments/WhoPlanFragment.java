package me.akshanshjain.kwik.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
    private ImageView nextButton;
    private LinearLayout invitedContactsContainer;

    private OnFragmentInteractionListener interactionListener;

    private DatabaseReference registeredUsers;

    private List<String> allContactsList;
    private List<String> commonContactsList;

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

        invitedContactsContainer = view.findViewById(R.id.invited_contacts_container);

        /*
        Adding an image dynamically to the linear layout container.
        This will act as a button to display the common contacts list.
        */
        final ImageView inviteContacts = new ImageView(getContext());
        inviteContacts.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_add_contacts));
        inviteContacts.setMinimumHeight(48);
        inviteContacts.setMinimumWidth(48);
        inviteContacts.setMaxWidth(64);
        inviteContacts.setMaxHeight(64);
        invitedContactsContainer.addView(inviteContacts);

        /*
        Creating the empty list which will contain all contacts and common contacts.
        */
        allContactsList = new ArrayList<>();
        commonContactsList = new ArrayList<>();

        /*
        We get all contacts from a Utils class containing common functions.
        */
        Utils utils = new Utils();
        allContactsList = utils.getAllContactsFromPhone(getContext());

        /*
        Getting the reference from the Firebase Database and getting all the registered numbers.
        These are then checked against the contacts.
        The final contacts are then added to the common contacts list.
        */
        registeredUsers = FirebaseDatabase.getInstance().getReference().child("registered_users");
        registeredUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot contact : dataSnapshot.getChildren()) {
                    if (allContactsList.contains(contact.getKey())) {
                        //Getting all the common contacts into a different list.
                        commonContactsList.add(contact.getKey());
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
}
