package com.example.datetimerecord.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.datetimerecord.R;
import com.example.datetimerecord.model.AppLog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LogRecyclerAdapter extends RecyclerView.Adapter<LogRecyclerAdapter.ViewHolder> {

    private List<AppLog> appLogList = new ArrayList<>();

    public void setAppLogList(List<AppLog> appLogList){
        this.appLogList = appLogList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppLog appLog = appLogList.get(position);
        String message = appLog.getMessage();
        String timestamp = appLog.getTimestamp();
        holder.message_tv.setText(message);
        holder.timestamp_tv.setText(timestamp);
    }

    @Override
    public int getItemCount() {
        return appLogList.size();
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
