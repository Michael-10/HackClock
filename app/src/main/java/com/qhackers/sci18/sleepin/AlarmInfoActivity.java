package com.qhackers.sci18.sleepin;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;


public class AlarmInfoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    /**
     * Writes the alarm info to the database.
     * @param v - The view that the event was triggered from (OK button)
     */
    @TargetApi(23)
    public void saveAlarm(View v) {
        // get time picker time
        final TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
        int currentApiVersion = Build.VERSION.SDK_INT;
        int hour;
        int minute;
        // get hour from time picker
        if (currentApiVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            hour = tp.getHour(); // for api 23+
        } else {
            hour = tp.getCurrentHour().intValue();
        }
        // get minute from time picker
        if (currentApiVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            minute = tp.getMinute();
        } else {
            minute = tp.getCurrentMinute().intValue();
        }

        // get vibrateOnOff
        final CheckBox cb = (CheckBox) findViewById(R.id.vibrateOnOff);
        boolean isVibrate = cb.isChecked();

        // get alarm name
        final EditText et = (EditText) findViewById(R.id.alarmName);
        String alarmName = et.getText().toString();

        Log.i("TEST", "Hour is: " + hour + " minute is: " + minute + " isVibrate is: " + isVibrate + " alarm name is: " + alarmName);

        // write to alarm info database
        String alarmID = "placeholder"; // TODO: get id from intent info
        Gson g = new Gson();
        String s = g.toJson(new Alarm(hour, minute, true, isVibrate, alarmName));
        SharedPreferences sPrefs = getSharedPreferences("Sleepin", MODE_PRIVATE);
        SharedPreferences.Editor pe = sPrefs.edit();
        pe.putString(alarmID, s);
        pe.apply();

        Set<String> test1 = sPrefs.getStringSet("alarms", new HashSet<String>());
        for (String string : test1) {
            Log.i("DB", s);
        }

    }

    /**
     * Cancels changes made to alarm and returns back to AlarmListActivity
     * @param v
     */
    public void cancelEditCreate(View v) {
        finish();
    }

}
