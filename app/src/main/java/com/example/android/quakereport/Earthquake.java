package com.example.android.quakereport;

/**
 * Created by andre on 20/10/2017.
 */

public class Earthquake {

    private Double mMagnetude;
    private String mLocation;
    private String mDate;

    public Earthquake(Double magnetude, String location, String date) {
        mMagnetude = magnetude;
        mLocation = location;
        mDate = date;
    }

    public Double getMagnetude() {
        return mMagnetude;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getDate() {
        return mDate;
    }
}
