package me.akshanshjain.kwik.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.akshanshjain.kwik.Objects.EventItem;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    private Context context;
    private List<EventItem> eventItemList;

    public EventsAdapter(Context context, List<EventItem> eventItemList) {
        this.context = context;
        this.eventItemList = eventItemList;
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder {

        public EventsViewHolder(View itemView) {
            super(itemView);
        }

    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return eventItemList.size();
    }
}
