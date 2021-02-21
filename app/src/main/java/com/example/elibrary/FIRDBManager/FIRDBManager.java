package com.example.elibrary.FIRDBManager;

import com.example.elibrary.ui.single.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FIRDBManager {

    private static FIRDBManager dbManager;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public static final String USER = "User";
    public static final String LIBRARY = "Library";
    public static final String CATEGORY = "Category";

    private FIRDBManager(){}

    public static FIRDBManager getInstance() {
        if  (dbManager == null) {
            dbManager = new FIRDBManager();
        }
        return dbManager;
    }

    public DatabaseReference getRootRef() {
        return mDatabase;
    }

    public DatabaseReference getLibraryRef() {
        return mDatabase.child(LIBRARY);
    }

    public DatabaseReference getCategoryRef() {
        return mDatabase.child(CATEGORY);
    }

}
