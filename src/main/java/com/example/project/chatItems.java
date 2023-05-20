package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class chatItems extends AppCompatActivity {

    private String userName, message,pid, data,time;

    public chatItems(){}

    public chatItems(String userName, String message, String pid, String data, String time) {
        this.userName = userName;
        this.message = message;
        this.pid = pid;
        this.data = data;
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}