package com.example.datetimerecord.viewmodel;

import android.app.Application;
import com.example.datetimerecord.model.Course;
import com.example.datetimerecord.persistence.repository.CourseRepository;
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

    public void insert(Course course) {
        mRepository.insert(course);
    }

    public void update(Course course) {
        mRepository.update(course);
    }

    public void delete(Course course) {
        mRepository.delete(course);
    }

    public LiveData<List<Course>> getAllCourse() {
        return allCourse;
    }

    public Course getCourse(String course) {
        return mRepository.getCourse(course);
    }

    public List<Course> getCourse(){
        return mRepository.getCourse();
    }

    public void deleteAllCourse(){
         mRepository.deleteAllCourse();
    }
}
