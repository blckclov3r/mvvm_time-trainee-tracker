package com.example.datetimerecord.persistence;

import android.content.Context;
import android.os.AsyncTask;

import com.example.datetimerecord.model.Course;
import com.example.datetimerecord.model.Student;
import com.example.datetimerecord.persistence.dao.CourseDao;
import com.example.datetimerecord.persistence.dao.StudentDao;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Student.class, Course.class}, version = 2)
public abstract class MyDatabase extends RoomDatabase {

    public abstract CourseDao mCourseDao();
    public abstract StudentDao mStudentDao();

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
        public PopulateCourseAsyncTask(MyDatabase db){
            courseDao = db.mCourseDao();
        }
        @Override
        protected Void doInBackground(Void... courses) {
            courseDao.insert(new Course("Java",300,"Java Programming language",0,0,0,0));
            courseDao.insert(new Course("PHP",200,"PHP programming language",0,0,0,0));
            courseDao.insert(new Course("C++",400,"c++ Programming language",0,0,0,0));
            courseDao.insert(new Course("C#",400,"C# Programming language",0,0,0,0));
            return null;
        }
    }

    private static class PopulateAsyncTask extends AsyncTask<Void,Void,Void>{
        public StudentDao studentDao;
        public PopulateAsyncTask(MyDatabase db){
            studentDao= db.mStudentDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            studentDao.insert(new Student("Aljun Abrenica","Java","aljun_abrenica@yahoo.com",
                    "09555076271","???","???",100,1,0,3,0));
            studentDao.insert(new Student("Nujla Acinerba","C++","blckclov3r@gmail.com",
                    "09555076271","???","???",200,3,0,6,0));
            studentDao.insert(new Student("Junz D Silenthacker","C#","blckclov3r@gmail.com",
                    "09555076271","???","???",300,6,0,9,0));
            return null;
        }
    }
}
