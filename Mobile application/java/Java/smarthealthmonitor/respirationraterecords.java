package com.example.smarthealthmonitor;

import androidx.fragment.app.Fragment;

public class respirationraterecords extends Fragment {
    private String Month, Date;
    private long RRReading;

    private respirationraterecords(){}
    private respirationraterecords(long RRReading, String Month, String Date, String RrStatus){

        this.RRReading = RRReading;
        this.Month = Month;
        this.Date = Date;
    }

    public long getRRReading() {
        return RRReading;
    }

    public void setRRReading(long RRReading) {
        this.RRReading = RRReading;
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

    public String getRrStatus() {
        String rrstatus = new String();
        if(RRReading > 20){
            rrstatus = "High Respiration rate";
        }else if(RRReading < 12){
            rrstatus = "Low Respiration rate";
        } else {
            rrstatus = "Normal";
        }
        return rrstatus;
    }

    public void setRrStatus(String RrStatus) {
        //
    }

}