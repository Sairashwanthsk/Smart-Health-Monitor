package com.example.smarthealthmonitor;

import androidx.fragment.app.Fragment;

public class pulserecords extends Fragment {
    private String Month, Date;
    private long PReading;

    private pulserecords(){}
    private pulserecords(long PReading, String Month, String Date){

        this.PReading = PReading;
        this.Month = Month;
        this.Date = Date;
    }

    public long getPReading() {
        return PReading;
    }

    public void setPReading(long PReading) {
        this.PReading = PReading;
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

    public String getPStatus() {
        String pstatus = new String();
        if(PReading > 100){
            pstatus = "High Pulse";
        } else if(PReading < 60){
            pstatus = "Low Pulse";
        } else {
            pstatus = "Normal";
        }
        return pstatus;
    }

    public void setPStatus(String pstatus) {
        //
    }

}
