package com.example.datetimerecord.persistence;

import android.app.Application;
import android.os.AsyncTask;

import com.example.datetimerecord.model.Course;
import com.example.datetimerecord.persistence.dao.CourseDao;

import java.util.List;

import androidx.lifecycle.LiveData;

public class CourseRepository {

    private CourseDao mCourseDao;
    private LiveData<List<Course>> mAllCourse;

    public CourseRepository(Application application) {
        MyDatabase myDatabase = MyDatabase.getInstance(application);
        mCourseDao = myDatabase.mCourseDao();
        mAllCourse = mCourseDao.getAllCourse();
    }

    public void insert(Course course) {
        new InsertAsyncTask(mCourseDao).execute(course);
    }

    public void update(Course course) {

    }

    public void delete(Course course) {

    }

    public void deleteAllCourse() {

    }

    public LiveData<List<Course>> retrieveAllCourse(String course) {
        return  mCourseDao.retrieveAllCourse(course);
    }

    public LiveData<List<Course>> getmAllCourse() {
        return mAllCourse;
    }

    public static class InsertAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDao courseDao;

        public InsertAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.insert(courses[0]);
            return null;
        }
    }



}
