package com.example.datetimerecord.fragment.course;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.datetimerecord.R;
import com.example.datetimerecord.model.Course;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CourseDetailFragment extends Fragment {
    private static final String COMMON_TAG = "mAppLog";
    private static final String TAG = "CourseDetailFragment";
    private TextView id_tv,course_tv,time_tv,desc_tv;

    public CourseDetailFragment(){}

    public static CourseDetailFragment newInstance(Course course){
        CourseDetailFragment fragment = new CourseDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("selected_course",course);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.course_detail_fragment,container,false);
        Log.d(COMMON_TAG,TAG+" onCreateView");
        id_tv = view.findViewById(R.id.id_textView);
        course_tv = view.findViewById(R.id.course_textView);
        time_tv = view.findViewById(R.id.time_textView);
        desc_tv = view.findViewById(R.id.description_textView);
        if(getArguments() != null){
            Course course = getArguments().getParcelable("selected_course");
            id_tv.setText(String.valueOf(course.getC_id()));
            course_tv.setText(" "+course.getCourse());
            time_tv.setText(" "+String.valueOf(course.getCourse_time()));
            desc_tv.setText(course.getDescription());
        }
        return view;
    }


}
