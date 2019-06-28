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
    @ColumnInfo(name = "course")
    private String course;
    @ColumnInfo(name = "course_time")
    private int course_time;
    @ColumnInfo(name = "description")
    private String description;
    //timein hour and minute
    @ColumnInfo(name = "timein_hour")
    private int timein_hour;
    @ColumnInfo(name = "timein_minute")
    private int timein_minute;
    //timeout hour and minute
    @ColumnInfo(name = "timeout_hour")
    private int timeout_hour;
    @ColumnInfo(name = "timeout_minute")
    private int timeout_minute;

    public Course(){}

    @Ignore
    public Course(String course, int course_time, String description) {
        this.course = course;
        this.course_time = course_time;
        this.description = description;
    }

    @Ignore
    public Course(String course, int course_time, String description, int timein_hour, int timein_minute, int timeout_hour, int timeout_minute) {
        this.course = course;
        this.course_time = course_time;
        this.description = description;
        this.timein_hour = timein_hour;
        this.timein_minute = timein_minute;
        this.timeout_hour = timeout_hour;
        this.timeout_minute = timeout_minute;
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

    public int getTimein_hour() {
        return timein_hour;
    }

    public void setTimein_hour(int timein_hour) {
        this.timein_hour = timein_hour;
    }

    public int getTimein_minute() {
        return timein_minute;
    }

    public void setTimein_minute(int timein_minute) {
        this.timein_minute = timein_minute;
    }

    public int getTimeout_hour() {
        return timeout_hour;
    }

    public void setTimeout_hour(int timeout_hour) {
        this.timeout_hour = timeout_hour;
    }

    public int getTimeout_minute() {
        return timeout_minute;
    }

    public void setTimeout_minute(int timeout_minute) {
        this.timeout_minute = timeout_minute;
    }


    @Ignore
    @Override
    public String toString() {
        return "Course{" +
                "c_id=" + c_id +
                ", course='" + course + '\'' +
                ", course_time=" + course_time +
                ", description='" + description + '\'' +
                ", timein_hour=" + timein_hour +
                ", timein_minute=" + timein_minute +
                ", timeout_hour=" + timeout_hour +
                ", timeout_minute=" + timeout_minute +
                '}';
    }


    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(c_id);
        dest.writeString(course);
        dest.writeInt(course_time);
        dest.writeString(description);
        dest.writeInt(timein_hour);
        dest.writeInt(timein_minute);
        dest.writeInt(timeout_hour);
        dest.writeInt(timeout_minute);
    }

    @Ignore
    protected Course(Parcel in) {
        c_id = in.readInt();
        course = in.readString();
        course_time = in.readInt();
        description = in.readString();
        timein_hour = in.readInt();
        timein_minute = in.readInt();
        timeout_hour = in.readInt();
        timeout_minute = in.readInt();
    }

    @Ignore
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
}
