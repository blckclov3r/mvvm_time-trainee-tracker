package com.example.datetimerecord.fragment.course;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datetimerecord.R;
import com.example.datetimerecord.model.Course;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CourseDetailFragment extends Fragment {

    private static final String COMMON_TAG = "mAppLog";
    private static final String TAG = "CourseDetailFragment";

    //components
    private TextView mId_tv, mCourse_tv, mTime_tv, mDesc_tv, mTimein_tv, mTimeout_tv, mTimein_term_tv, mTimeout_term_tv;
    private Button courseEnrollee_btn;

    //vars
    private String mTimein_minute;
    private String mTimeout_minute;

    public CourseDetailFragment() {
    }

    public static CourseDetailFragment newInstance(Course course) {
        CourseDetailFragment fragment = new CourseDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("selected_course", course);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_detail_fragment, container, false);
        Log.d(COMMON_TAG, TAG + " onCreateView");
        mId_tv = view.findViewById(R.id.id_textView);
        mCourse_tv = view.findViewById(R.id.course_textView);
        mTime_tv = view.findViewById(R.id.time_textView);
        mDesc_tv = view.findViewById(R.id.description_textView);
        mTimein_tv = view.findViewById(R.id.timein_textView);
        mTimeout_tv = view.findViewById(R.id.timeout_textView);
        mTimein_term_tv = view.findViewById(R.id.timein_term_textView);
        mTimeout_term_tv = view.findViewById(R.id.timeout_term_textView);
        courseEnrollee_btn = view.findViewById(R.id.course_enrollee_button);

        if (getArguments() != null) {
            Course course = getArguments().getParcelable("selected_course");
            Log.d(COMMON_TAG, TAG + " course detail: " + course.toString());
            mId_tv.setText(String.valueOf(course.getC_id()));
            mCourse_tv.setText("" + course.getCourse());
            mTime_tv.setText("" + String.valueOf(course.getCourse_time()));
            mDesc_tv.setText("" + course.getDescription());

            mTimein_minute = String.valueOf(course.getTimein_minute());
            mTimeout_minute = String.valueOf(course.getTimeout_minute());
            if (mTimein_minute.equals("0")) {
                mTimein_minute = "00";
            }
            if (mTimeout_minute.equals("0")) {
                mTimeout_minute = "00";
            }

            String timein_hour_string = String.valueOf(course.getTimein_hour());
            int timein_hour = Integer.parseInt(timein_hour_string);
            if (timein_hour >= 12) {
                mTimein_term_tv.setText("pm");
                if (timein_hour == 13) {
                    timein_hour = 1;
                    mTimein_tv.setText("" + timein_hour + " : " + mTimein_minute);
                }
                if (timein_hour == 14) {
                    timein_hour = 2;
                    mTimein_tv.setText("" + timein_hour + " : " + mTimein_minute);
                }
                if (timein_hour == 15) {
                    timein_hour = 3;
                    mTimein_tv.setText("" + timein_hour + " : " + mTimein_minute);
                }
                if (timein_hour == 16) {
                    timein_hour = 4;
                    mTimein_tv.setText("" + timein_hour + " : " + mTimein_minute);
                }
                if (timein_hour == 17) {
                    timein_hour = 5;
                    mTimein_tv.setText("" + timein_hour + " : " + mTimein_minute);
                }
                if (timein_hour == 18) {
                    timein_hour = 6;
                    mTimein_tv.setText("" + timein_hour + " : " + mTimein_minute);
                }
                if (timein_hour == 19) {
                    timein_hour = 7;
                    mTimein_tv.setText("" + timein_hour + " : " + mTimein_minute);
                }
                if (timein_hour == 20) {
                    timein_hour = 8;
                    mTimein_tv.setText("" + timein_hour + " : " + mTimein_minute);
                }
                if (timein_hour == 21) {
                    timein_hour = 9;
                    mTimein_tv.setText("" + timein_hour + " : " + mTimein_minute);
                }
                if (timein_hour == 22) {
                    timein_hour = 10;
                    mTimein_tv.setText("" + timein_hour + " : " + mTimein_minute);
                }
                if (timein_hour == 23) {
                    timein_hour = 11;
                    mTimein_tv.setText("" + timein_hour + " : " + mTimein_minute);
                }
                if (timein_hour == 24) {
                    timein_hour = 12;
                    mTimein_tv.setText("" + timein_hour + " : " + mTimein_minute);
                }
            } else {
                mTimein_term_tv.setText("am");
                if (timein_hour == 0) {
                    timein_hour = 12;
                    mTimein_tv.setText("" + timein_hour + " : " + mTimein_minute);
                } else {
                    mTimein_tv.setText("" + timein_hour + " : " + mTimein_minute);
                }
            }

            String timeout_hour_string = String.valueOf(course.getTimeout_hour());
            int timeout_hour = Integer.parseInt(timeout_hour_string);
            if (timeout_hour >= 12) {
                mTimeout_term_tv.setText("pm");
                if (timeout_hour == 13) {
                    timeout_hour = 1;
                    mTimeout_tv.setText("" + timeout_hour + " : " + mTimeout_minute);
                }
                if (timeout_hour == 14) {
                    timeout_hour = 2;
                    mTimeout_tv.setText("" + timeout_hour + " : " + mTimeout_minute);
                }
                if (timeout_hour == 15) {
                    timeout_hour = 3;
                    mTimeout_tv.setText("" + timeout_hour + " : " + mTimeout_minute);
                }
                if (timeout_hour == 16) {
                    timeout_hour = 4;
                    mTimeout_tv.setText("" + timeout_hour + " : " + mTimeout_minute);
                }
                if (timeout_hour == 17) {
                    timeout_hour = 5;
                    mTimeout_tv.setText("" + timeout_hour + " : " + mTimeout_minute);
                }
                if (timeout_hour == 18) {
                    timeout_hour = 6;
                    mTimeout_tv.setText("" + timeout_hour + " : " + mTimeout_minute);
                }
                if (timeout_hour == 19) {
                    timeout_hour = 7;
                    mTimeout_tv.setText("" + timeout_hour + " : " + mTimeout_minute);
                }
                if (timeout_hour == 20) {
                    timeout_hour = 8;
                    mTimeout_tv.setText("" + timeout_hour + " : " + mTimeout_minute);
                }
                if (timeout_hour == 21) {
                    timeout_hour = 9;
                    mTimeout_tv.setText("" + timeout_hour + " : " + mTimeout_minute);
                }
                if (timeout_hour == 22) {
                    timeout_hour = 10;
                    mTimeout_tv.setText("" + timeout_hour + " : " + mTimeout_minute);
                }
                if (timeout_hour == 23) {
                    timeout_hour = 11;
                    mTimeout_tv.setText("" + timeout_hour + " : " + mTimeout_minute);
                }
                if (timeout_hour == 24) {
                    timeout_hour = 12;
                    mTimeout_tv.setText("" + timeout_hour + " : " + mTimeout_minute);
                }
            } else {
                mTimeout_term_tv.setText("am");
                if (timeout_hour == 0) {
                    timeout_hour = 12;
                    mTimeout_tv.setText("" + timeout_hour + " : " + mTimeout_minute);
                } else {
                    mTimeout_tv.setText("" + timeout_hour + " : " + mTimeout_minute);
                }
            }

        }

        courseEnrollee_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null) {
                    if(!mCourse_tv.getText().toString().trim().equals("")){
                        mListener.onClickSearch(mCourse_tv.getText().toString().trim());
                    }
                }
            }
        });
        return view;
    }

    public interface OnClickDetailSearch{
        void onClickSearch(String course);
    }
    private OnClickDetailSearch mListener;
    public void setonClickDetailSearchListener(OnClickDetailSearch listener){
        mListener = listener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnClickDetailSearch){
            mListener = (OnClickDetailSearch) getActivity();
        }else{
            throw new RuntimeException(context.toString()+" must implement OnClickDetailSearch");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mId_tv = null;
        mCourse_tv = null;
        mTime_tv = null;
        mDesc_tv = null;
        mTimein_tv = null;
        mTimeout_tv = null;
    }
}
