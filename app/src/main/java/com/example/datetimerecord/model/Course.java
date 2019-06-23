package com.example.datetimerecord.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_table")
public class Course implements Parcelable {
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

    protected Course(Parcel in) {
        c_id = in.readInt();
        course = in.readString();
        course_time = in.readInt();
        description = in.readString();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(c_id);
        dest.writeString(course);
        dest.writeInt(course_time);
        dest.writeString(description);
    }
}
