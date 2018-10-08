package me.akshanshjain.kwik.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.akshanshjain.kwik.Objects.EventItem;
import me.akshanshjain.kwik.R;

public class EventDetailFragment extends Fragment {

    private TextView eventName, eventDescription;
    private TextView eventDateTime, eventLocation;
    private LinearLayout attendeesContainer;

    private ImageView background;
    private Drawable[] backgroundDrawables;

    private EventItem eventItem;
    private static final String EVENT_KEY = "EVENT";

    private List<String> eventAttendeesList;

    /*
    Mandatory constructor for instantiating the fragment.
    */
    public EventDetailFragment() {
    }

    /*
    Inflating the fragment layout and performs the required operations or functions.
    */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflating the layout from the XML.
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_event_detail, container, false);

        if (getArguments() != null) {
            eventItem = getArguments().getParcelable(EVENT_KEY);
        }

        eventAttendeesList = new ArrayList<>();

        eventName = view.findViewById(R.id.event_name_details);
        eventDescription = view.findViewById(R.id.event_description_details);
        eventDateTime = view.findViewById(R.id.event_date_time_details);
        eventLocation = view.findViewById(R.id.event_location_details);
        attendeesContainer = view.findViewById(R.id.attendees_container_details);

        eventName.setText(eventItem.getEventName());
        eventDescription.setText(eventItem.getEventDescription());
        String dateTime = eventItem.getEventDate() + " ● " + eventItem.getEventTime();
        eventDateTime.setText(dateTime);
        eventLocation.setText(eventItem.getEventLocation());

        eventAttendeesList = eventItem.getEventAttendees();
        addAttendeesToContainer();

        background = view.findViewById(R.id.background_event_details);

        /*
        Getting all the drawables into an array to set as dynamic background for account page.
        */
        backgroundDrawables = new Drawable[]{ContextCompat.getDrawable(getActivity(), R.drawable.celebration),
                ContextCompat.getDrawable(getActivity(), R.drawable.refreshing),
                ContextCompat.getDrawable(getActivity(), R.drawable.hang_out),
                ContextCompat.getDrawable(getActivity(), R.drawable.park),
                ContextCompat.getDrawable(getActivity(), R.drawable.selfie),
                ContextCompat.getDrawable(getActivity(), R.drawable.trip)};

        /*
        Setting the background as a random drawable image.
        */
        Random random = new Random();
        int bg = random.nextInt(backgroundDrawables.length);
        background.setImageDrawable(backgroundDrawables[bg]);


        return view;
    }

    private void addAttendeesToContainer() {
        if (eventAttendeesList != null) {
            if (eventAttendeesList.size() > 3) {

                for (int i = 0; i < eventAttendeesList.size(); i++) {
                    addingViewsToContainer(i);
                }

                int remaining = eventAttendeesList.size() - 3;
                TextView textView = new TextView(getContext());
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.materialBlack));
                textView.setText("+ " + remaining);

            } else {

                for (int i = 0; i < eventAttendeesList.size(); i++) {
                    addingViewsToContainer(i);
                }
            }
        }
    }

    private void addingViewsToContainer(int position) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 8, 8, 8);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        imageView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.circular_white_background));
        imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_user));
        imageView.setPadding(16, 16, 16, 16);

        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.materialBlack));
        textView.setText(eventAttendeesList.get(position));

        linearLayout.addView(imageView);
        linearLayout.addView(textView);
        attendeesContainer.addView(linearLayout);
    }
}
