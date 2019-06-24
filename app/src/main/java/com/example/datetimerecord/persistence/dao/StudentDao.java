package com.example.datetimerecord.persistence.dao;

import com.example.datetimerecord.model.Student;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface StudentDao {

    @Insert
    void insert(Student student);
    @Update
    void update(Student student);
    @Delete
    void delete(Student student);

    @Query("SELECT * FROM student_table ORDER BY name ASC")
    LiveData<List<Student>> getAllTrainee();

}
