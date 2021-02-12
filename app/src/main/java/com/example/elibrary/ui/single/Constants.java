package com.example.elibrary.ui.single;

import android.util.Log;

public class Constants {

    private static Constants constants;
    public static int screenWidthInPx;
    public static int screenHeightInPx;
    public static int screenWidthInDp;
    public static int screenHeightInDp;
    public static float density;

    public static final int cardDefaultheightInDp = 320;
    public static final int defaultMarginInDp = 16;

    public static Constants getInstance() {
        if  (constants == null) {
            constants = new Constants();
        }
        return constants;
    }

}
