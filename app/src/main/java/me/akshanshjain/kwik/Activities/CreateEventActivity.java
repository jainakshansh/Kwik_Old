package me.akshanshjain.kwik.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import me.akshanshjain.kwik.Fragments.OverviewPlanFragment;
import me.akshanshjain.kwik.Fragments.WhatPlanFragment;
import me.akshanshjain.kwik.Fragments.WhenPlanFragment;
import me.akshanshjain.kwik.Fragments.WherePlanFragment;
import me.akshanshjain.kwik.Fragments.WhoPlanFragment;
import me.akshanshjain.kwik.Interfaces.OnFragmentInteractionListener;
import me.akshanshjain.kwik.R;

public class CreateEventActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private String whatPlan, whenPlan, wherePlan, whoPlan;
    private FragmentManager fragmentManager;

    private int fragmentStack = 0;

    private static final String WHAT_KEY = "WHAT";
    private static final String WHEN_KEY = "WHEN";
    private static final String WHERE_KEY = "WHERE";
    private static final String WHO_KEY = "WHO";

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
                WhoPlanFragment whoPlanFragment = new WhoPlanFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.plan_creation_container, whoPlanFragment)
                        .commit();
                wherePlan = data;
                break;

            case 4:
                Bundle bundle = new Bundle();
                bundle.putString(WHAT_KEY, whatPlan);
                bundle.putString(WHEN_KEY, whenPlan);
                bundle.putString(WHERE_KEY, wherePlan);

                OverviewPlanFragment overviewPlanFragment = new OverviewPlanFragment();
                overviewPlanFragment.setArguments(bundle);

                fragmentManager.beginTransaction()
                        .replace(R.id.plan_creation_container, overviewPlanFragment)
                        .commit();
                whoPlan = data;
                break;

            default:
                /*
                If there's an error, we start again.
                This condition won't be switched to most of the times.
                */
                fragmentStack = 0;
                WhatPlanFragment whatPlanFragment = new WhatPlanFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.plan_creation_container, whatPlanFragment)
                        .commit();
                break;
        }
    }
}