package com.example.tsonio.launchcalendar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity{

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //Fragment manager
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mTextMessage.setAlpha(0);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransaction.replace(R.id.content,new UpcomingFragment()).commit();
                    mTextMessage.setText(R.string.title_home);
                    getSupportActionBar().setSubtitle("Upcoming Launches");
                    return true;
                case R.id.navigation_dashboard:
                    fragmentTransaction.replace(R.id.content,new PastFragment()).commit();
                    mTextMessage.setText(R.string.title_dashboard);
                    getSupportActionBar().setSubtitle("Past Launches");
                    return true;
                case R.id.navigation_notifications:
                    fragmentTransaction.replace(R.id.content,new SettingsFragment()).commit();
                    mTextMessage.setText(R.string.title_notifications);
                    getSupportActionBar().setSubtitle("Settings");
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add a subtitle to the app bar
        getSupportActionBar().setSubtitle("Upcoming Launches");



        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Load the home fragment on app startup
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content,new UpcomingFragment()).commit();


    }



}

