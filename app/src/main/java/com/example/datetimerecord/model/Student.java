package com.example.datetimerecord.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "student_table")
public class Student implements Parcelable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int t_id;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "course")
    private String course;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "contact")
    private String contact;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "timestamp")
    private String timestamp;
    @ColumnInfo(name = "remaining")
    private int remaining;
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
    @ColumnInfo(name = "elapse_minute")
    private int elapse_minute;

    public Student(){}


    @Ignore
    public Student(String name, String course, String email, String contact, String address) {
        this.name = name;
        this.course = course;
        this.email = email;
        this.contact = contact;
        this.address = address;
    }

    @Ignore
    public Student(String name, String course, String email, String contact, String address, String timestamp) {
        this.name = name;
        this.course = course;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.timestamp = timestamp;
    }



    @Ignore
    public Student(String name, String course, String email, String contact, String address, String timestamp, int remaining) {
        this.name = name;
        this.course = course;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.timestamp = timestamp;
        this.remaining = remaining;
    }

    @Ignore
    public Student(@NonNull String name, @NonNull String course, @NonNull String email, @NonNull String contact,
                   @NonNull String address, @NonNull String timestamp, int remaining, int timein_hour, int timein_minute,
                   int timeout_hour, int timeout_minute) {
        this.name = name;
        this.course = course;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.timestamp = timestamp;
        this.remaining = remaining;
        this.timein_hour = timein_hour;
        this.timein_minute = timein_minute;
        this.timeout_hour = timeout_hour;
        this.timeout_minute = timeout_minute;
    }

    @Ignore
    public Student(String name, String course, String email, String contact, String address, String timestamp,
                   int remaining, int timein_hour, int timein_minute, int timeout_hour, int timeout_minute, int elapse_minute) {
        this.name = name;
        this.course = course;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.timestamp = timestamp;
        this.remaining = remaining;
        this.timein_hour = timein_hour;
        this.timein_minute = timein_minute;
        this.timeout_hour = timeout_hour;
        this.timeout_minute = timeout_minute;
        this.elapse_minute = elapse_minute;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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

    public int getElapse_minute() {
        return elapse_minute;
    }

    public void setElapse_minute(int elapse_minute) {
        this.elapse_minute = elapse_minute;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(t_id);
        dest.writeString(name);
        dest.writeString(course);
        dest.writeString(email);
        dest.writeString(contact);
        dest.writeString(address);
        dest.writeString(timestamp);
        dest.writeInt(remaining);
        dest.writeInt(timein_hour);
        dest.writeInt(timein_minute);
        dest.writeInt(timeout_hour);
        dest.writeInt(timeout_minute);
        dest.writeInt(elapse_minute);
    }

    protected Student(Parcel in) {
        t_id = in.readInt();
        name = in.readString();
        course = in.readString();
        email = in.readString();
        contact = in.readString();
        address = in.readString();
        timestamp = in.readString();
        remaining = in.readInt();
        timein_hour = in.readInt();
        timein_minute = in.readInt();
        timeout_hour = in.readInt();
        timeout_minute = in.readInt();
        elapse_minute = in.readInt();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public String toString() {
        return "Student{" +
                "t_id=" + t_id +
                ", name='" + name + '\'' +
                ", course='" + course + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", remaining=" + remaining +
                ", timein_hour=" + timein_hour +
                ", timein_minute=" + timein_minute +
                ", timeout_hour=" + timeout_hour +
                ", timeout_minute=" + timeout_minute +
                ", elapse_minute=" + elapse_minute +
                '}';
    }
}
