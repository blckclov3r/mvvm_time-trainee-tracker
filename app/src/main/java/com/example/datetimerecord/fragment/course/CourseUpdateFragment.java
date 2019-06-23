package com.example.datetimerecord.fragment.course;

import android.os.Bundle;
import android.util.Log;
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
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

public class CourseUpdateFragment extends DialogFragment {

    private static final String TAG = "CourseUpdateFragment";
    public static final String COMMON_TAG = "mAppLog";

    private EditText course_et;
    private EditText time_et;
    private EditText description_et;
    private Button update_btn;
    private Course mCourse;
    private CourseViewModel mCourseViewModel;

    public CourseUpdateFragment(){}

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
        update_btn = view.findViewById(R.id.update_button);
        mCourseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        Log.d(COMMON_TAG,TAG+" onCreateView");
        if(getArguments() != null){
            mCourse = getArguments().getParcelable("selected_course");
            course_et.setText(mCourse.getCourse());
            time_et.setText(String.valueOf(mCourse.getCourse_time()));
            description_et.setText(mCourse.getDescription());
        }
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String course = course_et.getText().toString().trim();
                String time = time_et.getText().toString().trim();
                String description = description_et.getText().toString().trim();
                Course c = new Course(course,Integer.parseInt(time),description);
                if(mCourse.getC_id() > 0) {
                    c.setC_id(mCourse.getC_id()); //must include the id
                    mCourseViewModel.update(c);
                    Toast.makeText(getActivity(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Something wen't wrong", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        return view;
    }



}
