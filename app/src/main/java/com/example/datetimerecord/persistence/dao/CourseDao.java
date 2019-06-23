package com.example.datetimerecord.persistence.dao;

import com.example.datetimerecord.model.Course;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CourseDao {
    @Insert
    void insert(Course course);
    @Update
    void update(Course course);
    @Delete
    void delete(Course course);

    @Query("SELECT * FROM course_table ORDER BY mcourse ASC")
    LiveData<List<Course>> getAllCourse();

    @Query("DELETE FROM course_table")
    void deleteAllCourse();


    @Query("SELECT * FROM course_table WHERE mcourse=:course LIMIT 1")
    Course getCourseTime(String course);
}
