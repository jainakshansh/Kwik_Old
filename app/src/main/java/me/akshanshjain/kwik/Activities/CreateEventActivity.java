package me.akshanshjain.kwik.Activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import me.akshanshjain.kwik.Fragments.WhatPlanFragment;
import me.akshanshjain.kwik.R;

public class CreateEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        //Creating an instance of a What Plan Fragment.
        WhatPlanFragment whatPlanFragment = new WhatPlanFragment();

        //Using a Fragment Manager and transaction to add the fragment to the screen.
        FragmentManager fragmentManager = getSupportFragmentManager();

        //Fragment Transaction.
        fragmentManager.beginTransaction()
                .add(R.id.plan_creation_container, whatPlanFragment)
                .commit();
    }
}