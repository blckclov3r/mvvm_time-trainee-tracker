package com.example.datetimerecord.fragment.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.datetimerecord.R;
import com.example.datetimerecord.adapter.StudentRecyclerAdapter;
import com.example.datetimerecord.model.Student;
import com.example.datetimerecord.viewmodel.StudentViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StudentListFragment extends Fragment {

    private static final String COMMON_TAG = "mAppLog";
    private static final String TAG = "StudentListFragment";
    private RecyclerView mRecyclerView;
    private StudentRecyclerAdapter mAdapter;
    private StudentViewModel mStudentViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_list_fragment,container,false);
        mRecyclerView = view.findViewById(R.id.student_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new StudentRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStudentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        mStudentViewModel.getmAllStudents().observe(getViewLifecycleOwner(), new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                mAdapter.setmStudentList(students);
            }
        });
    }
}
