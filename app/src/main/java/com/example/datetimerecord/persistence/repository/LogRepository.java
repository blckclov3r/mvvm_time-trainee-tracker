package com.example.datetimerecord.persistence.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.datetimerecord.model.AppLog;
import com.example.datetimerecord.persistence.MyDatabase;
import com.example.datetimerecord.persistence.dao.LogDao;

import java.util.List;

import androidx.lifecycle.LiveData;

public class LogRepository {
    private LogDao mLogDao;
    private LiveData<List<AppLog>> mAllLog;

    public LogRepository(Application application){
        MyDatabase myDatabase = MyDatabase.getInstance(application);
        mLogDao = myDatabase.mLogDao();
        mAllLog = mLogDao.getAllLog();
    }



    public void insert(AppLog appLog){
        new InsertAsyncTask(mLogDao).execute(appLog);
    }

    public void delete(AppLog appLog){
        new DeleteAsyncTask(mLogDao).execute(appLog);
    }

    public LiveData<List<AppLog>> getAllLog(){
        return mAllLog;
    }

    private static class InsertAsyncTask extends AsyncTask<AppLog,Void,Void>{
        private LogDao logDao;

        public InsertAsyncTask(LogDao logDao) {
            this.logDao = logDao;
        }

        @Override
        protected Void doInBackground(AppLog... appLogs) {
            logDao.insert(appLogs[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<AppLog,Void,Void>{
        private LogDao logDao;

        public DeleteAsyncTask(LogDao logDao) {
            this.logDao = logDao;
        }

        @Override
        protected Void doInBackground(AppLog... appLogs) {
            logDao.delete(appLogs[0]);
            return null;
        }
    }
}
