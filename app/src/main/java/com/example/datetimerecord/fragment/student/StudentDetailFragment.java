package com.example.datetimerecord.fragment.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.datetimerecord.R;
import com.example.datetimerecord.model.Student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StudentDetailFragment extends Fragment {

    private TextView name_tv, course_tv, email_tv, contact_tv, address_tv, timestamp_tv, id_tv,remaining_tv;
    public StudentDetailFragment() {
    }

    public static StudentDetailFragment newInstance(Student student){
        StudentDetailFragment fragment = new StudentDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("selected_student",student);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_detail_fragment,container,false);
        id_tv = view.findViewById(R.id.id_textView);
        name_tv = view.findViewById(R.id.name_textView);
        course_tv = view.findViewById(R.id.course_textView);
        email_tv = view.findViewById(R.id.email_textView);
        contact_tv = view.findViewById(R.id.contact_textView);
        address_tv = view.findViewById(R.id.address_textView);
        timestamp_tv = view.findViewById(R.id.timestamp_textView);
        remaining_tv = view.findViewById(R.id.remaining_textView);

        if(getArguments()!=null){
            Student student = getArguments().getParcelable("selected_student");
            id_tv.setText(String.valueOf(student.getT_id()));
            name_tv.setText(String.valueOf(student.getName()));
            course_tv.setText(String.valueOf(student.getCourse()));
            email_tv.setText(student.getEmail());
            contact_tv.setText(String.valueOf(student.getContact()));
            address_tv.setText(String.valueOf(student.getAddress()));
            timestamp_tv.setText(String.valueOf(student.getTimestamp()));
            remaining_tv.setText(String.valueOf(student.getRemaining()));
        }


        return view;
    }
}
