package com.qhackers.sci18.sleepin;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.sql.Time;

public class AlarmReceivedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_received);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView tvTime = (TextView) findViewById(R.id.tv_time);
        String lTime = "11:05";
        tvTime.setText(lTime);
    }

    public void dismissButtonClick(View view) {

    }

    public void snoozeButtonClick(View view) {

    }
}
