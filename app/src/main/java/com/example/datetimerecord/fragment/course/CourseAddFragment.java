package com.example.datetimerecord.fragment.course;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
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
import com.example.datetimerecord.viewmodel.CourseViewModel;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

public class CourseAddFragment extends Fragment implements View.OnClickListener {

    private EditText course_et, time_et, description_et;
    private Button addCourse_btn;
    private Course mCourse;
    private CourseViewModel mViewModel;
    private AppCompatImageButton mTimeIn_btn, mTimeOut_btn;
    private TextView mTimeIn_hour_tv, mTimeInMinute_tv, mTimeOut_hour_tv, mTimeOut_minute_tv;

    //vars
    private int mHour;
    private int mMinute;

    public CourseAddFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_course_fragment, container, false);

        course_et = view.findViewById(R.id.course_editText);
        time_et = view.findViewById(R.id.time_editText);
        description_et = view.findViewById(R.id.description_editText);
        addCourse_btn = view.findViewById(R.id.addCourse_button);
        mTimeIn_btn = view.findViewById(R.id.timeIn_button);
        mTimeOut_btn = view.findViewById(R.id.timeOut_button);
        mTimeIn_hour_tv = view.findViewById(R.id.timeIn_hour_textView);

        //time in & out
        mTimeInMinute_tv = view.findViewById(R.id.timeIn_minute_textView);
        mTimeOut_hour_tv = view.findViewById(R.id.timeOut_hour_textView);
        mTimeOut_minute_tv = view.findViewById(R.id.timeOut_minute_textView);

        mViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);

        //setOnClick
        addCourse_btn.setOnClickListener(this);
        mTimeIn_btn.setOnClickListener(this);
        mTimeOut_btn.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addCourse_button: {
                String course = course_et.getText().toString().trim();
                String courseTime = time_et.getText().toString();
                String desc = description_et.getText().toString().trim();
                String timein_hour = mTimeIn_hour_tv.getText().toString().trim();
                String timein_minute = mTimeInMinute_tv.getText().toString().trim();
                String timeout_hour = mTimeOut_hour_tv.getText().toString().trim();
                String timeout_minute = mTimeOut_minute_tv.getText().toString().trim();

                if (course.isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                    course_et.requestFocus();
                    return;
                }
                if (courseTime.isEmpty()) {
                    Toast.makeText(getActivity(), "Time is required", Toast.LENGTH_SHORT).show();
                    time_et.requestFocus();
                    return;
                }
                if(timein_hour.equals("00") || timein_hour.equals("0")){
                    mTimeIn_hour_tv.setText("00");
                    Toast.makeText(getActivity(), "Timein hour is invalid", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(timein_minute.equals("00") || timein_minute.equals("0")){
                    mTimeInMinute_tv.setText("00");
                }
                if(timeout_minute.equals("00") || timeout_minute.equals("0")){
                    mTimeOut_minute_tv.setText("00");
                }

                if(timeout_hour.equals("00") || timeout_hour.equals("0")){
                    mTimeOut_hour_tv.setText("00");
                    Toast.makeText(getActivity(), "Timeout hour is invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                
                int time = Integer.parseInt(courseTime);
                if (desc.isEmpty()) {
                    desc = "Empty";
                }
                mCourse = new Course(course, time, desc,
                        Integer.parseInt(timein_hour),Integer.parseInt(timein_minute),
                        Integer.parseInt(timeout_hour),Integer.parseInt(timeout_minute));
                mViewModel.insert(mCourse);
                setClear();
                Toast.makeText(getActivity(), "Course successfully created", Toast.LENGTH_SHORT).show();
            }
            break;

            case R.id.timeIn_button: {
                setTimeIn_btn();
            }
            break;

            case R.id.timeOut_button: {
                setTimeOut_btn();
            }
            break;
        }
    }


    private void setTimeIn_btn() {
        mHour = 0;
        mMinute = 0;
        Calendar calendar = Calendar.getInstance();
        mHour = calendar.get(Calendar.HOUR);
        mMinute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if(hourOfDay == 0){
                    mTimeIn_hour_tv.setText("00");
                }else{
                    mTimeIn_hour_tv.setText(String.valueOf(hourOfDay));
                }
                if(minute == 0){
                    mTimeInMinute_tv.setText("00");
                }else {
                    mTimeInMinute_tv.setText(String.valueOf(minute));
                }
            }
        }, mHour, mMinute, DateFormat.is24HourFormat(getActivity()));
        timePickerDialog.show();
    }


    private void setTimeOut_btn() {
        mHour = 0;
        mMinute = 0;
        Calendar calendar = Calendar.getInstance();
        mHour = calendar.get(Calendar.HOUR);
        mMinute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if(hourOfDay == 0 ){
                    mTimeOut_hour_tv.setText("00");
                }else{
                    mTimeOut_hour_tv.setText(String.valueOf(hourOfDay));
                }
                if(minute == 0){
                    mTimeOut_minute_tv.setText("00");
                }else {
                    mTimeOut_minute_tv.setText(String.valueOf(minute));
                }
            }
        }, mHour, mMinute, DateFormat.is24HourFormat(getActivity()));
        timePickerDialog.show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel = null;
        course_et = null;
        time_et = null;
        description_et = null;
    }

    public void setClear() {
        course_et.setText("");
        time_et.setText("");
        description_et.setText("");
    }


}
