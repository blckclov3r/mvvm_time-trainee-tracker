package com.example.datetimerecord.viewmodel;

import android.app.Application;

import com.example.datetimerecord.model.AppLog;
import com.example.datetimerecord.persistence.repository.LogRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LogViewModel extends AndroidViewModel {
    private LogRepository mRepository;
    private LiveData<List<AppLog>> mAllLog;


    public LogViewModel(@NonNull Application application) {
        super(application);
        mRepository = new LogRepository(application);
        mAllLog = mRepository.getAllLog();
    }

    public void insert(AppLog appLog){
        mRepository.insert(appLog);
    }

    public void delete(AppLog appLog){
        mRepository.delete(appLog);
    }

    public LiveData<List<AppLog>> getAllLog(){
        return mAllLog;
    }
}
