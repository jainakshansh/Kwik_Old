package me.akshanshjain.kwik.Activities;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import me.akshanshjain.kwik.Fragments.OverviewPlanFragment;
import me.akshanshjain.kwik.Fragments.WhatPlanFragment;
import me.akshanshjain.kwik.Fragments.WhenPlanFragment;
import me.akshanshjain.kwik.Fragments.WherePlanFragment;
import me.akshanshjain.kwik.Fragments.WhoPlanFragment;
import me.akshanshjain.kwik.Interfaces.OnFragmentInteractionListener;
import me.akshanshjain.kwik.Interfaces.WhoFragmentInteractionListener;
import me.akshanshjain.kwik.R;

public class CreateEventActivity extends AppCompatActivity implements OnFragmentInteractionListener, WhoFragmentInteractionListener {

    private String whatPlan, whenPlan, wherePlan;
    private FragmentManager fragmentManager;

    private int fragmentStack = 0;

    private static final String WHAT_KEY = "WHAT";
    private static final String WHEN_KEY = "WHEN";
    private static final String WHERE_KEY = "WHERE";
    private static final String WHO_KEY = "WHO";
    private static final String WHO_NAMES_KEY = "NAMES";

    private ArrayList<String> allContactsList;
    private ArrayList<String> registeredList;
    private DatabaseReference registeredUsers;
    private ArrayList<String> contactNamesList;

    private ArrayList<String> selectedContactsList;

    private static final String ALL_CONTACTS_KEY = "ALL_CONTACTS";
    private static final String REGISTERED_CONTACTS_KEY = "REGISTERED_CONTACTS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        //Creating an instance of the first fragment.
        WhatPlanFragment whatPlanFragment = new WhatPlanFragment();

        //Using a Fragment Manager & Transaction to add & remove required fragments to & from the screen.
        fragmentManager = getSupportFragmentManager();

        //Fragment Transaction.
        fragmentManager.beginTransaction()
                .add(R.id.plan_creation_container, whatPlanFragment)
                .commit();

        allContactsList = new ArrayList<>();
        registeredList = new ArrayList<>();
        selectedContactsList = new ArrayList<>();

        /*
        We are performing all the operation of retrieving user contacts using an Async Task.
        This does not bug the UI or make it unresponsive.
        */
        new GetNormalizeContactsTask().execute();
    }

    @Override
    public void onFragmentInteraction(String data) {

        /*
        Incrementing the stack value to replace the current fragment with a new one.
        This happens on interaction with the fragment views.
        */
        fragmentStack++;

        /*
        This helps to replace the fragment based on the interaction and fragment stack value.
        The click listeners are handled in a way so as to not break the flow of event creation.
        */
        switch (fragmentStack) {

            case 1:
                WhenPlanFragment whenPlanFragment = new WhenPlanFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.plan_creation_container, whenPlanFragment)
                        .commit();
                whatPlan = data;
                break;

            case 2:
                WherePlanFragment wherePlanFragment = new WherePlanFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.plan_creation_container, wherePlanFragment)
                        .commit();
                whenPlan = data;
                break;

            case 3:
                /*
                Creating a bundle of contacts lists to be passed to the WhoFragment.
                Getting the data early on already so that there is no delay.
                */
                Bundle listBundle = new Bundle();
                listBundle.putStringArrayList(ALL_CONTACTS_KEY, allContactsList);
                listBundle.putStringArrayList(REGISTERED_CONTACTS_KEY, registeredList);
                listBundle.putStringArrayList(WHO_NAMES_KEY, contactNamesList);

                //Passing the bundle with lists as an argument to the fragment.
                WhoPlanFragment whoPlanFragment = new WhoPlanFragment();
                whoPlanFragment.setArguments(listBundle);

                fragmentManager.beginTransaction()
                        .replace(R.id.plan_creation_container, whoPlanFragment)
                        .commit();
                wherePlan = data;
                break;

            case 4:
                break;

            default:
                /*
                If there's an error, we start again.
                This condition won't be switched to majority of the time.
                */
                fragmentStack = 0;
                WhatPlanFragment whatPlanFragment = new WhatPlanFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.plan_creation_container, whatPlanFragment)
                        .commit();
                break;
        }
    }

    @Override
    public void onContactsSelection(ArrayList<String> selectedContacts) {
        selectedContactsList.clear();
        selectedContactsList.addAll(selectedContacts);

        fragmentStack++;

        if (fragmentStack == 4) {
            /*
            Creating a bundle of strings to be passed to the overview fragment.
            */
            Bundle bundle = new Bundle();
            bundle.putString(WHAT_KEY, whatPlan);
            bundle.putString(WHEN_KEY, whenPlan);
            bundle.putString(WHERE_KEY, wherePlan);
            bundle.putStringArrayList(WHO_KEY, selectedContacts);
            bundle.putStringArrayList(WHO_NAMES_KEY, contactNamesList);

            //Passing the bundle as an argument to the fragment.
            OverviewPlanFragment overviewPlanFragment = new OverviewPlanFragment();
            overviewPlanFragment.setArguments(bundle);

            fragmentManager.beginTransaction()
                    .replace(R.id.plan_creation_container, overviewPlanFragment)
                    .commit();
        }
    }

    /*
    Running an AsyncTask in the background to get all the contacts list.
    Also getting all the registered users from the Firebase Database.
    All these information will be passed to the WhoFragment.
    Carrying the operation out in the activity directly so that there is no delay when the fragment is instantiated.
    */
    private class GetNormalizeContactsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            /*
            We get all contacts from a Utils class containing common functions.
            */
            allContactsList = getAllContactsFromPhone();

            /*
            Normalizing all the strings by removing any extra spaces that might pop up during contact entries.
            We replace all the spaces with nothing to nullify them.
            Then finally, we replace the current value with the new string at the same index to avoid duplication.
            */
            List<String> normalizedList = new ArrayList<>();
            for (int c = 0; c < allContactsList.size(); c++) {
                String contact = allContactsList.get(c);
                contact = contact.replaceAll("\\s+", "");
                normalizedList.add(contact);
            }

            allContactsList.clear();
            allContactsList.addAll(normalizedList);

            /*
            Getting the reference from the Firebase Database and getting all the registered numbers.
            These are then checked against the contacts.
            The final contacts are then added to the common contacts list.
            */
            registeredUsers = FirebaseDatabase.getInstance().getReference().child("registered_numbers");
            registeredUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot post : dataSnapshot.getChildren()) {
                        registeredList.add(post.getKey());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

            return null;
        }
    }


    /*
    This function gets all the contacts from the user's directory along with their names.
    We just return the list of numbers, but names are passed to the Fragment as required.
    */
    public ArrayList<String> getAllContactsFromPhone() {

        ArrayList<String> allContacts = new ArrayList<>();
        contactNamesList = new ArrayList<>();

        ContentResolver contentResolver = getContentResolver();

        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                    Cursor pCur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String contactNum = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String contactName = pCur.getString(pCur.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));

                        allContacts.add(contactNum);
                        contactNamesList.add(contactName);
                    }
                    pCur.close();
                }

            } while (cursor.moveToNext());
        }

        return allContacts;
    }
}
