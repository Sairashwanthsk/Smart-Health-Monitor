package com.example.smarthealthmonitor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class subMessageFragment extends Fragment {

    private String Date, Title, Message;

    private subMessageFragment(){}
    private subMessageFragment(String Date, String Title, String Message){
        this.Date = Date;
        this.Title = Title;
        this.Message = Message;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
}