package com.example.datetimerecord.activity.dialog;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.datetimerecord.R;
import com.example.datetimerecord.viewmodel.CourseViewModel;
import com.example.datetimerecord.viewmodel.LogViewModel;
import com.example.datetimerecord.viewmodel.StudentViewModel;
import java.util.Objects;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class DeleteCustomDialog extends DialogFragment {

    private static final String TAG = DeleteCustomDialog.class.getSimpleName();
    private static final String COMMON_TAG = "mAppLog";

    private EditText delete_et;
    private Button delete_btn;

    //vars
    private StudentViewModel mStudentViewModel;
    private LogViewModel mLogViewModel;
    private CourseViewModel mCourseViewModel;
    private String mDelete;
    private long mLastClick = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_delete_dialog, container, false);
        delete_btn = view.findViewById(R.id.delete_button);
        delete_et = view.findViewById(R.id.delete_editText);

        mCourseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        mStudentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        mLogViewModel = ViewModelProviders.of(this).get(LogViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClick < 1000) {
                    return;
                }
                mLastClick = SystemClock.elapsedRealtime();
                new SweetAlertDialog(Objects.requireNonNull(getActivity()), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Delete All List")
                        .setContentText("Are you sure?, This action can't be undone")
                        .setConfirmText("Yes")
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                if (SystemClock.elapsedRealtime() - mLastClick < 1000) {
                                    return;
                                }
                                mLastClick = SystemClock.elapsedRealtime();
                                sDialog.dismissWithAnimation();
                                mDelete = delete_et.getText().toString().trim().toLowerCase();
                                if (mDelete.equals("delete")) {
                                    getDialog().dismiss();
                                    mCourseViewModel.deleteAllCourse();
                                    mStudentViewModel.deleteAllStudent();
                                    mLogViewModel.deleteAllLog();
                                    new SweetAlertDialog(Objects.requireNonNull(getActivity()), SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Success")
                                            .setContentText("All list of students, course, and logs has deleted")
                                            .show();
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
        delete_et = null;
        delete_btn = null;
        mStudentViewModel = null;
        mLogViewModel = null;
        mCourseViewModel = null;
        Log.d(COMMON_TAG,TAG+" onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(COMMON_TAG,TAG+" onDetach");
    }
}
