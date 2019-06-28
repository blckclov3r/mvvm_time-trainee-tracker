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

    @Query("SELECT * FROM student_table WHERE name LIKE :student ORDER BY name ASC")
    LiveData<List<Student>> getAllTrainee(String student);

    @Query("UPDATE student_table SET remaining =:remaining,elapse_minute =:elapse_minute WHERE t_id=:id")
    void time_elapse(int id,int remaining,int elapse_minute);

    @Query("SELECT * FROM student_table")
    List<Student> getStudentId();

    @Query("SELECT * FROM student_table WHERE course=:course")
    List<Student> getStudentCourse(String course);

    @Query("DELETE FROM student_table")
    void deleteAllStudents();



}
