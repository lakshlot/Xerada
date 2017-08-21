package io.github.xerada.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.github.xerada.R;

public class Email_signup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText emailinput;
    EditText passwordinput;
    private String email;
    private String password;
    Button signupbutton;
    private final String TAG= "emailsignup";
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_signup);
        mAuth=FirebaseAuth.getInstance();

        emailinput=(EditText)findViewById(R.id.editText);
        passwordinput=(EditText)findViewById(R.id.editText2);

        signupbutton=(Button)findViewById(R.id.button4) ;
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=emailinput.getText().toString();
                password=passwordinput.getText().toString();
                final ProgressDialog mProgress=new ProgressDialog(Email_signup.this);
                mProgress.setTitle("Registering You ");
                mProgress.setMessage("Please wait while we Create an account for you. ");
                mProgress.show();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Email_signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mProgress.dismiss();
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    user = mAuth.getCurrentUser();
                                    updateUI();
                                } else {
                                    mProgress.dismiss();
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(Email_signup.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }


                            }

                        });





            }
        });


    }
    private void updateUI(){
        Intent intent= new Intent(this,Users_info.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }
}
