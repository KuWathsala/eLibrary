package com.example.elibrary.models;

import java.util.LinkedList;
import java.util.List;

public class Library {

    private static Library library;
    public List booksList = new LinkedList<Book>();

    private Library(){}

    public static Library getInstance() {
        if  (library == null) {
            library = new Library();
        }
        return library;
    }

}
