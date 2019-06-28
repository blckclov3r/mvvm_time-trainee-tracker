package com.example.datetimerecord.fragment.student;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.example.datetimerecord.R;
import com.example.datetimerecord.model.AppLog;
import com.example.datetimerecord.model.Course;
import com.example.datetimerecord.model.Student;
import com.example.datetimerecord.utils.LineEditText;
import com.example.datetimerecord.viewmodel.CourseViewModel;
import com.example.datetimerecord.viewmodel.LogViewModel;
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
import cn.pedant.SweetAlert.SweetAlertDialog;

public class StudentAddFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "StudentAddFragment";
    private static final String COMMON_TAG = "mAppLog";

    //components
    private EditText mName_et, mEmail_et, mContact_et;
    private LineEditText mAddress_et;
    private Button addStudent_Btn;

    //vars
    private StudentViewModel mStudentViewModel;
    private SmartMaterialSpinner mCourse_spinner;
    private CourseViewModel mCourseViewModel;
    private ArrayAdapter<String> mArrayAdapter;
    private List<String> mArrayList;
    private String mCourse;
    private LogViewModel mLogViewModel;
    private long mLastClick = 0;
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
        mLogViewModel = ViewModelProviders.of(this).get(LogViewModel.class);



        mCourseViewModel.getAllCourse().observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                mArrayList = new ArrayList<>();
                if (courses.size() > 0) {
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
                mCourse_spinner.setAdapter(mArrayAdapter);
            }
        });

        mCourse_spinner.setSearchable(true);
        mCourse_spinner.setEnableSearchHeader(true);
        mCourse_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) {
                    mCourse = mArrayList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    @Override
    public void onClick(View v) {
        final String name = mName_et.getText().toString().trim();
        Log.d(COMMON_TAG, TAG + " mCourse: " + mCourse);
        final String email = mEmail_et.getText().toString().trim();
        final String contact = mContact_et.getText().toString().trim();
        final String address = mAddress_et.getText().toString().trim();


        if (mCourse == null) {
            Toast("Course is required");
            return;
        }
        if (mCourse.isEmpty() || mCourse.equals("Empty")) {
            Toast("Course is required");
            return;
        }

        if (name.isEmpty()) {
            mName_et.requestFocus();
            Toast("Name is required");
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

        List<Student> studentList = mStudentViewModel.getStudentCourse(mCourse);
        boolean check = false;
        for(Student student : studentList){
            String studentName = student.getName();
            String studentCourse = student.getCourse();
            Log.d(COMMON_TAG,TAG+" studentname: "+studentName+", student course: "+studentCourse);

            if(studentName.equals(name) && studentCourse.equals(mCourse)){
                Log.d(COMMON_TAG,TAG+" true");
                check = true;
                break;
            }
        }
        if(check){
            Toast("The student is already registered in course: "+mCourse);
            check = false; //it reset to false without this :D, this is not necessary
            return;
        }

        new SweetAlertDialog(Objects.requireNonNull(getActivity()), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("Add Student")
                .setContentText("Are you sure?")
                .setConfirmText("Yes")
                .setCustomImage(R.drawable.man_a)
                .setCancelText("No")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        if (SystemClock.elapsedRealtime() - mLastClick < 300) {
                            return;
                        }
                        mLastClick = SystemClock.elapsedRealtime();
                        sDialog.dismiss();
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                        String dateFormat = simpleDateFormat.format(new Date());
                        Course lCourse = mCourseViewModel.getCourse(mCourse);
                        if(lCourse!=null) {
                            int time = lCourse.getCourse_time();
                            int timein_hour = lCourse.getTimein_hour();
                            int timein_minute = lCourse.getTimein_minute();
                            int timeout_hour = lCourse.getTimeout_hour();
                            int timeout_minute = lCourse.getTimeout_minute();

                            Student student = new Student(name, mCourse, email, contact, address, dateFormat, time,
                                    timein_hour, timein_minute, timeout_hour, timeout_minute);

                            mStudentViewModel.insert(student);

                            Log.d(COMMON_TAG, TAG + " onCLick, status: " + student.toString());


                            new SweetAlertDialog(Objects.requireNonNull(getActivity()), SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Add Student")
                                    .setContentText("Student successfully created")
                                    .show();

                            setClear();

                            mLogViewModel.insert(new AppLog("Student successfully created, name: "+name,dateFormat));

                        }else{
                            new SweetAlertDialog(Objects.requireNonNull(getActivity()), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error")
                                    .setContentText("Student not created")
                                    .show();
                            mLogViewModel.insert(new AppLog("Something went wrong in add student fragment: "+name,dateFormat));
                        }
                    }
                })
                .show();
    }

    public void setClear() {
        mName_et.setText("");
        mEmail_et.setText("");
        mContact_et.setText("");
        mAddress_et.setText("");
        mName_et.requestFocus();
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
