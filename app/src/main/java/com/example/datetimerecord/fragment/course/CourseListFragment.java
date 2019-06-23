package com.example.datetimerecord.fragment.course;

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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CourseListFragment extends Fragment implements CourseRecyclerAdapter.OnCourseClickListener {
    private static final String TAG = "CourseListFragment";
    public static final String COMMON_TAG = "mAppLog";
    private RecyclerView mRecyclerView;
    private CourseRecyclerAdapter mCourseAdapter;
    private CourseViewModel mCourseViewModel;

    public CourseListFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_list_fragment,container,false);
        Log.d(COMMON_TAG,TAG+" onCreateView invoked");
        mRecyclerView = view.findViewById(R.id.course_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mCourseAdapter = new CourseRecyclerAdapter();
        mCourseAdapter.setOnCourseClickListener(this);
        mRecyclerView.setAdapter(mCourseAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                mCourseViewModel.delete(mCourseAdapter.getNoteAt(position));
                Toast.makeText(getActivity(), "Course successfully deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(mRecyclerView);

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


    @Override
    public void onCourseClick(Course course) {
        listFragmentListener.OnCourseListFragment(course);
    }

    @Override
    public void onCourseLongClick(Course course) {
       listFragmentListener.OnLongClickCourseListFragment(course);
    }

    public interface OnCourseListFragmentListener{
        void OnCourseListFragment(Course course);
        void OnLongClickCourseListFragment(Course course);
    }
    private OnCourseListFragmentListener listFragmentListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnCourseListFragmentListener){
            listFragmentListener = (OnCourseListFragmentListener) context;
        }else{
            throw new RuntimeException(context.toString()+" must implement OnCourseListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(COMMON_TAG,TAG+" onDetach");
        if(listFragmentListener != null) {
            listFragmentListener = null;
        }
    }

    public void setOnCourseListFragmentListener(OnCourseListFragmentListener listener){
        listFragmentListener = listener;
    }
}
