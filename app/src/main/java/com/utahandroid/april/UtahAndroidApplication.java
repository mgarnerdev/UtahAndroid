package com.utahandroid.april;

import android.app.Application;

/**
 * Created by mgarner on 4/12/2016.
 * The application class. Used primarily for storing the device's pixel density right now.
 */
public class UtahAndroidApplication extends Application {
    public static float mPixelDensity;

    @Override
    public void onCreate() {
        super.onCreate();
        mPixelDensity = getApplicationContext().getResources().getDisplayMetrics().density;
    }
}
