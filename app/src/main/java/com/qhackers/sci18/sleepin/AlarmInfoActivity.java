package com.qhackers.sci18.sleepin;

import android.annotation.TargetApi;
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
import android.widget.TextClock;
import android.widget.TimePicker;

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
     *
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
            hour = tp.getHour();
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
        Alarm a = new Alarm("placeholder", true);


    }

}
