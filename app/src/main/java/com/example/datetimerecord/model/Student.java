package com.example.datetimerecord.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "student_table")
public class Student {

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

    @Ignore
    public Student(){}

    public Student(String name, String course, String email, String contact, String address, String timestamp) {
        this.name = name;
        this.course = course;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.timestamp = timestamp;
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
}
