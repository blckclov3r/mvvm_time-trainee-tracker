package com.example.datetimerecord.persistence;

import android.content.Context;
import android.os.AsyncTask;

import com.example.datetimerecord.model.AppLog;
import com.example.datetimerecord.model.Course;
import com.example.datetimerecord.model.Student;
import com.example.datetimerecord.persistence.dao.CourseDao;
import com.example.datetimerecord.persistence.dao.LogDao;
import com.example.datetimerecord.persistence.dao.StudentDao;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Student.class, Course.class, AppLog.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    public abstract CourseDao mCourseDao();
    public abstract StudentDao mStudentDao();
    public abstract LogDao mLogDao();

    public static final String MYDATABASE = "mydatabase.db";

    private static MyDatabase mInstance = null;

    public synchronized static MyDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, MYDATABASE)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(courseRoomCallback)
                    .addCallback(studentRoomCallback)
                    .build();
        }
        return mInstance;
    }

    private static RoomDatabase.Callback courseRoomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateCourseAsyncTask(mInstance).execute();
        }
    };

    private static RoomDatabase.Callback studentRoomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsyncTask(mInstance).execute();
        }
    };

    private static class PopulateCourseAsyncTask extends AsyncTask<Void,Void,Void>{
        private CourseDao courseDao;
        private PopulateCourseAsyncTask(MyDatabase db){
            courseDao = db.mCourseDao();
        }
        @Override
        protected Void doInBackground(Void... courses) {
            courseDao.insert(new Course("PHP",0,"Lorem Ipsum",5,0,10,0));
            courseDao.insert(new Course("Dagger",0,"Lorem Ipsum",10,0,14,0));
            courseDao.insert(new Course("Android",0,"Lorem Ipsum",2,0,4,0));
            courseDao.insert(new Course("Django",0,"Lorem Ipsum",6,0,8,0));
            return null;
        }
    }

    private static class PopulateAsyncTask extends AsyncTask<Void,Void,Void>{
        private StudentDao studentDao;
        private PopulateAsyncTask(MyDatabase db){
            studentDao= db.mStudentDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            studentDao.insert(new Student("Aljun Abrenica","????","aljun_abrenica@yahoo.com",
                    "09555076271","???","???",0,0,0,0,0));
            studentDao.insert(new Student("Nujla Acinerba","????++","blckclov3r@gmail.com",
                    "09555076271","???","???",0,0,0,0,0));
            studentDao.insert(new Student("Junz D Silenthacker","????#","blckclov3r@gmail.com",
                    "09555076271","???","???",0,0,0,0,0));
            return null;
        }
    }
}
