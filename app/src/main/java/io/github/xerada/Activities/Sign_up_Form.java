package io.github.xerada.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.github.xerada.R;

public class Sign_up_Form extends AppCompatActivity {
    Button Ph_no_button;
    Button Email_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__form);
        Ph_no_button=(Button)findViewById(R.id.button2);
        Email_button=(Button)findViewById(R.id.button3);

        Ph_no_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Sign_up_Form.this,Phone_No_signup.class);
                startActivity(intent);

            }
        });

        Email_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2= new Intent(Sign_up_Form.this,Email_signup.class);
                startActivity(intent2);
            }
        });
    }
}
