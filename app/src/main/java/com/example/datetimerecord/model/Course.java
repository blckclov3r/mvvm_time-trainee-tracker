package com.example.datetimerecord.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_table")
public class Course {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int c_id;
    @ColumnInfo(name = "mcourse")
    private String course;
    @ColumnInfo(name = "course_time")
    private int course_time;
    @ColumnInfo(name = "description")
    private String description;

    @Ignore
    public Course(){}

    public Course(String course, int course_time, String description) {
        this.course = course;
        this.course_time = course_time;
        this.description = description;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getCourse_time() {
        return course_time;
    }

    public void setCourse_time(int course_time) {
        this.course_time = course_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Course{" +
                "c_id=" + c_id +
                ", course='" + course + '\'' +
                ", course_time=" + course_time +
                ", description='" + description + '\'' +
                '}';
    }
}
