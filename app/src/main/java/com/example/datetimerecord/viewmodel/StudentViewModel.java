package com.example.datetimerecord.viewmodel;

import android.app.Application;

import com.example.datetimerecord.model.Student;
import com.example.datetimerecord.persistence.StudentRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class StudentViewModel extends AndroidViewModel {
    private StudentRepository mRepository;
    private LiveData<List<Student>> mAllStudents;

    public StudentViewModel(@NonNull Application application) {
        super(application);
        mRepository = new StudentRepository(application);
        mAllStudents = mRepository.getmAllStudents();
    }
    public void insert(Student student){
        mRepository.insert(student);
    }
    public LiveData<List<Student>> getmAllStudents(){
        return mAllStudents;
    }
}
