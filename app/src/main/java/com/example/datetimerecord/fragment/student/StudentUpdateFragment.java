package com.example.datetimerecord.fragment.student;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class StudentUpdateFragment extends Fragment {

    private static final String COMMON_TAG = "mAppLog";
    private static final String TAG = "StudentUpdateFragment";

    //components
    private EditText name_et, email_et, contact_et, address_et;
    private Button studentUpdate_btn;
    private Spinner mCourse_spinner;

    //vars
    private int mCoursePos = 0;
    private CourseViewModel mCourseViewModel;
    private StudentViewModel mStudentViewModel;
    private ArrayAdapter<String> mArrayAdapter;

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
        mCourse_spinner = view.findViewById(R.id.course_spinner);
        mStudentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        mCourseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);

        if (getArguments() != null) {
            Student student = getArguments().getParcelable("selected_student");
            name_et.setText(Objects.requireNonNull(student).getName());
            email_et.setText(student.getEmail());
            contact_et.setText(String.valueOf(student.getContact()));
            address_et.setText(student.getAddress());
        }


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCourseViewModel.getAllCourse().observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                List<String> mCourseArrayList = new ArrayList<>();
                mCoursePos = 0;
                for (int i = 0; i < courses.size(); i++) {
                    Course course = courses.get(i);
                    mCourseArrayList.add(course.getCourse());
                }

                mArrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_item, mCourseArrayList);
                mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mCourse_spinner.setAdapter(mArrayAdapter);

                if (getArguments() != null) {
                    for (String x : mCourseArrayList) {
                        Log.d(COMMON_TAG, TAG + " x: " + x);
                        Student student = getArguments().getParcelable("selected_student");
                        if (x.equals(Objects.requireNonNull(student).getCourse())) {
                            Log.d(COMMON_TAG, TAG + " x equal: " + x + ", mStudent course: " + student.getCourse());
                            Log.d(COMMON_TAG, TAG + " mCoursePos: " + mCoursePos);
                            mCourse_spinner.setSelection(mCoursePos);
                            break;
                        }
                        mCoursePos++;
                    }
                } else {
                    mCourse_spinner.setSelection(mCoursePos);
                }
            }
        });

        studentUpdate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getArguments() != null) {
                    Student pStudent = getArguments().getParcelable("selected_student");
                    String name = name_et.getText().toString().trim();
                    String course = mCourse_spinner.getSelectedItem().toString();
                    String email = email_et.getText().toString().trim();
                    String contact = contact_et.getText().toString().trim();
                    String address = address_et.getText().toString().trim();
                    String timestamp = Objects.requireNonNull(pStudent).getTimestamp();
                    int remaining = pStudent.getRemaining();
                    Student student = new Student(name, course, email, contact, address, timestamp, remaining);
                    student.setT_id(pStudent.getT_id());
                    mStudentViewModel.update(student);
                    Toast.makeText(getActivity(), "Student successfully updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "something wen't wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCourseViewModel = null;
        mStudentViewModel = null;
        mCourse_spinner = null;
        mArrayAdapter = null;
    }
}
