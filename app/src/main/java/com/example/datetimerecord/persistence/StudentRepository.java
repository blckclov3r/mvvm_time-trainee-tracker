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

    }

    public void delete(Student student){

    }

    public void deleteAllAllStudents(){

    }

    public LiveData<List<Student>> getmAllStudents(){
        return mAllStudents;
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

}
