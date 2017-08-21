package io.github.xerada.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.github.xerada.R;

public class Create_Username extends AppCompatActivity {
    private FirebaseUser mUser;
    private String emailid;
    private String username;
    private EditText usernamefield;
    private Button mNext;
    private DatabaseReference basicprofileinfo;
    FirebaseUser mCurrentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__username);
        mUser= FirebaseAuth.getInstance().getCurrentUser();
        emailid=mUser.getEmail();
        String[] emailsplit= emailid.split("@");
        username= emailsplit[0];

        usernamefield=(EditText)findViewById(R.id.editText3);
        usernamefield.setText(username);

        mCurrentUser=FirebaseAuth.getInstance().getCurrentUser();
        basicprofileinfo= FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid()).child("basic_profile_info");


        mNext= (Button) findViewById(R.id.button6);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basicprofileinfo.child("username").setValue(username);


                //startActivity(new Intent(Create_Username.this,Basic_user_profile_photo.class));

            }
        });



    }
}
