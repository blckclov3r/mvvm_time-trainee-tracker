package com.example.datetimerecord.fragment.student;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datetimerecord.R;
import com.example.datetimerecord.model.Student;
import com.example.datetimerecord.viewmodel.StudentViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

//https://www.calculatorsoup.com/calculators/time/hours.php


public class StudentTimeUpdateFragment extends Fragment {

    private static final String COMMON_TAG = "mAppLog";
    private static final String TAG = "StudentTimeUpdateFragme";

    private TextView name_tv, course_tv, email_tv, contact_tv, address_tv, id_tv, remaining_tv;
    private TextView mTimeIn_hour_tv, mTimeInMinute_tv, mTimeOut_hour_tv, mTimeOut_minute_tv;
    private TextView mTimein_term_tv, mTimeout_term_tv;
    private Button elapse_btn;
    private StudentViewModel mStudentViewModel;
    private Student mStudent;

    //vars
    private int mStudentId;
    private int mTimein_hour, mTimein_minute, mTimeout_hour, mTimeout_minute;
    private int mElapseMinute, mRemaining, mElapseTime;

    public static StudentTimeUpdateFragment newInstance(Student student) {
        StudentTimeUpdateFragment fragment = new StudentTimeUpdateFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("selected_student", student);
        fragment.setArguments(bundle);
        return fragment;
    }

