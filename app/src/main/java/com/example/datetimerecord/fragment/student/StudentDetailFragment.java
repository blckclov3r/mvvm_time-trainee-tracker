package com.example.datetimerecord.fragment.student;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datetimerecord.R;
import com.example.datetimerecord.model.Student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StudentDetailFragment extends Fragment {
    private static final String COMMON_TAG = "mAppLog";
    private static final String TAG = "StudentDetailFragment";
    private TextView name_tv, course_tv, email_tv, contact_tv, address_tv, timestamp_tv, id_tv, remaining_tv;
    private Button timeUpdate_btn;

    //vars
    public StudentDetailFragment() {
    }

    public static StudentDetailFragment newInstance(Student student) {
        StudentDetailFragment fragment = new StudentDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("selected_student", student);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_detail_fragment, container, false);
        id_tv = view.findViewById(R.id.id_textView);
        name_tv = view.findViewById(R.id.name_textView);
        course_tv = view.findViewById(R.id.course_textView);
        email_tv = view.findViewById(R.id.email_textView);
        contact_tv = view.findViewById(R.id.contact_textView);
        address_tv = view.findViewById(R.id.address_textView);
        timestamp_tv = view.findViewById(R.id.timestamp_textView);
        remaining_tv = view.findViewById(R.id.remaining_textView);
        timeUpdate_btn = view.findViewById(R.id.timeUpdate_button);

        timeUpdate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments() != null) {
                    Student student = getArguments().getParcelable("selected_student");
                    listener.onStudentDetailFragment(student);
                } else {
                    Log.d(COMMON_TAG, TAG + " timeUpdate_btn: something wen't wrong");
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            Student student = getArguments().getParcelable("selected_student");
//            AppLog.d(COMMON_TAG, TAG + " student: " + student.toString());
            id_tv.setText(String.valueOf(student.getT_id()));
            name_tv.setText(student.getName());
            course_tv.setText(student.getCourse());
            email_tv.setText(student.getEmail());
            contact_tv.setText(String.valueOf(student.getContact()));
            address_tv.setText(student.getAddress());
            timestamp_tv.setText(student.getTimestamp());
            remaining_tv.setText(String.valueOf(student.getRemaining()));
        }
    }


    private setOnUpdateListener listener;

    public interface setOnUpdateListener {
        void onStudentDetailFragment(Student student);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof setOnUpdateListener) {
            listener = (setOnUpdateListener) getActivity();
        } else {
            throw new RuntimeException(context.toString() + " must implement setOnUpdateListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (listener != null) {
            listener = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        name_tv=null;
        course_tv=null;
        email_tv=null;
        contact_tv=null;
        address_tv=null;
        timestamp_tv=null;
        id_tv=null;
        remaining_tv=null;
        timeUpdate_btn=null;
    }
}
