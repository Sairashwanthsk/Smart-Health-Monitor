package com.example.smarthealthmonitor;

import androidx.fragment.app.Fragment;

public class Cholesterolrecords extends Fragment {
    private String Month, Date;
    private long CReading;

    private Cholesterolrecords(){}
    private Cholesterolrecords(long CReading, String Month, String Date, String CStatus){

        this.CReading = CReading;
        this.Month = Month;
        this.Date = Date;
    }

    public long getCReading() {
        return CReading;
    }

    public void setCReading(long CReading) {
        this.CReading = CReading;
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

    public String getCStatus() {
        String cstatus = new String();
        if(CReading > 200){
            cstatus = "High Cholesterol";
        }else if(CReading < 125){
            cstatus = "Low Cholesterol";
        } else {
            cstatus = "Normal";
        }
        return cstatus;
    }

    public void setCStatus(String CStatus) {
        //
    }

}

