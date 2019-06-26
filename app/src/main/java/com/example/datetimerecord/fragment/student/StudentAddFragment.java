package com.example.datetimerecord.fragment.student;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.datetimerecord.R;
import com.example.datetimerecord.model.Course;
import com.example.datetimerecord.model.Student;
import com.example.datetimerecord.viewmodel.CourseViewModel;
import com.example.datetimerecord.viewmodel.StudentViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class StudentAddFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "StudentAddFragment";
    private static final String COMMON_TAG = "mAppLog";

    private EditText mName_et, mEmail_et, mContact_et, mAddress_et;
    private Button addStudent_Btn;
    private StudentViewModel mStudentViewModel;
    private Spinner mCourse_spinner;
    private CourseViewModel mCourseViewModel;
    private ArrayAdapter<String> mArrayAdapter;
    ArrayList<String> mArrayList;

    public StudentAddFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_add_fragment, container, false);
        mName_et = view.findViewById(R.id.name_editText);
        mEmail_et = view.findViewById(R.id.email_editText);
        mContact_et = view.findViewById(R.id.contact_editText);
        mAddress_et = view.findViewById(R.id.address_editText);
        addStudent_Btn = view.findViewById(R.id.addStudent_button);
        mCourse_spinner = view.findViewById(R.id.course_spinner);


        addStudent_Btn.setOnClickListener(this);
        mStudentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        mCourseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);


        mCourseViewModel.getAllCourse().observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                mArrayList = new ArrayList<>();
                if (courses.size()>0) {
                    for (int i = 0; i < courses.size(); i++) {
                        Course course = courses.get(i);
                        String course_name = course.getCourse();
                        mArrayList.add(course_name);
                    }
                } else {
                    String course_name = "Empty";
                    mArrayList.add(course_name);
                }
                mArrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_item, mArrayList);
                mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mCourse_spinner.setAdapter(mArrayAdapter);
            }
        });


        return view;
    }

    @Override
    public void onClick(View v) {
        String name = mName_et.getText().toString().trim();
        String course = mCourse_spinner.getSelectedItem().toString().trim();
        String email = mEmail_et.getText().toString().trim();
        String contact = mContact_et.getText().toString().trim();
        String address = mAddress_et.getText().toString().trim();


        if (name.isEmpty()) {
            mName_et.requestFocus();
            Toast("Name is required");
            return;
        }
        if (course.isEmpty() || course.equals("Empty")) {
            Toast("Course is required");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast("The email you entered is invalid");
            mEmail_et.requestFocus();
            return;
        }

        if (!Patterns.PHONE.matcher(contact).matches()) {
            Toast("Contact is invalid");
            mContact_et.requestFocus();
            return;
        }
        if (address.isEmpty()) {
            Toast("Address is required");
            mAddress_et.requestFocus();
            return;
        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String dateFormat = simpleDateFormat.format(new Date());

        Course lCourse = mCourseViewModel.getCourse(course);
        int time = lCourse.getCourse_time();
        int timein_hour = lCourse.getTimein_hour();
        int timein_minute = lCourse.getTimein_minute();
        int timeout_hour = lCourse.getTimeout_hour();
        int timeout_minute = lCourse.getTimeout_minute();

        Student student = new Student(name, course, email, contact, address, dateFormat, time, timein_hour, timein_minute, timeout_hour, timeout_minute);
        mStudentViewModel.insert(student);
        Log.d(COMMON_TAG, TAG + " onCLick, status: " + student.toString());
        Toast.makeText(getActivity(), "Student successfully created", Toast.LENGTH_LONG).show();
        setClear();
    }

    public void setClear() {
        mName_et.setText("");
        mEmail_et.setText("");
        mContact_et.setText("");
        mAddress_et.setText("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mStudentViewModel = null;
        mCourseViewModel = null;
        mName_et = null;
        mEmail_et = null;
        mContact_et = null;
        mAddress_et = null;
        addStudent_Btn = null;
        mCourse_spinner = null;
        mArrayAdapter = null;
    }

    private void Toast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }
}