    public StudentTimeUpdateFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_time_update_fragment, container, false);
        id_tv = view.findViewById(R.id.id_textView);
        name_tv = view.findViewById(R.id.name_textView);
        course_tv = view.findViewById(R.id.course_textView);
        email_tv = view.findViewById(R.id.email_textView);
        contact_tv = view.findViewById(R.id.contact_textView);
        address_tv = view.findViewById(R.id.address_textView);
        remaining_tv = view.findViewById(R.id.remaining_textView);
        mTimeIn_hour_tv = view.findViewById(R.id.timeIn_hour_textView);
        mTimeInMinute_tv = view.findViewById(R.id.timeIn_minute_textView);
        mTimeOut_hour_tv = view.findViewById(R.id.timeOut_hour_textView);
        mTimeOut_minute_tv = view.findViewById(R.id.timeOut_minute_textView);
        elapse_btn = view.findViewById(R.id.time_elapse_button);
        mTimein_term_tv = view.findViewById(R.id.timein_term_textView);
        mTimeout_term_tv = view.findViewById(R.id.timeout_term_textView);

        mStudentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            mStudent = getArguments().getParcelable("selected_student");
            if (mStudent != null) {
                mTimein_minute = mStudent.getTimein_minute();
                mTimeout_minute = mStudent.getTimeout_minute();
                mElapseMinute = mStudent.getElapse_minute();
                mTimein_hour = mStudent.getTimein_hour();
                mTimeout_hour = mStudent.getTimeout_hour();
                mRemaining = mStudent.getRemaining();
                mStudentId = mStudent.getT_id();
            }


            assert mStudent != null;
            id_tv.setText(String.valueOf(mStudent.getT_id()));
            name_tv.setText(mStudent.getName());
            course_tv.setText(mStudent.getCourse());
            email_tv.setText(mStudent.getEmail());
            contact_tv.setText(mStudent.getContact());
            address_tv.setText(mStudent.getAddress());
            remaining_tv.setText(String.valueOf(mStudent.getRemaining()));


            //set 24hr to 12hr
            timein_timeout_hour(mTimein_hour, mTimeout_hour);

            if (mTimein_minute == 0) {
                mTimeInMinute_tv.setText("00");
            } else {
                mTimeInMinute_tv.setText(String.valueOf(mTimein_minute));
            }
            if (mTimeout_minute == 0) {
                mTimeOut_minute_tv.setText("00");
            } else {
                mTimeOut_minute_tv.setText(String.valueOf(mTimeout_minute));
            }

            elapse_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String timein_hour = mTimeIn_hour_tv.getText().toString();
                    String timeout_hour = mTimeOut_hour_tv.getText().toString();
                    setElapseTime(Integer.parseInt(timein_hour), mTimein_minute, Integer.parseInt(timeout_hour), mTimeout_minute);
                }
            });
        }
    }

    private void setElapseTime(int timein_hour, int timein_minute, int timeout_hour, int timeout_minute) {

        String timein_term = mTimein_term_tv.getText().toString().trim();
        String timeout_term = mTimeout_term_tv.getText().toString().trim();
        int elapse_minute = timein_minute + timeout_minute;
        mElapseMinute = mElapseMinute + elapse_minute; //20
        if (timein_term.equals("am") && timeout_term.equals("am")) {
            if (timein_hour == timeout_hour) {
                Log.d(COMMON_TAG, TAG + " result: invalid");
            }
            if (timein_hour < timeout_hour) {
                if (mElapseMinute > 60) {
                    mElapseMinute = mElapseMinute - 60;
                    mElapseTime = (timeout_hour - timein_hour) + 1;
                }
                if (mElapseMinute < 60) {
                    mElapseTime = (timeout_hour - timein_hour);
                }
                if (mElapseMinute == 60) {
                    mElapseMinute = 0;
                    mElapseTime = ((timeout_hour - timein_hour) + 1);
                }
                Log.d(COMMON_TAG, TAG + " timein < timeout");
            }
            if (timein_hour > timeout_hour) {
                if (mElapseMinute > 60) {
                    mElapseMinute = mElapseMinute - 60;
                    mElapseTime = ((24 + timeout_hour) - (timein_hour)) + 1;
                }
                if (mElapseMinute < 60) {
                    mElapseTime = ((24 + timeout_hour) - (timein_hour));
                }
                if (mElapseMinute == 60) {
                    mElapseMinute = 0;
                    mElapseTime = ((24 + timeout_hour) - (timein_hour)) + 1;
                }
            }
            Log.d(COMMON_TAG, TAG + " mElapseMite: " + mElapseMinute);
        }
        if (timein_term.equals("pm") && timeout_term.equals("pm")) {
            if (timein_hour == timeout_hour) {
                Log.d(COMMON_TAG, TAG + " result: invalid");
            }
            if (timein_hour < timeout_hour) {
                if (mElapseMinute > 60) {
                    mElapseMinute = (mElapseMinute - 60);
                    mElapseTime = (timeout_hour - timein_hour) + 1;
                    Log.d(COMMON_TAG, TAG + " mElapseMinute > 60");
                }
                if (mElapseMinute < 60) {
                    mElapseTime = (timeout_hour - timein_hour);
                    Log.d(COMMON_TAG, TAG + " mElapseMinute < 60");
                }
                if (mElapseMinute == 60) {
                    mElapseMinute = 0;
                    mElapseTime = (timeout_hour - timein_hour) + 1;
                    Log.d(COMMON_TAG, TAG + " mElapseMinute == 60");
                }
            }
            if (timein_hour > timeout_hour) {
                if (mElapseMinute > 60) {
                    mElapseMinute = mElapseMinute - 60;
                    mElapseTime = ((24 + timeout_hour) - (timein_hour)) + 1;
                }
                if (mElapseMinute < 60) {
                    mElapseTime = ((24 + timeout_hour) - (timein_hour));
                }
                if (mElapseMinute == 60) {
                    mElapseMinute = 0;
                    mElapseTime = ((24 + timeout_hour) - (timein_hour)) + 1;
                }
                Log.d(COMMON_TAG, TAG + " timein > timeout");
            }
        }
        if (timein_term.equals("am") && timeout_term.equals("pm")) {
            if (timein_hour == timeout_hour) {
                if (mElapseMinute > 60) {
                    mElapseMinute = mElapseMinute - 60;
                    mElapseTime = ((12 + timeout_hour) - (timein_hour)) + 1;
                }
                if (mElapseMinute < 60) {
                    mElapseTime = ((12 + timeout_hour) - (timein_hour));
                }
                if (mElapseMinute == 60) {
                    mElapseMinute = 0;
                    mElapseTime = ((12 + timeout_hour) - (timein_hour)) + 1;
                }
            }
            if (timein_hour < timeout_hour) {
                if (mElapseMinute > 60) {
                    mElapseMinute = mElapseMinute - 60;
                    mElapseTime = ((12 + timeout_hour) - (timein_hour)) + 1;
                }
                if (mElapseMinute < 60) {
                    mElapseTime = ((12 + timeout_hour) - (timein_hour));
                }
                if (mElapseMinute == 60) {
                    mElapseMinute = 0;
                    mElapseTime = ((12 + timeout_hour) - (timein_hour)) + 1;
                }
            }
            if (timein_hour > timeout_hour) {
                if (mElapseMinute > 60) {
                    mElapseMinute = mElapseMinute - 60;
                    mElapseTime = ((12 + timeout_hour) - (timein_hour)) + 1;
                }
                if (mElapseMinute < 60) {
                    mElapseTime = ((12 + timeout_hour) - (timein_hour));
                }
                if (mElapseMinute == 60) {
                    mElapseMinute = 0;
                    mElapseTime = ((12 + timeout_hour) - (timein_hour)) + 1;
                }
            }
        }
        if (timein_term.equals("pm") && timeout_term.equals("am")) {
            if (timein_hour == timeout_hour) {
                if (mElapseMinute > 60) {
                    mElapseMinute = mElapseMinute - 60;
                    mElapseTime = ((12 + timeout_hour) - (timein_hour)) + 1;
                }
                if (mElapseMinute < 60) {
                    mElapseTime = ((12 + timeout_hour) - (timein_hour));
                }
                if (mElapseMinute == 60) {
                    mElapseMinute = 0;
                    mElapseTime = ((12 + timeout_hour) - (timein_hour)) + 1;
                }
            }
            if (timein_hour < timeout_hour) {
                if (mElapseMinute > 60) {
                    mElapseMinute = mElapseMinute - 60;
                    mElapseTime = ((12 + timeout_hour) - (timein_hour)) + 1;
                }
                if (mElapseMinute < 60) {
                    mElapseTime = ((12 + timeout_hour) - (timein_hour));
                }
                if (mElapseMinute == 60) {
                    mElapseMinute = 0;
                    mElapseTime = ((12 + timeout_hour) - (timein_hour)) + 1;
                }
            }
            if (timein_hour > timeout_hour) {
                if (mElapseMinute > 60) {
                    mElapseMinute = mElapseMinute - 60;
                    mElapseTime = ((12 + timeout_hour) - (timein_hour)) + 1;
                }
                if (mElapseMinute < 60) {
                    mElapseTime = ((12 + timeout_hour) - (timein_hour));
                }
                if (mElapseMinute == 60) {
                    mElapseMinute = 0;
                    mElapseTime = ((12 + timeout_hour) - (timein_hour)) + 1;
                }
            }
        }

        int result = mRemaining - mElapseTime;
        if (result <= 0) {
            result = 0;
            remaining_tv.setText(String.valueOf(result));
            Log.d(COMMON_TAG, TAG + " mResult: " + result + ", mRemaining: " + mRemaining + ", mElapseTime: " + mElapseTime + ", elapse minute: " + mElapseMinute);
            mStudentViewModel.time_elapse(mStudentId, result, mElapseMinute);
        } else {
            remaining_tv.setText(String.valueOf(result));
            Toast.makeText(getActivity(), "Time Elapse: " + mElapseTime + " hr. and " + mElapseMinute + " min.", Toast.LENGTH_LONG).show();
            Log.d(COMMON_TAG, TAG + " mResult: " + result + ", mRemaining: " + mRemaining + ", mElapseTime: " + mElapseTime + ", elapse minute: " + mElapseMinute);
            mStudentViewModel.time_elapse(mStudentId, result, mElapseMinute);
            elapse_btn.setEnabled(false);
            elapse_btn.setClickable(false);
            elapse_btn.setTextColor(Color.GRAY);
        }
    }


    private void timein_timeout_hour(int timein_hour, int timeout_hour) {
        if (timein_hour >= 12) {
            mTimein_term_tv.setText("pm");
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

        } else {
            mTimein_term_tv.setText("am");
            if (timein_hour == 0) {
                mTimeIn_hour_tv.setText("12");
            } else {
                mTimeIn_hour_tv.setText(String.valueOf(timein_hour));
            }
        }


        if (timeout_hour >= 12) {
            mTimeout_term_tv.setText("pm");
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

        } else {
            mTimeout_term_tv.setText("am");
            if (timeout_hour == 0) {
                mTimeOut_hour_tv.setText("12");
            } else {
                mTimeOut_hour_tv.setText(String.valueOf(timeout_hour));
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        name_tv = null;
        course_tv = null;
        email_tv = null;
        contact_tv = null;
        address_tv = null;
        id_tv = null;
        remaining_tv = null;
        mTimeIn_hour_tv = null;
        mTimeInMinute_tv = null;
        mTimeOut_hour_tv = null;
        mTimeOut_minute_tv = null;
        mTimein_term_tv = null;
        mTimeout_term_tv = null;
        elapse_btn = null;
        mStudentViewModel = null;
        mStudent = null;
    }
}
