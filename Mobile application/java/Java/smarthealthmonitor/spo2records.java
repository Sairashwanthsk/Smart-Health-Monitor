package com.example.smarthealthmonitor;

import androidx.fragment.app.Fragment;

public class spo2records extends Fragment {
    private String Month, Date;
    private long O2Reading;

    private spo2records(){}
    private spo2records(long O2Reading, String Month, String Date, String O2Status){

        this.O2Reading = O2Reading;
        this.Month = Month;
        this.Date = Date;
    }

    public long getO2Reading() {
        return O2Reading;
    }

    public void setO2Reading(long O2Reading) {
        this.O2Reading = O2Reading;
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

    public String getO2Status() {
        String o2status = new String();
        if(O2Reading < 98){
            o2status = "Hypoxemia";
        } else {
            o2status = "Normal";
        }
        return o2status;
    }

    public void setO2Status(String O2Status) {
        //
    }

}
