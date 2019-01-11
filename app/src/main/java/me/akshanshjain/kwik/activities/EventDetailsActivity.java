package me.akshanshjain.kwik.activities;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import me.akshanshjain.kwik.adapters.ViewPagerAdapter;
import me.akshanshjain.kwik.fragments.EventDetailFragment;
import me.akshanshjain.kwik.fragments.EventUpdatesFragment;
import me.akshanshjain.kwik.objects.EventItem;
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
        adapter.addFragment(new EventDetailFragment(), getString(R.string.details));
        adapter.addFragment(new EventUpdatesFragment(), getString(R.string.updates));
        viewPager.setAdapter(adapter);
    }
}
