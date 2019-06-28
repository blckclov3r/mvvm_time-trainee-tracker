package com.example.datetimerecord.persistence.dao;

import com.example.datetimerecord.model.AppLog;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface LogDao {

    @Insert
    void insert(AppLog appLog);

    @Delete
    void delete(AppLog appLog);

    @Query("SELECT * FROM log_table ORDER BY log_id DESC")
    LiveData<List<AppLog>> getAllLog();

    @Query("DELETE FROM log_table")
    void deleteAllLog();
}
