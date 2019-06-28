package com.example.datetimerecord.fragment.course;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.SystemClock;
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
import com.example.datetimerecord.model.AppLog;
import com.example.datetimerecord.model.Course;
import com.example.datetimerecord.utils.LineEditText;
import com.example.datetimerecord.viewmodel.CourseViewModel;
import com.example.datetimerecord.viewmodel.LogViewModel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class CourseAddFragment extends Fragment implements View.OnClickListener {

    private static final String COMMON_TAG = "mAppLog";
    private static final String TAG = "CourseAddFragment";

    //components
    private EditText course_et, time_et;
    private LineEditText description_et;
    private Button addCourse_btn;
    private Course mCourse;
    private AppCompatImageButton mTimeIn_btn, mTimeOut_btn;
    private TextView mTimeIn_hour_tv, mTimeInMinute_tv, mTimeOut_hour_tv, mTimeOut_minute_tv;
    private TextView mTimein_term_tv, mTimeout_term_tv;

    //vars
    private volatile int mHour;
    private volatile int mMinute;
    private CourseViewModel mCourseViewModel;
    private LogViewModel mLogViewModel;
    private long mLastClick = 0;

    public CourseAddFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_add_fragment, container, false);

        course_et = view.findViewById(R.id.course_editText);
        time_et = view.findViewById(R.id.time_editText);
        description_et = view.findViewById(R.id.description_editText);
        addCourse_btn = view.findViewById(R.id.addCourse_button);

        mTimeIn_btn = view.findViewById(R.id.timeIn_button);
        mTimeOut_btn = view.findViewById(R.id.timeOut_button);
        mTimeIn_hour_tv = view.findViewById(R.id.timeIn_hour_textView);
        mTimein_term_tv = view.findViewById(R.id.timein_term_textView);
        mTimeout_term_tv = view.findViewById(R.id.timeout_term_textView);
        mTimeInMinute_tv = view.findViewById(R.id.timeIn_minute_textView);
        mTimeOut_hour_tv = view.findViewById(R.id.timeOut_hour_textView);
        mTimeOut_minute_tv = view.findViewById(R.id.timeOut_minute_textView);

        mCourseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        mLogViewModel = ViewModelProviders.of(this).get(LogViewModel.class);

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
                final String course = course_et.getText().toString().trim();
                String courseTime = time_et.getText().toString();
                String desc = description_et.getText().toString().trim();
                //timein & timeout vars
                String timein_hour = mTimeIn_hour_tv.getText().toString().trim();
                final String timein_minute = mTimeInMinute_tv.getText().toString().trim();
                String timeout_hour = mTimeOut_hour_tv.getText().toString().trim();
                final String timeout_minute = mTimeOut_minute_tv.getText().toString().trim();

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
                if (timein_hour.equals("00") || timein_hour.equals("0")) {
                    mTimeIn_hour_tv.setText("00");
                    Toast.makeText(getActivity(), "Timein hour is invalid", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (timein_minute.equals("00") || timein_minute.equals("0")) {
                    mTimeInMinute_tv.setText("00");
                }
                if (timeout_minute.equals("00") || timeout_minute.equals("0")) {
                    mTimeOut_minute_tv.setText("00");
                }

                if (timeout_hour.equals("00") || timeout_hour.equals("0")) {
                    mTimeOut_hour_tv.setText("00");
                    Toast.makeText(getActivity(), "Timeout hour is invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                Course lcourse = mCourseViewModel.getCourse(course);
                if(lcourse!=null) {
                    Toast.makeText(getActivity(), "Course is already exist", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Log.d(COMMON_TAG,TAG+" course is not exist in out database");
                }


                final int time = Integer.parseInt(courseTime);
                if (desc.isEmpty()) {
                    desc = "Empty";
                }

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

                final String finalTimein_hour = timein_hour;
                final String finalDesc = desc;
                final String finalTimeout_hour = timeout_hour;
                new SweetAlertDialog(Objects.requireNonNull(getActivity()), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("Add Course")
                        .setContentText("Are you sure?")
                        .setConfirmText("Yes")
                        .setCustomImage(R.drawable.books_xml)
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                if(SystemClock.elapsedRealtime() - mLastClick < 1000){
                                    return;
                                }
                                mLastClick = SystemClock.elapsedRealtime();
                                sDialog.dismiss();
                                mCourse = new Course(course, time, finalDesc,
                                        Integer.parseInt(finalTimein_hour), Integer.parseInt(timein_minute), Integer.parseInt(finalTimeout_hour), Integer.parseInt(timeout_minute));
                                if(mCourse != null) {
                                    Log.d(COMMON_TAG, TAG + " mCourse: " + mCourse.toString());
                                    mCourseViewModel.insert(mCourse);
                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                                    String dateFormat = simpleDateFormat.format(new Date());
                                    mLogViewModel.insert(new AppLog("Course successfully created, name: "+course,dateFormat));
                                    setClear();
                                    new SweetAlertDialog(Objects.requireNonNull(getActivity()), SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Add Course")
                                            .setContentText("Course successfully created")
                                            .show();
                                }else{
                                    new SweetAlertDialog(Objects.requireNonNull(getActivity()), SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Error")
                                            .setContentText("Course not created")
                                            .show();
                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                                    String dateFormat = simpleDateFormat.format(new Date());
                                    mLogViewModel.insert(new AppLog("Something went wrong in the add course fragment",dateFormat));
                                }
                            }
                        })
                        .show();
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

    private void setTimeOut_btn() {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        course_et = null;
        time_et = null;
        description_et = null;
        addCourse_btn = null;
        mCourse = null;
        mCourseViewModel = null;
        mTimeIn_btn = null;
        mTimeOut_btn = null;
        mTimeIn_hour_tv = null;
        mTimeInMinute_tv = null;
        mTimeOut_hour_tv = null;
        mTimeOut_minute_tv = null;
        mTimein_term_tv = null;
        mTimeout_term_tv = null;
    }

    public void setClear() {
        course_et.setText("");
        time_et.setText("");
        description_et.setText("");
        course_et.requestFocus();
    }


}
