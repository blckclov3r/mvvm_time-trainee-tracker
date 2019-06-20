package com.example.datetimerecord.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.datetimerecord.R;
import com.example.datetimerecord.model.Student;
import com.example.datetimerecord.viewmodel.StudentViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class AddStudentFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "AddStudentFragment";
    private static final String COMMON_TAG = "mAppLog";

    private EditText name_et,course_et,email_et,contact_et,address_et;
    private Button addStudent_Btn;
    private StudentViewModel mViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_student_fragment,container,false);
        name_et = view.findViewById(R.id.name_editText);
        course_et = view.findViewById(R.id.course_editText);
        email_et = view.findViewById(R.id.email_editText);
        contact_et = view.findViewById(R.id.contact_editText);
        address_et = view.findViewById(R.id.address_editText);
        addStudent_Btn = view.findViewById(R.id.addStudent_button);
        addStudent_Btn.setOnClickListener(this);
        mViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        return view;
    }

    @Override
    public void onClick(View v) {
        String name = name_et.getText().toString().trim();
        String course = course_et.getText().toString().trim();
        String email = email_et.getText().toString().trim();
        String contact = contact_et.getText().toString().trim();
        String address = address_et.getText().toString().trim();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String dateFormat = simpleDateFormat.format(new Date());
        Log.d(COMMON_TAG,TAG+" dataFormat: "+dateFormat);
        Student student = new Student(name,course,email,contact,address,dateFormat);
        mViewModel.insert(student);
        Toast.makeText(getActivity(), "Student successfully created", Toast.LENGTH_SHORT).show();
        setClear();
    }

    public void setClear(){
        name_et.setText("");
        course_et.setText("");
        email_et.setText("");
        contact_et.setText("");
        address_et.setText("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel = null;
    }
}
