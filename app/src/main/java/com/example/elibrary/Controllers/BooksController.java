package com.example.elibrary.Controllers;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.elibrary.FIRDBManager.FIRDBManager;
import com.example.elibrary.FIRDBManager.FIRStorageManager;
import com.example.elibrary.models.Book;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class BooksController {

    FIRDBManager dbManager = FIRDBManager.getInstance();
    FIRStorageManager storageManager = FIRStorageManager.getInstance();

    public void addNewBook(Book book) {
        dbManager.getCategoryRef().push().setValue(book)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //
                    }
                });
    }

    public void loadBookImgtoStorage (Uri imgUri) {

        //upload file to firebase
        StorageReference riversRef = storageManager.getBooksRef().child(imgUri.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(imgUri);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String  filePath = taskSnapshot.getMetadata().getReference().getPath();
                addNewBook(Book book);
            }
        });

    }

}
