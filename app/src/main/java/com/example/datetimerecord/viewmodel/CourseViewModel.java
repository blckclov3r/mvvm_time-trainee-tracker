package com.example.datetimerecord.viewmodel;

import android.app.Application;

import com.example.datetimerecord.model.Course;
import com.example.datetimerecord.persistence.CourseRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CourseViewModel extends AndroidViewModel {
    private CourseRepository mRepository;
    private LiveData<List<Course>> allCourse;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CourseRepository(application);
        allCourse = mRepository.getmAllCourse();
    }

    public void insert(Course course){
        mRepository.insert(course);
    }

    public LiveData<List<Course>> getAllCourse(){
        return allCourse;
    }

}
