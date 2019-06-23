package com.example.datetimerecord.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class AddStudentFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "AddStudentFragment";
    private static final String COMMON_TAG = "mAppLog";

    private EditText name_et,email_et,contact_et,address_et;
    private Button addStudent_Btn;
    private StudentViewModel mStudentViewModel;
    private Spinner mCourse_spinner;
    private CourseViewModel mCourseViewModel;
    private ArrayAdapter<String> mArrayAdapter;

    private Course mCourse;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_student_fragment,container,false);
        name_et = view.findViewById(R.id.name_editText);
        email_et = view.findViewById(R.id.email_editText);
        contact_et = view.findViewById(R.id.contact_editText);
        address_et = view.findViewById(R.id.address_editText);
        addStudent_Btn = view.findViewById(R.id.addStudent_button);
        mCourse_spinner = view.findViewById(R.id.course_spinner);
        mCourse_spinner.setSelection(0);

        addStudent_Btn.setOnClickListener(this);
        mStudentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        mCourseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        mCourseViewModel.getAllCourse().observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                ArrayList<String> arrayList = new ArrayList<>();
                for(int i =0;i<courses.size();i++){
                    Course course = courses.get(i);
                    String course_name = course.getCourse();
                    arrayList.add(course_name);
                }
                mArrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),android.R.layout.simple_spinner_item,arrayList);
                mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mCourse_spinner.setAdapter(mArrayAdapter);
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        String name = name_et.getText().toString().trim();
        String course = mCourse_spinner.getSelectedItem().toString().trim();
        String email = email_et.getText().toString().trim();
        String contact = contact_et.getText().toString().trim();
        String address = address_et.getText().toString().trim();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String dateFormat = simpleDateFormat.format(new Date());

        mCourse = mCourseViewModel.getCourseTime(course);
        int time = mCourse.getCourse_time();

        Log.d(COMMON_TAG,TAG+" dataFormat: "+dateFormat);
        Student student = new Student(name,course,email,contact,address,dateFormat,time);
        mStudentViewModel.insert(student);
        Log.d(COMMON_TAG,TAG+" onCLick, status: "+student.toString());
        Toast.makeText(getActivity(), "Student successfully created", Toast.LENGTH_LONG).show();
        setClear();
    }

    public void setClear(){
        name_et.setText("");
        email_et.setText("");
        contact_et.setText("");
        address_et.setText("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mStudentViewModel = null;
        mCourseViewModel = null;
    }
}
