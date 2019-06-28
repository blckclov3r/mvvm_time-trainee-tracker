package com.example.datetimerecord.fragment.log;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.datetimerecord.R;
import com.example.datetimerecord.adapter.LogRecyclerAdapter;
import com.example.datetimerecord.model.AppLog;
import com.example.datetimerecord.viewmodel.LogViewModel;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LogFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.log_fragment,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.log_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        final LogRecyclerAdapter logAdapter = new LogRecyclerAdapter();
        recyclerView.setAdapter(logAdapter);

        LogViewModel mLogViewModel = ViewModelProviders.of(this).get(LogViewModel.class);
        mLogViewModel.getAllLog().observe(getViewLifecycleOwner(), new Observer<List<AppLog>>() {
            @Override
            public void onChanged(List<AppLog> appLogs) {
                logAdapter.submitList(appLogs);
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
