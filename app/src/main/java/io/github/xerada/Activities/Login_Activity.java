package io.github.xerada.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import io.github.xerada.R;

public class Login_Activity extends AppCompatActivity {
    private LoginButton loginButton;
    private CallbackManager mCallbackManager;
    private String TAG= "fbloginstatus";
    private FirebaseAuth mAuth;
    private TextView signup_button;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mUserTokenRef;
    private EditText emailfield;
    private EditText passwordfield;
    private Button signin_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);



        mAuth=FirebaseAuth.getInstance();

        emailfield=(EditText)findViewById(R.id.emailinput);
        passwordfield=(EditText)findViewById(R.id.passinput);
        signin_button=(Button) findViewById(R.id.loginbutton);

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });





        signup_button=(TextView)  findViewById(R.id.regtextview);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Login_Activity.this,Sign_up_Form.class);
                startActivity(intent);
            }
        });
    }

    private void startSignIn(){
        final ProgressDialog mProgress=new ProgressDialog(Login_Activity.this);
        mProgress.setTitle("Signing In");
        mProgress.setMessage("Please wait while we Sign in you");
        mProgress.show();
        String email= emailfield.getText().toString();
        String password=passwordfield.getText().toString();
        if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
            Toast.makeText(Login_Activity.this,"Fields are Empty",Toast.LENGTH_SHORT).show();

        }
        else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    mUserTokenRef= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("device_token");


                    mUserTokenRef.setValue(FirebaseInstanceId.getInstance().getToken()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });
                    if (!task.isSuccessful()){
                        mProgress.hide();
                        Toast.makeText(Login_Activity.this,"Sign In not successful",Toast.LENGTH_SHORT).show();
                    }
                    mProgress.dismiss();
                }
            });

        }




    }

}
