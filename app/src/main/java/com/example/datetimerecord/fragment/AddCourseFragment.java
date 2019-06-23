package com.example.datetimerecord.fragment;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.datetimerecord.R;
import com.example.datetimerecord.model.Course;
import com.example.datetimerecord.viewmodel.CourseViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class AddCourseFragment extends Fragment implements View.OnClickListener {
    private EditText course_et,time_et,description_et;
    private Button addCourse_btn;
    private Course mCourse;
    private CourseViewModel mViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_course_fragment,container,false);
        course_et = view.findViewById(R.id.course_editText);
        time_et = view.findViewById(R.id.time_editText);
        description_et = view.findViewById(R.id.description_editText);
        addCourse_btn = view.findViewById(R.id.addCourse_button);
        mViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        addCourse_btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        String course = course_et.getText().toString().trim();
        String courseTime = time_et.getText().toString();
        String desc = description_et.getText().toString().trim();
        if(course.isEmpty()){
            Toast.makeText(getActivity(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
            course_et.requestFocus();
            return;
        }
        if(courseTime.isEmpty()){
            Toast.makeText(getActivity(), "Time is required", Toast.LENGTH_SHORT).show();
            time_et.requestFocus();
            return;
        }
        if(desc.isEmpty()){
            desc = "Empty";
        }
        int time = Integer.parseInt(courseTime);
        mCourse = new Course(course,time,desc);
        mViewModel.insert(mCourse);
        setClear();
        Toast.makeText(getActivity(), "Course successfully created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel = null;
        course_et = null;
        time_et = null;
        description_et = null;
    }

    public void setClear(){
        course_et.setText("");
        time_et.setText("");
        description_et.setText("");
    }
}
