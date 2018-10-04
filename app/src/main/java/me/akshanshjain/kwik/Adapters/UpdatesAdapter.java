package me.akshanshjain.kwik.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.akshanshjain.kwik.R;

public class UpdatesAdapter extends RecyclerView.Adapter<UpdatesAdapter.UpdatesViewHolder> {

    private Context context;
    private List<String> updatesList;

    public UpdatesAdapter(Context context, List<String> updatesList) {
        this.context = context;
        this.updatesList = updatesList;
    }

    public class UpdatesViewHolder extends RecyclerView.ViewHolder {

        private TextView updatesTv;

        public UpdatesViewHolder(View view) {
            super(view);
            updatesTv = view.findViewById(R.id.event_updates_tv);
        }
    }

    @NonNull
    @Override
    public UpdatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_event_updates, parent, false);
        return new UpdatesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdatesViewHolder holder, int position) {
        String update = updatesList.get(position);

        holder.updatesTv.setText(update);
    }

    @Override
    public int getItemCount() {
        return updatesList.size();
    }
}
