package com.example.datetimerecord.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.datetimerecord.R;
import com.example.datetimerecord.adapter.CourseRecyclerAdapter;
import com.example.datetimerecord.model.Course;
import com.example.datetimerecord.viewmodel.CourseViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CourseListFragment extends Fragment {
    private static final String TAG = "CourseListFragment";
    public static final String COMMON_TAG = "mAppLog";
    private RecyclerView mRecyclerView;
    private CourseRecyclerAdapter mCourseAdapter;
    private CourseViewModel mCourseViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_list_fragment,container,false);
        Log.d(COMMON_TAG,TAG+" onCreateView invoked");
        mRecyclerView = view.findViewById(R.id.course_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mCourseAdapter = new CourseRecyclerAdapter();
        mRecyclerView.setAdapter(mCourseAdapter);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(COMMON_TAG,TAG+" onViewCreated invoked");
        mCourseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        mCourseViewModel.getAllCourse().observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                mCourseAdapter.setCourseList(courses);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(COMMON_TAG,TAG+" onStart invoked");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(COMMON_TAG,TAG+" onDestroy invoked");
        mCourseViewModel = null;
        mRecyclerView = null;
        mCourseAdapter = null;
    }
}
