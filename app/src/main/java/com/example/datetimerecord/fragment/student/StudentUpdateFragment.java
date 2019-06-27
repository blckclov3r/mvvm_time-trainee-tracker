package com.example.datetimerecord.fragment.student;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.datetimerecord.R;
import com.example.datetimerecord.model.AppLog;
import com.example.datetimerecord.model.Student;
import com.example.datetimerecord.utils.LineEditText;
import com.example.datetimerecord.viewmodel.LogViewModel;
import com.example.datetimerecord.viewmodel.StudentViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class StudentUpdateFragment extends Fragment {

    private static final String COMMON_TAG = "mAppLog";
    private static final String TAG = "StudentUpdateFragment";

    //components
    private EditText name_et, email_et, contact_et, course_et;
    private LineEditText address_et;
    private Button studentUpdate_btn;

    //vars
    private StudentViewModel mStudentViewModel;
    private LogViewModel mLogViewModel;

    public static StudentUpdateFragment newInstance(Student student) {
        StudentUpdateFragment fragment = new StudentUpdateFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("selected_student", student);
        fragment.setArguments(bundle);
        return fragment;
    }

    public StudentUpdateFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_update_fragment, container, false);
        name_et = view.findViewById(R.id.name_editText);
        email_et = view.findViewById(R.id.email_editText);
        contact_et = view.findViewById(R.id.contact_editText);
        address_et = view.findViewById(R.id.address_editText);
        studentUpdate_btn = view.findViewById(R.id.studentUpdatet_button);
        course_et = view.findViewById(R.id.course_editText);
        mStudentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        mLogViewModel = ViewModelProviders.of(this).get(LogViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            Student student = getArguments().getParcelable("selected_student");
            name_et.setText(Objects.requireNonNull(student).getName());
            email_et.setText(student.getEmail());
            contact_et.setText(String.valueOf(student.getContact()));
            address_et.setText(student.getAddress());
            course_et.setText(student.getCourse());
        }




        studentUpdate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(Objects.requireNonNull(getActivity()), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("Student Update")
                        .setContentText("Are you sure?")
                        .setConfirmText("Yes")
                        .setCustomImage(R.drawable.user)
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                if (getArguments() != null) {
                                    int timein_hour, timein_minute;
                                    int timeout_hour, timeout_minute;
                                    Student pStudent = getArguments().getParcelable("selected_student");
                                    String name = name_et.getText().toString().trim();
                                    String course = course_et.getText().toString().trim();
                                    String email = email_et.getText().toString().trim();
                                    String contact = contact_et.getText().toString().trim();
                                    String address = address_et.getText().toString().trim();
                                    String timestamp = Objects.requireNonNull(pStudent).getTimestamp();
                                    int remaining = pStudent.getRemaining();

                                    //timein and timeout
                                    timein_hour = pStudent.getTimein_hour();
                                    timein_minute = pStudent.getTimein_minute();
                                    timeout_hour = pStudent.getTimeout_hour();
                                    timeout_minute = pStudent.getTimeout_minute();


                                    Student student = new Student(name, course, email, contact, address, timestamp, remaining, timein_hour, timein_minute, timeout_hour, timeout_minute);
                                    student.setT_id(pStudent.getT_id());
                                    mStudentViewModel.update(student);
                                    studentUpdate_btn.setClickable(false);
                                    studentUpdate_btn.setEnabled(false);
                                    studentUpdate_btn.setTextColor(Color.GRAY);

                                    new SweetAlertDialog(Objects.requireNonNull(getActivity()), SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Student Update")
                                            .setContentText("Student succesffully updated")
                                            .show();

                                    Log.d(COMMON_TAG, TAG + " student update log: " + student.toString());
                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                                    String dateFormat = simpleDateFormat.format(new Date());
                                    mLogViewModel.insert(new AppLog("Student successfully Updated id: "+pStudent.getT_id(),dateFormat));

                                } else {
                                    Toast.makeText(getActivity(), "something wen't wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .show();



            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        name_et = null;
        email_et = null;
        contact_et = null;
        address_et = null;
        course_et = null;
        studentUpdate_btn = null;
        mStudentViewModel = null;
    }
}
