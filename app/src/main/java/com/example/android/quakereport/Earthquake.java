package com.example.android.quakereport;

/**
 * Created by andre on 20/10/2017.
 */

public class Earthquake {

    private double mMagnetude;
    private String mLocation;
    private long mTimeInMilissecons;
    private String mUrl;

    public Earthquake(double magnetude, String location, long timeInMilisseconds, String url) {
        mMagnetude = magnetude;
        mLocation = location;
        mTimeInMilissecons = timeInMilisseconds;
        mUrl = url;
    }

    public double getMagnitude() {
        return mMagnetude;
    }

    public String getLocation() {
        return mLocation;
    }

    public long getTimeInMilisseconds() {
        return mTimeInMilissecons;
    }

    public String getUrl() {
        return mUrl;
    }
}
