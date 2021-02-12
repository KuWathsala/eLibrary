package com.example.elibrary.ui.Auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.elibrary.MainActivity;
import com.example.elibrary.R;
import com.example.elibrary.models.User;
import com.example.elibrary.ui.home.ActivityHomeLibrary;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class ActivityGoogleAuth extends AppCompatActivity {

    private SignInButton btnSignIn;
    private Button btnSignOut;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private int RC_SIGN_IN=1;

    private String name;
    private String familyName;
    private String email;
    private String id;
    private Uri profileImage;
    ProgressBar progressCircular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_auth);

        btnSignIn = findViewById(R.id.button_signin_google);
        btnSignOut = findViewById(R.id.button_signout_google);
        firebaseAuth = FirebaseAuth.getInstance();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
                progressCircular = (ProgressBar)findViewById(R.id.progressCircular);
                if(progressCircular.getVisibility() == View.INVISIBLE){
                    progressCircular.setVisibility(View.VISIBLE);
                }
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                btnSignOut.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

    private void signIn(){
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(getApplication(), "Sign in successed", Toast.LENGTH_LONG).show();
            firebaseGoogleAuth(acc);
        } catch (ApiException e){
            Toast.makeText(getApplication(), "Sign in failed", Toast.LENGTH_LONG).show();
            firebaseGoogleAuth(null);
        }
    }

    private void firebaseGoogleAuth(GoogleSignInAccount acc) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acc.getIdToken(), null);
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Toast.makeText(MainActivity.this, "successed", Toast.LENGTH_LONG).show();
                    FirebaseUser user =   firebaseAuth.getCurrentUser();
                    updateUI(user);
                }else{
                    //Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_LONG).show();
                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser firebaseUser){
        btnSignOut.setVisibility(View.VISIBLE);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account!=null){
            String personName=account.getDisplayName();
            name=account.getGivenName();
            familyName=account.getFamilyName();
            email=account.getEmail();
            id=account.getId();
            profileImage=account.getPhotoUrl();

            final Intent intent = new Intent(this, MainActivity.class);
            Bundle bundle = new Bundle();

            User user = User.getInstance();
            user.name=name;
            user.familyName=familyName;
            user.email=email;
            user.id=id;
            user.profileImage=profileImage;

            startActivity(intent);

            btnSignIn.setVisibility(View.INVISIBLE);
            Toast.makeText(this, personName, Toast.LENGTH_LONG).show();
        }
    }
}