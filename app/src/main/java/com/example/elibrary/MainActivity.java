package com.example.elibrary;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.elibrary.models.User;
import com.example.elibrary.ui.ActivityTemplate;
import com.example.elibrary.ui.auth.ActivityGoogleAuth;
import com.example.elibrary.ui.home.ActivityHomeLibrary;
import com.example.elibrary.ui.home.HomeFragment;
import com.example.elibrary.ui.notifications.NotificationsFragment;
import com.example.elibrary.ui.profile.ActivityAddBook;
import com.example.elibrary.ui.profile.ProfileFragment;
import com.example.elibrary.ui.request.RequestFragment;
import com.example.elibrary.ui.single.Constants;
import com.example.elibrary.ui.single.SingleApplBarLayout;
import com.example.elibrary.ui.single.SingleCollapsingToolbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE =1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        //configure
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            Log.d("Auth", "user auth token "+firebaseUser.toString());
            User user = User.getInstance();
            user.name=firebaseUser.getDisplayName();
            user.email=firebaseUser.getEmail();
            user.id=firebaseUser.getUid();
            user.profileImage=firebaseUser.getPhotoUrl();

            firebaseUser.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                String idToken = task.getResult().getToken();
                                // Send token to your backend via HTTPS
                                Log.d("Auth", "user auth token "+idToken);
                                //configActivity();
                                startActivity(new Intent(MainActivity.this, ActivityTemplate.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));;
                            } else {
                                // Handle error -> task.getException();
                                startActivity(new Intent(MainActivity.this, ActivityGoogleAuth.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            }
                        }
                    });


            //startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }
}