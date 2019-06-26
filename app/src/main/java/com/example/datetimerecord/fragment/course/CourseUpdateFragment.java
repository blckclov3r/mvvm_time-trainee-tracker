package com.example.datetimerecord.fragment.course;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.datetimerecord.R;
import com.example.datetimerecord.model.Course;
import com.example.datetimerecord.model.Student;
import com.example.datetimerecord.viewmodel.CourseViewModel;
import com.example.datetimerecord.viewmodel.StudentViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

public class CourseUpdateFragment extends DialogFragment {

    private static final String TAG = "CourseUpdateFragment";
    public static final String COMMON_TAG = "mAppLog";

    private EditText course_et;
    private EditText time_et;
    private EditText description_et;
    private Button update_btn;
    private Course mCourse;
    private CourseViewModel mCourseViewModel;

    private AppCompatImageButton mTimeIn_btn, mTimeOut_btn;
    private TextView mTimeIn_hour_tv, mTimeInMinute_tv, mTimeOut_hour_tv, mTimeOut_minute_tv;
    private TextView mTimein_term_tv, mTimeout_term_tv;

    private volatile int mHour,mMinute;
    private StudentViewModel mStudentViewModel;

    public CourseUpdateFragment(){}

    public static CourseUpdateFragment newInstance(Course course){
        CourseUpdateFragment fragment = new CourseUpdateFragment();
        Bundle args = new Bundle();
        args.putParcelable("selected_course",course);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_update_fragment,container,false);
        course_et = view.findViewById(R.id.course_editText);
        time_et = view.findViewById(R.id.time_editText);
        description_et = view.findViewById(R.id.description_editText);
        update_btn = view.findViewById(R.id.update_button);

        mTimeIn_btn = view.findViewById(R.id.timeIn_button);
        mTimeOut_btn = view.findViewById(R.id.timeOut_button);
        mTimeIn_hour_tv = view.findViewById(R.id.timeIn_hour_textView);
        mTimein_term_tv = view.findViewById(R.id.timein_term_textView);
        mTimeout_term_tv = view.findViewById(R.id.timeout_term_textView);
        mTimeInMinute_tv = view.findViewById(R.id.timeIn_minute_textView);
        mTimeOut_hour_tv = view.findViewById(R.id.timeOut_hour_textView);
        mTimeOut_minute_tv = view.findViewById(R.id.timeOut_minute_textView);

        mStudentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        mCourseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        Log.d(COMMON_TAG,TAG+" onCreateView");
        if(getArguments() != null){
            mCourse = getArguments().getParcelable("selected_course");
            course_et.setText(mCourse.getCourse());
            time_et.setText(String.valueOf(mCourse.getCourse_time()));
            description_et.setText(mCourse.getDescription());
            mTimeIn_hour_tv.setText(String.valueOf(mCourse.getTimein_hour()));
            mTimeOut_hour_tv.setText(String.valueOf(mCourse.getTimeout_hour()));
            mTimeInMinute_tv.setText(String.valueOf(mCourse.getTimein_minute()));
            mTimeOut_minute_tv.setText(String.valueOf(mCourse.getTimeout_minute()));

            timein_timeout_hour(mCourse.getTimein_hour(), mCourse.getTimeout_hour());
        }



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTimeIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimeinBtn();
            }
        });
        mTimeOut_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimeoutBtn();
            }
        });
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpdate_btn();
            }
        });
    }

    private void setUpdate_btn(){
        String course = course_et.getText().toString().trim();
        String time = time_et.getText().toString().trim();
        String description = description_et.getText().toString().trim();
        String timein_hour = mTimeIn_hour_tv.getText().toString().trim();
        String timein_minute = mTimeInMinute_tv.getText().toString().trim();
        String timeout_hour = mTimeOut_hour_tv.getText().toString().trim();
        String timeout_minute = mTimeOut_minute_tv.getText().toString().trim();




        //timein reverse <-> 12hr to 24hr
        String timein_AMPM = mTimein_term_tv.getText().toString().trim();
        if (timein_AMPM.equals("am")) {
            if (timein_hour.equals("12")) {
                timein_hour = "0";
            } else {
                timein_hour = mTimeIn_hour_tv.getText().toString().trim();
            }
        } else {
            if (timein_hour.equals("12")) {
                timein_hour = "12";
            }
            if (timein_hour.equals("1")) {
                timein_hour = "13";
            }
            if (timein_hour.equals("2")) {
                timein_hour = "14";
            }
            if (timein_hour.equals("3")) {
                timein_hour = "15";
            }
            if (timein_hour.equals("4")) {
                timein_hour = "16";
            }
            if (timein_hour.equals("5")) {
                timein_hour = "17";
            }
            if (timein_hour.equals("6")) {
                timein_hour = "18";
            }
            if (timein_hour.equals("7")) {
                timein_hour = "19";
            }
            if (timein_hour.equals("8")) {
                timein_hour = "20";
            }
            if (timein_hour.equals("9")) {
                timein_hour = "21";
            }
            if (timein_hour.equals("10")) {
                timein_hour = "22";
            }
            if (timein_hour.equals("11")) {
                timein_hour = "23";
            }
            if (timein_hour.equals("12")) {
                timein_hour = "24";
            }
        }

        //timeout reverse <-> 12hr to 24hr
        String timeout_AMPM = mTimeout_term_tv.getText().toString().trim();
        if (timeout_AMPM.equals("am")) {
            if (timeout_hour.equals("12")) {
                timeout_hour = "0";
            } else {
                timeout_hour = mTimeOut_hour_tv.getText().toString().trim();
            }
        } else {
            if (timeout_hour.equals("12")) {
                timeout_hour = "12";
            }
            if (timeout_hour.equals("1")) {
                timeout_hour = "13";
            }
            if (timeout_hour.equals("2")) {
                timeout_hour = "14";
            }
            if (timeout_hour.equals("3")) {
                timeout_hour = "15";
            }
            if (timeout_hour.equals("4")) {
                timeout_hour = "16";
            }
            if (timeout_hour.equals("5")) {
                timeout_hour = "17";
            }
            if (timeout_hour.equals("6")) {
                timeout_hour = "18";
            }
            if (timeout_hour.equals("7")) {
                timeout_hour = "19";
            }
            if (timeout_hour.equals("8")) {
                timeout_hour = "20";
            }
            if (timeout_hour.equals("9")) {
                timeout_hour = "21";
            }
            if (timeout_hour.equals("10")) {
                timeout_hour = "22";
            }
            if (timeout_hour.equals("11")) {
                timeout_hour = "23";
            }
            if (timeout_hour.equals("12")) {
                timeout_hour = "24";
            }
        }


        Course c = new Course(course,Integer.parseInt(time),description,Integer.parseInt(timein_hour),
                Integer.parseInt(timein_minute),Integer.parseInt(timeout_hour),Integer.parseInt(timeout_minute));

        List<Student> studentList = mStudentViewModel.getStudentCourse(mCourse.getCourse());
        for(Student student : studentList){
//            student.setRemaining(Integer.parseInt(time)); //we don't set time it cause reset to the student remaining time
            student.setTimein_hour(Integer.parseInt(timein_hour));
            student.setTimeout_hour(Integer.parseInt(timeout_hour));
            student.setTimein_minute(Integer.parseInt(timein_minute));
            student.setTimeout_minute(Integer.parseInt(timeout_minute));
            mStudentViewModel.update(student);
        }

        if(mCourse.getC_id() > 0) {
            c.setC_id(mCourse.getC_id()); //must include the id
            mCourseViewModel.update(c);
            Toast.makeText(getActivity(), "Successfully Updated", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), "Something wen't wrong", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void setTimeinBtn(){
        Calendar calendar = Calendar.getInstance();
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                if (mHour >= 12) {
                    mTimein_term_tv.setText("pm");
                    if (mHour == 12) {
                        mTimeIn_hour_tv.setText("12");
                    }
                    if (mHour == 13) {
                        mTimeIn_hour_tv.setText("1");
                    }
                    if (mHour == 14) {
                        mTimeIn_hour_tv.setText("2");
                    }
                    if (mHour == 15) {
                        mTimeIn_hour_tv.setText("3");
                    }
                    if (mHour == 16) {
                        mTimeIn_hour_tv.setText("4");
                    }
                    if (mHour == 17) {
                        mTimeIn_hour_tv.setText("5");
                    }
                    if (mHour == 18) {
                        mTimeIn_hour_tv.setText("6");
                    }
                    if (mHour == 19) {
                        mTimeIn_hour_tv.setText("7");
                    }
                    if (mHour == 20) {
                        mTimeIn_hour_tv.setText("8");
                    }
                    if (mHour == 21) {
                        mTimeIn_hour_tv.setText("9");
                    }
                    if (mHour == 22) {
                        mTimeIn_hour_tv.setText("10");
                    }
                    if (mHour == 23) {
                        mTimeIn_hour_tv.setText("11");
                    }
                    if (mHour == 24) {
                        mTimeIn_hour_tv.setText("12");
                    }

                } else {
                    mTimein_term_tv.setText("am");
                    if (mHour == 0) {
                        mTimeIn_hour_tv.setText("12");
                    } else {
                        mTimeIn_hour_tv.setText(String.valueOf(mHour));
                    }
                }


                if (mMinute == 0) {
                    mTimeInMinute_tv.setText("00");
                } else {
                    mTimeInMinute_tv.setText(String.valueOf(mMinute));
                }

            }
        }, mHour, mMinute, DateFormat.is24HourFormat(getActivity()));
        timePickerDialog.show();
    }

    private void setTimeoutBtn(){
        Calendar calendar = Calendar.getInstance();
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                if (mHour >= 12) {
                    mTimeout_term_tv.setText("pm");
                    if (mHour == 12) {
                        mTimeOut_hour_tv.setText("12");
                    }
                    if (mHour == 13) {
                        mTimeOut_hour_tv.setText("1");
                    }
                    if (mHour == 14) {
                        mTimeOut_hour_tv.setText("2");
                    }
                    if (mHour == 15) {
                        mTimeOut_hour_tv.setText("3");
                    }
                    if (mHour == 16) {
                        mTimeOut_hour_tv.setText("4");
                    }
                    if (mHour == 17) {
                        mTimeOut_hour_tv.setText("5");
                    }
                    if (mHour == 18) {
                        mTimeOut_hour_tv.setText("6");
                    }
                    if (mHour == 19) {
                        mTimeOut_hour_tv.setText("7");
                    }
                    if (mHour == 20) {
                        mTimeOut_hour_tv.setText("8");
                    }
                    if (mHour == 21) {
                        mTimeOut_hour_tv.setText("9");
                    }
                    if (mHour == 22) {
                        mTimeOut_hour_tv.setText("10");
                    }
                    if (mHour == 23) {
                        mTimeOut_hour_tv.setText("11");
                    }
                    if (mHour == 24) {
                        mTimeOut_hour_tv.setText("12");
                    }

                } else {
                    mTimeout_term_tv.setText("am");
                    if (mHour == 0) {
                        mTimeOut_hour_tv.setText("12");
                    } else {
                        mTimeOut_hour_tv.setText(String.valueOf(mHour));
                    }
                }

                if (mMinute == 0) {
                    mTimeOut_minute_tv.setText("00");
                } else {
                    mTimeOut_minute_tv.setText(String.valueOf(mMinute));
                }
            }
        }, mHour, mMinute, DateFormat.is24HourFormat(getActivity()));
        timePickerDialog.show();
    }

    private void timein_timeout_hour(int timein_hour, int timeout_hour) {
        if (timein_hour >= 12) {

            if (timein_hour == 12) {
                mTimeIn_hour_tv.setText("12");
            }
            if (timein_hour == 13) {
                mTimeIn_hour_tv.setText("1");
            }
            if (timein_hour == 14) {
                mTimeIn_hour_tv.setText("2");
            }
            if (timein_hour == 15) {
                mTimeIn_hour_tv.setText("3");
            }
            if (timein_hour == 16) {
                mTimeIn_hour_tv.setText("4");
            }
            if (timein_hour == 17) {
                mTimeIn_hour_tv.setText("5");
            }
            if (timein_hour == 18) {
                mTimeIn_hour_tv.setText("6");
            }
            if (timein_hour == 19) {
                mTimeIn_hour_tv.setText("7");
            }
            if (timein_hour == 20) {
                mTimeIn_hour_tv.setText("8");
            }
            if (timein_hour == 21) {
                mTimeIn_hour_tv.setText("9");
            }
            if (timein_hour == 22) {
                mTimeIn_hour_tv.setText("10");
            }
            if (timein_hour == 23) {
                mTimeIn_hour_tv.setText("11");
            }
            if (timein_hour == 24) {
                mTimeIn_hour_tv.setText("12");
            }
            mTimein_term_tv.setText("pm");

        } else {

            if (timein_hour == 0) {
                mTimeIn_hour_tv.setText("12");
            } else {
                mTimeIn_hour_tv.setText(String.valueOf(timein_hour));
            }
            mTimein_term_tv.setText("am");
        }


        if (timeout_hour >= 12) {

            if (timeout_hour == 12) {
                mTimeOut_hour_tv.setText("12");
            }
            if (timeout_hour == 13) {
                mTimeOut_hour_tv.setText("1");
            }
            if (timeout_hour == 14) {
                mTimeOut_hour_tv.setText("2");
            }
            if (timeout_hour == 15) {
                mTimeOut_hour_tv.setText("3");
            }
            if (timeout_hour == 16) {
                mTimeOut_hour_tv.setText("4");
            }
            if (timeout_hour == 17) {
                mTimeOut_hour_tv.setText("5");
            }
            if (timeout_hour == 18) {
                mTimeOut_hour_tv.setText("6");
            }
            if (timeout_hour == 19) {
                mTimeOut_hour_tv.setText("7");
            }
            if (timeout_hour == 20) {
                mTimeOut_hour_tv.setText("8");
            }
            if (timeout_hour == 21) {
                mTimeOut_hour_tv.setText("9");
            }
            if (timeout_hour == 22) {
                mTimeOut_hour_tv.setText("10");
            }
            if (timeout_hour == 23) {
                mTimeOut_hour_tv.setText("11");
            }
            if (timeout_hour == 24) {
                mTimeOut_hour_tv.setText("12");
            }
            mTimeout_term_tv.setText("pm");
        } else {

            if (timeout_hour == 0) {
                mTimeOut_hour_tv.setText("12");
            } else {
                mTimeOut_hour_tv.setText(String.valueOf(timeout_hour));
            }
            mTimeout_term_tv.setText("am");
        }
    }
}
