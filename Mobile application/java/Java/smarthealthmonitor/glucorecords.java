package com.example.smarthealthmonitor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class glucorecords extends Fragment {
    private String Month, Date;
    private long GReading;

    private glucorecords(){}
    private glucorecords(long GReading, String Month, String Date, String GStatus){

        this.GReading = GReading;
        this.Month = Month;
        this.Date = Date;
    }

    public long getGReading() {
        return GReading;
    }

    public void setGReading(long GReading) {
        this.GReading = GReading;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String Month) {
        this.Month = Month;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getGStatus() {
        String gstatus = new String();
        if((GReading > 140 && GReading <= 199)){
            gstatus = "Pre-Diabetes";
        }else if(GReading >= 200){
            gstatus = "Diabetes";
        } else {
            gstatus = "Normal";
        }
        return gstatus;
    }

    public void setGStatus(String GStatus) {
        //
    }

}