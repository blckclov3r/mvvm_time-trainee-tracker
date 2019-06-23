package com.example.datetimerecord.fragment.course;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.datetimerecord.R;
import com.example.datetimerecord.model.Course;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CourseUpdateFragment extends DialogFragment {

    private static final String TAG = "CourseUpdateFragment";
    public static final String COMMON_TAG = "mAppLog";

    private EditText course_et;
    private EditText time_et;
    private EditText description_et;

    public static CourseUpdateFragment newInstance(Course course){
        CourseUpdateFragment fragment = new CourseUpdateFragment();
        Bundle args = new Bundle();
        args.putParcelable("selected_course",course);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_update_fragment,container,false);
        course_et = view.findViewById(R.id.course_editText);
        time_et = view.findViewById(R.id.time_editText);
        description_et = view.findViewById(R.id.description_editText);

        Log.d(COMMON_TAG,TAG+" onCreateView");
        if(getArguments() != null){
            Course course = getArguments().getParcelable("selected_course");
            course_et.setText(course.getCourse());
            time_et.setText(String.valueOf(course.getCourse_time()));
            description_et.setText(course.getDescription());
        }
        return view;
    }



}
