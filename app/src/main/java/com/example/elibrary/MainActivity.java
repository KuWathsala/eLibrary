package com.example.elibrary;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.elibrary.models.User;
import com.example.elibrary.ui.Auth.ActivityGoogleAuth;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    Fragment selectedFragment = new HomeFragment();
    SingleApplBarLayout singleApplBarLayout = SingleApplBarLayout.getInstance();
    float heightDp;

    private static final int PERMISSION_CODE =1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //set consants
        Constants constants = Constants.getInstance();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        constants.screenWidthInPx = metrics.widthPixels;
        constants.screenHeightInPx = metrics.heightPixels;
        constants.screenWidthInDp = (int) (metrics.widthPixels/metrics.density);
        constants.screenHeightInDp = (int) (metrics.heightPixels/metrics.density);
        constants.density = metrics.density;

        //configure
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            singleApplBarLayout.getAppBarLayout().getLayoutParams().height = (int)heightDp;
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)+
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)+
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)+
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(MainActivity.this, "already have permission", Toast.LENGTH_LONG).show();
            callActivity();
        }else{
            requestPermission();
        }
    }

    private void requestPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) ||
                ActivityCompat.shouldShowRequestPermissionRationale(
                        MainActivity.this,
                        Manifest.permission.CAMERA) ||
                ActivityCompat.shouldShowRequestPermissionRationale(
                        MainActivity.this,
                        Manifest.permission.READ_PHONE_STATE) ||
                ActivityCompat.shouldShowRequestPermissionRationale(
                        MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE}, PERMISSION_CODE);

        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE}, PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_CODE){
            if(requestCode== PERMISSION_CODE){
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "permission granted", Toast.LENGTH_SHORT).show();
                    callActivity();
                }else {
                    Toast.makeText(MainActivity.this, "permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void callActivity(){
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            startActivity(new Intent(MainActivity.this, ActivityGoogleAuth.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }else {

            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            User user = User.getInstance();
            user.name=firebaseUser.getDisplayName();
            user.email=firebaseUser.getEmail();
            user.id=firebaseUser.getUid();
            user.profileImage=firebaseUser.getPhotoUrl();
            configActivity();
            //startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }
}