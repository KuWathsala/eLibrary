package com.example.elibrary.models;

import android.net.Uri;

public class User {

    private static User user;

    public String name;
    public String familyName;
    public String email;
    public String id;
    public Uri profileImage;

    private User(){}

    public static User getInstance() {
        if  (user == null) {
            user = new User();
        }
        return user;
    }

}
