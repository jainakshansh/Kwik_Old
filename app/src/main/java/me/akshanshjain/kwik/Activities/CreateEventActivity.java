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

        fragmentStack++;

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
                OverviewPlanFragment overviewPlanFragment = new OverviewPlanFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.plan_creation_container, overviewPlanFragment)
                        .commit();
                whoPlan = data;
                break;
            default:
                break;
        }
    }
}