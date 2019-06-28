package com.example.datetimerecord.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "log_table")
public class AppLog implements Parcelable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "log_id")
    private int id;
    private String message;
    private String timestamp;

    public AppLog(){}

   @Ignore
    public AppLog(String message, String timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    @Ignore
    @Override
    public String toString() {
        return "AppLog{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
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
        dest.writeInt(id);
        dest.writeString(message);
        dest.writeString(timestamp);
    }
    @Ignore
    protected AppLog(Parcel in) {
        id = in.readInt();
        message = in.readString();
        timestamp = in.readString();
    }

    @Ignore
    public static final Creator<AppLog> CREATOR = new Creator<AppLog>() {
        @Override
        public AppLog createFromParcel(Parcel in) {
            return new AppLog(in);
        }

        @Override
        public AppLog[] newArray(int size) {
            return new AppLog[size];
        }
    };
}
