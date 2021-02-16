package com.example.smarthealthmonitor;

import androidx.fragment.app.Fragment;

public class Bprecords extends Fragment {
    private String Month, Date;
    private long SReading, DReading;

    private Bprecords(){}
    private Bprecords(long SReading, long DReading, String Month, String Date, String BpStatus){

        this.SReading = SReading;
        this.Month = Month;
        this.Date = Date;
    }

    public long getSReading() {
        return SReading;
    }

    public void setSReading(long SReading) {
        this.SReading = SReading;
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

    public String getBpStatus() {
        String bpstatus = new String();
        if((SReading > 120 && SReading < 130) && (DReading <= 90)){
            bpstatus = "Elevated BP";
        }else if((SReading >= 130 && SReading < 140) && (DReading <= 100)){
            bpstatus = "High BP stage-I";
        }else if((SReading >= 140 && SReading < 180) && (DReading <= 120)){
            bpstatus = "High BP stage-II";
        }else if(SReading > 180 && DReading > 120){
            bpstatus = "High BP stage-III";
        } else {
            bpstatus = "Normal";
        }
        return bpstatus;
    }

    public void setBpStatus(String BpStatus) {
        //
    }

    public long getDReading() {
        return DReading;
    }

    public void setDReading(long DReading) {
        this.DReading = DReading;
    }
}

