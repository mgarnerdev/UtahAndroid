package com.utahandroid.april.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by mgarner on 4/12/2016.
 * Only checks for an internet connection right now.
 */
public class ConnectionUtils {

    /**
     * Returns a boolean indicating whether the user is connected to a network. Not a fool-proof solution as the user may be connected to a network without internet.
     * @param context I'll be needing that.
     * @return true if the user is connected to a network.
     */
    public static boolean hasInternet(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }
}
