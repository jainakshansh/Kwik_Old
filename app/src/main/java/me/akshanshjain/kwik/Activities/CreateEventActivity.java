package me.akshanshjain.kwik.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import me.akshanshjain.kwik.Fragments.WhatPlanFragment;
import me.akshanshjain.kwik.Fragments.WhenPlanFragment;
import me.akshanshjain.kwik.Fragments.WherePlanFragment;
import me.akshanshjain.kwik.Fragments.WhoPlanFragment;
import me.akshanshjain.kwik.R;

public class CreateEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        //Creating an instance of all the fragments.
        WhatPlanFragment whatPlanFragment = new WhatPlanFragment();
        WhenPlanFragment whenPlanFragment = new WhenPlanFragment();
        WherePlanFragment wherePlanFragment = new WherePlanFragment();
        WhoPlanFragment whoPlanFragment = new WhoPlanFragment();

        //Using a Fragment Manager & Transaction to add & remove required fragments to & from the screen.
        FragmentManager fragmentManager = getSupportFragmentManager();

        //Fragment Transaction.
        fragmentManager.beginTransaction()
                .add(R.id.plan_creation_container, whatPlanFragment)
                .commit();
    }
}