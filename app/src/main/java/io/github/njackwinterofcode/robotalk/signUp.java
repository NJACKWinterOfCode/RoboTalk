package io.github.njackwinterofcode.robotalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signUp extends AppCompatActivity {

    Button button;
    TextView em,pwd,cpwd,nme,adhr;
    FirebaseAuth firebaseAuth;
    public void signUp(View view){
        final String email = em.getText().toString();
        String password = pwd.getText().toString();
        String cpassword = cpwd.getText().toString();

        final String userName = nme.getText().toString();
        if(email.isEmpty()){
            em.setError("Please enter your E-mail");
            em.requestFocus();

        }
        else if(userName.isEmpty()){
            nme.setError("Please enter your Name");
            nme.requestFocus();
        }
        else if(password.isEmpty()){
            pwd.setError("Please enter your Password");
            pwd.requestFocus();
        }
        else if(cpassword.isEmpty()){
            cpwd.setError("Please confirm your Password");
            cpwd.requestFocus();
        }
        else if(email.isEmpty() && password.isEmpty() && userName.isEmpty() && cpassword.isEmpty()){

            Toast.makeText(signUp.this, "Please enter the required fields", Toast.LENGTH_SHORT).show();
        }
        else if(!(email.isEmpty() && password.isEmpty() && userName.isEmpty() && cpassword.isEmpty() )) {

            if (!(cpassword.equals(password))) {

                cpwd.setError("Password doesn't match.");
                cpwd.requestFocus();

            } else if(cpassword.equals(password)) {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(signUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(signUp.this, "Logged in", Toast.LENGTH_SHORT).show();
                            Intent signInn = new Intent(signUp.this, HomeActivity.class);
                            startActivity(signInn);
                            finish();

                        } else {
                            Toast.makeText(signUp.this, "Sign-up Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else {
                Toast.makeText(signUp.this, "Unexpected Error. Sign-up Failed.", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(signUp.this, "Sign-Up Failed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        em = findViewById(R.id.editText3);
        pwd = findViewById(R.id.editText4);
        button = findViewById(R.id.button);
        cpwd = findViewById(R.id.editText5);
        firebaseAuth = FirebaseAuth.getInstance();
        nme = findViewById(R.id.name);


    }
}
