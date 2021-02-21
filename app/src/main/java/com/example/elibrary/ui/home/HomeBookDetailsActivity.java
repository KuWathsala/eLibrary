package com.example.elibrary.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.elibrary.Controllers.CategoriesController;
import com.example.elibrary.R;
import com.example.elibrary.models.Category;

public class HomeBookDetailsActivity extends AppCompatActivity {

    CategoriesController categoriesController = new CategoriesController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_book_details);

        Category category = new Category();
        category.id = "1";
        category.name = "short story";

        //categoriesController.addNewCategory(category);
        //categoriesController.testTrans();
    }
}