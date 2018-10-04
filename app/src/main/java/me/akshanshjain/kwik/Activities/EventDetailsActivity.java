package me.akshanshjain.kwik.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import me.akshanshjain.kwik.Adapters.ViewPagerAdapter;
import me.akshanshjain.kwik.Fragments.EventDetailFragment;
import me.akshanshjain.kwik.Fragments.EventUpdatesFragment;
import me.akshanshjain.kwik.Objects.EventItem;
import me.akshanshjain.kwik.R;

public class EventDetailsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private EventItem eventItem;
    private static final String EVENT_KEY = "EVENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Toolbar toolbar = findViewById(R.id.toolbar_event_details);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent fromMainIntent = getIntent();
        if (fromMainIntent.getExtras() != null) {
            eventItem = fromMainIntent.getExtras().getParcelable(EVENT_KEY);
            getSupportActionBar().setTitle(eventItem.getEventName());
        }

        viewPager = findViewById(R.id.view_pager_details);
        tabLayout = findViewById(R.id.tab_layout_details);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        Bundle bundle = new Bundle();
        bundle.putParcelable(EVENT_KEY, eventItem);

        EventDetailFragment eventDetailFragment = new EventDetailFragment();
        eventDetailFragment.setArguments(bundle);
    }

    /*
    Adding the fragments to the view pager.
    */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), eventItem);
        adapter.addFragment(new EventDetailFragment(), "Details");
        adapter.addFragment(new EventUpdatesFragment(), "Updates");
        viewPager.setAdapter(adapter);
    }
}
