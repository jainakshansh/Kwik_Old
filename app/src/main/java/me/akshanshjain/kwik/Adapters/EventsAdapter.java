package me.akshanshjain.kwik.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import me.akshanshjain.kwik.Objects.EventItem;
import me.akshanshjain.kwik.R;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    private Context context;
    private List<EventItem> eventItemList;
    private ItemClickListener clickListener;

    public EventsAdapter(Context context, List<EventItem> eventItemList, ItemClickListener clickListener) {
        this.context = context;
        this.eventItemList = eventItemList;
        this.clickListener = clickListener;
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView eventName, eventDescription, eventDateTime, isEventHost;
        private ConstraintLayout eventParent;

        public EventsViewHolder(View itemView) {
            super(itemView);

            //Referencing the views from XML layout.
            eventName = itemView.findViewById(R.id.event_name);
            eventDescription = itemView.findViewById(R.id.event_description);
            eventDateTime = itemView.findViewById(R.id.event_date_time);
            isEventHost = itemView.findViewById(R.id.event_hosting);

            eventParent = itemView.findViewById(R.id.event_parent);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            String key = eventItemList.get(clickedPosition).getEventKey();
            clickListener.onItemClickListener(key);
        }
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflating the single event XML layout into the view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_main, parent, false);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {
        //Getting the event object from the list.
        EventItem eventItem = eventItemList.get(position);

        /*
        Using the getters to get individual attributes from the object.
        Then setting the attribute values into the views.
        */
        holder.eventName.setText(eventItem.getEventName());

        holder.eventDescription.setText(eventItem.getEventDescription());

        holder.eventDateTime.setText(eventItem.getEventDate() + "\n" + eventItem.getEventTime());

        if (eventItem.isHosting()) {
            holder.isEventHost.setText(context.getString(R.string.hosting));
        } else {
            holder.isEventHost.setText(context.getString(R.string.invited));
        }

        //Setting a random background on the card parent.
        int[] gradients = new int[]{R.drawable.gradient_blue, R.drawable.gradient_gray, R.drawable.gradient_green,
                R.drawable.gradient_maroon, R.drawable.gradient_orange, R.drawable.gradient_purple};
        Random random = new Random();

        holder.eventParent.setBackgroundResource(gradients[random.nextInt(gradients.length)]);
    }

    @Override
    public int getItemCount() {
        //Returns the total count of event objects in the list.
        return eventItemList.size();
    }

    public interface ItemClickListener {

        void onItemClickListener(String key);
    }
}
