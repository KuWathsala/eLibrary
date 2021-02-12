package com.example.elibrary.ui.single;

import com.google.android.material.appbar.AppBarLayout;

public class SingleApplBarLayout {

    private static SingleApplBarLayout appBar;
    private static AppBarLayout appBarLayout;
    private String barName;

    public static SingleApplBarLayout getInstance() {
        return appBar == null ? new SingleApplBarLayout() : appBar;
    }

    public void setAppBar (AppBarLayout appBarLayout){
        this.appBarLayout = appBarLayout;
    }

    public AppBarLayout getAppBarLayout () {
        return appBarLayout;
    }

    public String getBarName() {
        return barName;
    }

    public void setBarName(String barName) {
        this.barName = barName;
    }
}
