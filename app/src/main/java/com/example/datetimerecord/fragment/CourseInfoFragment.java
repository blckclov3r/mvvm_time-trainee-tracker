package com.example.datetimerecord.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.datetimerecord.R;
import com.example.datetimerecord.model.Course;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CourseInfoFragment extends Fragment {

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
        if(getArguments() != null){
            Course course = getArguments().getParcelable("selected_course");
            Toast.makeText(getActivity(), "course: "+course.toString(), Toast.LENGTH_SHORT).show();
        }
        return view;
    }


}
