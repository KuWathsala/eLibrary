package com.example.elibrary.ui.single;

import android.net.Uri;
import android.widget.ImageView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SingleCollapsingToolbar {

    private static SingleCollapsingToolbar collapsingToolbar;
    private CollapsingToolbarLayout collToolbar;
    private ImageView toolbarImageView;
    private FloatingActionButton mainBottomActionButton;

    private SingleCollapsingToolbar(){}

    public static SingleCollapsingToolbar getInstance() {
        if  (collapsingToolbar == null) {
            collapsingToolbar = new SingleCollapsingToolbar();
        }
        return collapsingToolbar;
    }

    public CollapsingToolbarLayout getCollapsingToolbarLayout() {
        return collToolbar;
    }

    public void setCollapsingToolbarLayout(CollapsingToolbarLayout collToolbar) {
        this.collToolbar = collToolbar;
    }

    public void setToolbarImageView(ImageView toolbarImageView) {
        this.toolbarImageView = toolbarImageView;
    }

    public ImageView getToolbarImageView() {
        return this.toolbarImageView;
    }

    public FloatingActionButton getMainBottomActionButton() {
        return mainBottomActionButton;
    }

    public void setMainBottomActionButton(FloatingActionButton mainBottomActionButton) {
        this.mainBottomActionButton = mainBottomActionButton;
    }
}
