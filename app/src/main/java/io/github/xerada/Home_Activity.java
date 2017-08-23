package io.github.xerada;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.xerada.Activities.Login_Activity;
import io.github.xerada.SectionPageAdapters.SectionsPageAdapter;

public class Home_Activity extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth firebaseAuth;
    private String TAG= "ActivityLog";
    private  FirebaseAuth mAuth;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private SectionsPageAdapter mSectionpageadapter;
    private TabLayout mTablayout;
    private Button signoutbtn;
    private DatabaseReference mUserDatabase;
    private CircleImageView main_dp;

    public static final int RC_SIGN_IN = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        toolbar=(Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        main_dp = (CircleImageView)findViewById(R.id.main_user_dp);

        mUserDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mUserDatabase.child("basic_profile_info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();
                Picasso.with(getApplicationContext()).load(thumb_image).centerCrop().fit().into(main_dp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){

        }else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setProviders(
                                    Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                            .build(),
                    RC_SIGN_IN);
        }


        viewPager=(ViewPager)findViewById(R.id.tab_pager);
        mSectionpageadapter= new SectionsPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mSectionpageadapter);

        mTablayout= (TabLayout) findViewById(R.id.main_tabs);
        mTablayout.setupWithViewPager(viewPager);
        mTablayout.getTabAt(0).setIcon(R.drawable.ic_house_outline);
        mTablayout.getTabAt(1).setIcon(R.drawable.ic_notifications_button);
        mTablayout.getTabAt(2).setIcon(R.drawable.friendsicon);
//
        mTablayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#00719c"), PorterDuff.Mode.SRC_IN);
//
        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#00719c"),PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                tab.getIcon().clearColorFilter();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sign-In Canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
