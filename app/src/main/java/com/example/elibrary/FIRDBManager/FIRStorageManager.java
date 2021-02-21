package com.example.elibrary.FIRDBManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FIRStorageManager {

    private static FIRStorageManager storageManager;

    private StorageReference  mStorage = FirebaseStorage.getInstance().getReference();

    public static final String BOOKS = "books";

    private FIRStorageManager(){}

    public static FIRStorageManager getInstance() {
        if  (storageManager == null) {
            storageManager = new FIRStorageManager();
        }
        return storageManager;
    }

    public StorageReference getRootRef() {
        return mStorage;
    }

    public StorageReference getBooksRef() {
        return getRootRef().child(BOOKS);
    }


}
