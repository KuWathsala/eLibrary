package com.example.elibrary.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.elibrary.Controllers.BooksController;
import com.example.elibrary.FIRDBManager.FIRDBManager;
import com.example.elibrary.FIRDBManager.FIRStorageManager;
import com.example.elibrary.R;
import com.example.elibrary.models.Book;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class ActivityAddBook extends AppCompatActivity {

    private TextInputEditText mTextInputName;
    private TextInputEditText mTextInputAuthor;
    private TextView mCategory;
    private TextView mLanguage;
    private Button mBtnAddNewBook;
    private FloatingActionButton mActionBtnAddBookImg;
    private ImageView mBookImgView;

    String categories[] ={"NOVEL","SHORT STORIES","EDUCATION","MAGAZINE", "FAIRY TAILS", "SCIENTIFIC FICTION"};
    String languages[] ={"SINHALA","ENGLISH","TAMIL","FRENCH", "HINDI", "KOREAN", "SINHALA","ENGLISH","TAMIL","FRENCH", "HINDI", "KOREAN", "SINHALA","ENGLISH","TAMIL","FRENCH", "HINDI", "KOREAN"};

    private static final int PERMISSION_CODE =1;
    private static final int PICK_IMAGE=1;

    String filePath;
    Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        Toolbar mToolbar = findViewById(R.id.toolbar_add_new_book);
        mToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        mTextInputName = findViewById(R.id.input_text_add_book_name);
        mTextInputAuthor = findViewById(R.id.input_text_add_book_auther);
        mCategory = findViewById(R.id.text_add_book_category);
        mLanguage = findViewById(R.id.text_add_book_language);
        mBtnAddNewBook = findViewById(R.id.button_add_new_book);
        mActionBtnAddBookImg = findViewById(R.id.add_new_book_img_btn);
        mBookImgView = findViewById(R.id.add_book_image_view);

        //back
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(categories, mCategory);
            }
        });

        mLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(languages, mLanguage);
            }
        });

        mActionBtnAddBookImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //request permission to access external storage
                requestPermission();
            }
        });

        mBtnAddNewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!String.valueOf(mTextInputName.getText()).isEmpty() &&
//                        !String.valueOf(mTextInputAuthor.getText()).isEmpty() &&
//                        !String.valueOf(mCategory.getText()).equals("CATEGORY") &&
//                        !String.valueOf(mLanguage.getText()).equals("LANGUAGE")
//                ) {
//                    //Log.d("add book", "add book buttom"+ mTextInputAuthor.getText());
//                }

                BooksController booksController = new BooksController();

//                Book book = new Book(){
//                    id
//                    public String name;
//                    public String author;
//                    public String language;
//                    public boolean isAvailable;
//                    public boolean isDeleted;
//                    public String categoryId;
//                    public String userId;
//                    public int likes;
//                };

                book.name = mTextInputName.toString();
                book.author = mTextInputAuthor.toString();
                book.language = mLanguage.

                //booksController.loadBookImgtoStorage(imgUri);

            }
        });

    }

    private void showDialog (String[] list, final TextView textView) {

        final Dialog dialog = new Dialog(ActivityAddBook.this);
        dialog.setContentView(R.layout.popup_list);

        ListView listView= (ListView) dialog.findViewById(R.id.popup_list_view);
        ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.popup_text, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                textView.setText(item);
                dialog.cancel();
            }
        });

        dialog.setCancelable(true);
        dialog.show();
    }

    private void requestPermission(){
        if(ContextCompat.checkSelfPermission
                (ActivityAddBook.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
        ){
            accessTheGallery();
        } else {
            ActivityCompat.requestPermissions(
                    ActivityAddBook.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_CODE
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //get the image's file location
        imgUri = data.getData();
        filePath = getRealPathFromUri(data.getData(), ActivityAddBook.this);

        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK){
            try {
                //set picked image to the mProfile
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                mBookImgView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getRealPathFromUri(Uri imageUri, Activity activity){
        Cursor cursor = activity.getContentResolver().query(imageUri, null, null, null, null);

        if(cursor==null) {
            return imageUri.getPath();
        }else{
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private void accessTheGallery(){
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        i.setType("image/*");
        startActivityForResult(i, PICK_IMAGE);
    }

}