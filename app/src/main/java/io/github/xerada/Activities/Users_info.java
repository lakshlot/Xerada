package io.github.xerada.Activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.github.xerada.R;

public class Users_info extends AppCompatActivity {
    private EditText namefield;
    private FirebaseUser mCurrentUser;

    private Button mNext,selectgenderBtn;

    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mbasicprofileinfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic__profile_info);
        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseUsers= FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());
        mbasicprofileinfo=mDatabaseUsers.child("basic_profile_info");


        namefield=(EditText)findViewById(R.id.editText4);
        selectgenderBtn=(Button)findViewById(R.id.button13);
        selectgenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu= new PopupMenu(Users_info.this,selectgenderBtn);
                popupMenu.getMenuInflater().inflate(R.menu.gender_select_menu,popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==R.id.g_male){
                            selectgenderBtn.setText("Male");
                        }
                        if (item.getItemId()==R.id.g_female){
                            selectgenderBtn.setText("Female");
                        }
                        if (item.getItemId()==R.id.g_other){
                            selectgenderBtn.setText("Other");
                        }
                        return true;
                    }
                });
            }
        });



        mNext= (Button)findViewById(R.id.button7);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(namefield.getText().toString())) {
                    String gender= selectgenderBtn.getText().toString();
                    mbasicprofileinfo.child("gender").setValue(gender);
                    mbasicprofileinfo.child("name").setValue(namefield.getText().toString());
                    startActivity(new Intent(Users_info.this,Create_Username.class));
                }else{
                    Snackbar.make(v,"Please Enter your name",Snackbar.LENGTH_SHORT).show();
                }
            }
        });


    }
}
