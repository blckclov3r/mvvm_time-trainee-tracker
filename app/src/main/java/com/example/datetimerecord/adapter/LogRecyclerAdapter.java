package com.example.datetimerecord.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.datetimerecord.R;
import com.example.datetimerecord.model.AppLog;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class LogRecyclerAdapter extends ListAdapter<AppLog,LogRecyclerAdapter.ViewHolder> {


    public LogRecyclerAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback DIFF_CALLBACK = new DiffUtil.ItemCallback<AppLog>(){

        @Override
        public boolean areItemsTheSame(@NonNull AppLog oldItem, @NonNull AppLog newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull AppLog oldItem, @NonNull AppLog newItem) {
            return oldItem.getMessage().equals(newItem.getMessage()) && oldItem.getTimestamp().equals(newItem.getTimestamp());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppLog appLog = getItem(position);
        String message = appLog.getMessage();
        String timestamp = appLog.getTimestamp();
        holder.message_tv.setText(message);
        holder.timestamp_tv.setText(timestamp);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView message_tv,timestamp_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message_tv = itemView.findViewById(R.id.log_message_textView);
            timestamp_tv = itemView.findViewById(R.id.log_timestamp_textView);
        }
    }
}
