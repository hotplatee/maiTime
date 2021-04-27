package de.matmar.maitime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimeEntryListAdapter extends RecyclerView.Adapter<TimeEntryListAdapter.TimeEntryViewHolder> {
    private final LayoutInflater mInflater;
    private List<TimeEntry> timeEntryList; // Cached copy of timeEntry

    TimeEntryListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public TimeEntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TimeEntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TimeEntryViewHolder holder, int position) {
        if (timeEntryList != null) {
            TimeEntry current = timeEntryList.get(position);
            holder.timeEntryItemView.setText(current.getTimeEntry());
        } else {
            // Covers the case of data not being ready yet.
            holder.timeEntryItemView.setText("No Time Entry");
        }
    }

    void setTimeEntry(List<TimeEntry> timeEntries){
        timeEntryList = timeEntries;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // timeEntryList has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (timeEntryList != null)
            return timeEntryList.size();
        else return 0;
    }

    class TimeEntryViewHolder extends RecyclerView.ViewHolder {
        private final TextView timeEntryItemView;

        private TimeEntryViewHolder(View itemView) {
            super(itemView);
            timeEntryItemView = itemView.findViewById(R.id.textView);
        }
    }
}
