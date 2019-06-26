package com.example.datetimerecord.persistence;

import android.content.Context;
import android.os.AsyncTask;

import com.example.datetimerecord.model.Student;
import com.example.datetimerecord.persistence.dao.StudentDao;

import java.util.List;

import androidx.lifecycle.LiveData;

public class StudentRepository {
    private StudentDao mStudentDao;
    private LiveData<List<Student>> mAllStudents;
    private MyDatabase myDatabase;

    public StudentRepository(Context context){
        myDatabase = MyDatabase.getInstance(context);
        mStudentDao = myDatabase.mStudentDao();
        mAllStudents = mStudentDao.getAllTrainee();
    }

    public void insert(Student student){
        new InsertStudentAsyncTask(mStudentDao).execute(student);
    }

    public void update(Student student){
        new UpdateStudentAsyncTask(mStudentDao).execute(student);
    }

    public void delete(Student student){
         new DeleteStudentAsyncTask(mStudentDao).execute(student);
    }

    public void time_elapse(int id,int remaining,int minute){
        new TimeElapseAsyncTask(mStudentDao).execute(id,remaining,minute);
    }

    public List<Student> getStudentId(){
        return mStudentDao.getStudentId();
    }

    public LiveData<List<Student>> getAllStudents(){
        return mAllStudents;
    }

    public List<Student> getStudentCourse(String course){
        return mStudentDao.getStudentCourse(course);
    }

    private static class InsertStudentAsyncTask extends AsyncTask<Student,Void,Void>{
        private StudentDao studentDao;

        public InsertStudentAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.insert(students[0]);
            return null;
        }
    }

    private static class UpdateStudentAsyncTask extends AsyncTask<Student,Void,Void>{
        private StudentDao studentDao;

        public UpdateStudentAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.update(students[0]);
            return null;
        }
    }

    private static class DeleteStudentAsyncTask extends AsyncTask<Student,Void,Void>{
        private StudentDao studentDao;
        public DeleteStudentAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.delete(students[0]);
            return null;
        }
    }



    private static class TimeElapseAsyncTask extends AsyncTask<Integer,Void,Void>{
        private StudentDao studentDao;

        public TimeElapseAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            studentDao.time_elapse(integers[0],integers[1],integers[2]);
            return null;
        }
    }
}
