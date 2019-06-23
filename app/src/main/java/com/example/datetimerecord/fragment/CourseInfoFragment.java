package com.example.datetimerecord.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datetimerecord.R;
import com.example.datetimerecord.model.Course;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CourseInfoFragment extends Fragment {

    private TextView id_tv,course_tv,time_tv,desc_tv;

    public static CourseInfoFragment newInstance(Course course){
        CourseInfoFragment fragment = new CourseInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable("selected_course",course);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.course_info_fragment,container,false);
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
