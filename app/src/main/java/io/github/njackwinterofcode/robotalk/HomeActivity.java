package io.github.njackwinterofcode.robotalk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    Button tts;
    Button stt;
    Button logOut;

    List<AuthUI.IdpConfig> providers;
    private static final int MyRequestCode =2;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tts = findViewById(R.id.button1);
        stt = findViewById(R.id.button2);
        logOut = findViewById(R.id.logOut);
        tts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, textToSpeech.class));
            }
        });
        stt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,speechToText.class));
            }
        });


        providers = Arrays.asList(

                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.AnonymousBuilder().build()
        );
        showSignInOptions();

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(HomeActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                showSignInOptions();
                                logOut.setEnabled(false);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HomeActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
               // FirebaseAuth.getInstance().signOut();
               // startActivity(new Intent(HomeActivity.this,LoginActivity.class));
               // Toast.makeText(HomeActivity.this, "Logged Out.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showSignInOptions() {

        startActivityForResult(

                AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.MyTheme)
                .build(),MyRequestCode

        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MyRequestCode){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(requestCode == RESULT_OK){

                FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(HomeActivity.this, "Hi " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                logOut.setEnabled(true);
            }
            else{
               // Toast.makeText(HomeActivity.this, ""+response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }
}
