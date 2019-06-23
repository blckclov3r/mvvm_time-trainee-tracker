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

    private int remaining;

    @Ignore
    public Student(){}

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
    public Student(String name, String course, String email, String contact, String address) {
        this.name = name;
        this.course = course;
        this.email = email;
        this.contact = contact;
        this.address = address;
    }

    public Student(String name, String course, String email, String contact, String address, String timestamp, int remaining) {
        this.name = name;
        this.course = course;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.timestamp = timestamp;
        this.remaining = remaining;
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
                '}';
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
    }
}
