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
        new UpdateAsyncTask(mCourseDao).execute(course);
    }

    public void delete(Course course) {
        new DeleteAsyncTask(mCourseDao).execute(course);
    }



    public Course getCourse(String course) {
        return mCourseDao.getCourse(course);
    }

    public LiveData<List<Course>> getmAllCourse() {
        return mAllCourse;
    }


    public List<Course> getCourse(){
        return mCourseDao.getCourse();
    }



















    private static class InsertAsyncTask extends AsyncTask<Course, Void, Void> {
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

    private static class UpdateAsyncTask extends AsyncTask<Course,Void,Void>{
        private CourseDao courseDao;

        public UpdateAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.update(courses[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Course,Void,Void>{
        private CourseDao courseDao;

        public DeleteAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.delete(courses[0]);
            return null;
        }
    }

}
