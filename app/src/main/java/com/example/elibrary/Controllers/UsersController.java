package com.example.elibrary.Controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.elibrary.FIRDBManager.FIRDBManager;
import com.example.elibrary.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class UsersController {

    FIRDBManager dbManager = FIRDBManager.getInstance();

    public void  addUser (User user) {
        dbManager.getCategoryRef().push().setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("", "addUser onSuccess ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("", "addUser failed "+ e.toString());
                    }
                });
    }



}
