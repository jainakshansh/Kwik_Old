package me.akshanshjain.kwik.Adapters;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import me.akshanshjain.kwik.Fragments.EventDetailFragment;
import me.akshanshjain.kwik.Fragments.EventUpdatesFragment;
import me.akshanshjain.kwik.Objects.EventItem;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    private EventItem eventItem;
    private static final String EVENT_KEY = "EVENT";

    public ViewPagerAdapter(FragmentManager fragmentManager, EventItem eventItem) {
        super(fragmentManager);
        this.eventItem = eventItem;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Bundle detailBundle = new Bundle();
                detailBundle.putParcelable(EVENT_KEY, eventItem);
                EventDetailFragment eventDetailFragment = new EventDetailFragment();
                eventDetailFragment.setArguments(detailBundle);
                return eventDetailFragment;
            case 1:
                Bundle updateBundle = new Bundle();
                updateBundle.putParcelable(EVENT_KEY, eventItem);
                EventUpdatesFragment eventUpdatesFragment = new EventUpdatesFragment();
                eventUpdatesFragment.setArguments(updateBundle);
                return eventUpdatesFragment;
        }
        return new EventUpdatesFragment();
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }
}
