package com.example.elibrary.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.elibrary.R;
import com.example.elibrary.ui.home.HomeFragment;
import com.example.elibrary.ui.notifications.NotificationsFragment;
import com.example.elibrary.ui.profile.ProfileFragment;
import com.example.elibrary.ui.request.RequestFragment;
import com.example.elibrary.ui.single.Constants;
import com.example.elibrary.ui.single.SingleApplBarLayout;
import com.example.elibrary.ui.single.SingleCollapsingToolbar;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityTemplate extends AppCompatActivity {

    Fragment selectedFragment = new HomeFragment();
    SingleApplBarLayout singleApplBarLayout = SingleApplBarLayout.getInstance();
    float heightDp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);

        //set consants
        Constants constants = Constants.getInstance();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        constants.screenWidthInPx = metrics.widthPixels;
        constants.screenHeightInPx = metrics.heightPixels;
        constants.screenWidthInDp = (int) (metrics.widthPixels/metrics.density);
        constants.screenHeightInDp = (int) (metrics.heightPixels/metrics.density);
        constants.density = metrics.density;

        configActivity();

    }

    public void configActivity(){
        //app bar layout
        singleApplBarLayout.setAppBar((AppBarLayout)findViewById(R.id.appbar_layout));
        heightDp = getResources().getDimensionPixelSize(R.dimen.appbar_height_phone_default);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SingleCollapsingToolbar singleCollapsingToolbar = SingleCollapsingToolbar.getInstance();
        singleCollapsingToolbar.setCollapsingToolbarLayout((CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout));
        singleCollapsingToolbar.setToolbarImageView((ImageView) findViewById(R.id.collapsing_toolbar_image));
        singleCollapsingToolbar.setMainBottomActionButton((FloatingActionButton) findViewById(R.id.main_bottom_action_button));

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
        singleApplBarLayout.getAppBarLayout().getLayoutParams().height = (int)heightDp;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener
            navListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            SingleCollapsingToolbar.getInstance().getMainBottomActionButton().setVisibility(
                    item.getItemId() == R.id.navigation_profile ? View.VISIBLE : View.GONE);

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.navigation_notifications:
                    selectedFragment = new NotificationsFragment();
                    break;
                case R.id.navigation_profile:
                    selectedFragment = new ProfileFragment();
                    break;
                case R.id.navigation_library:
                    selectedFragment = new RequestFragment();//LibraryFragment();
                    break;
                default:
                    selectedFragment = new HomeFragment();
                    break;
            }
            // It will help to replace the one fragment to other.
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
            singleApplBarLayout.getAppBarLayout().getLayoutParams().height = (int) heightDp;
            return true;
        }
    };

}