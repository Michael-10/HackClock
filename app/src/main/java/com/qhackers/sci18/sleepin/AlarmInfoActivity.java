package com.qhackers.sci18.sleepin;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import com.google.gson.Gson;
import java.util.Map;

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
    public void saveAlarm(View v) {
        final TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
        int currentApiVersion = Build.VERSION.SDK_INT;
        int hour = getTimePickerHour(tp, currentApiVersion);
        int minute = getTimePickerMinute(tp, currentApiVersion);
        boolean isVibrate = isVibrate();
        String alarmName = getAlarmName();
        Alarm a = new Alarm(hour, minute, true, isVibrate, alarmName);
        writeAlarmToSharedPrefs(a);

        // debug purposes
        Log.i("TEST", "Hour is: " + hour + " minute is: " + minute + " isVibrate is: " + isVibrate + " alarm name is: " + alarmName);
    }

    @TargetApi(23)
    private int getTimePickerHour(TimePicker tp, int currentApiVersion) {
        int hour;
        if (currentApiVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            hour = tp.getHour(); // for api 23+
        } else {
            hour = tp.getCurrentHour();
        }
        return hour;
    }

    @TargetApi(23)
    private int getTimePickerMinute(TimePicker tp, int currentApiVersion) {
        int minute;
        if (currentApiVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            minute = tp.getMinute();
        } else {
            minute = tp.getCurrentMinute();
        }
        return minute;
    }

    private boolean isVibrate() {
        final CheckBox cb = (CheckBox) findViewById(R.id.vibrateOnOff);
        return cb.isChecked();
    }

    @NonNull
    private String getAlarmName() {
        final EditText et = (EditText) findViewById(R.id.alarmName);
        return et.getText().toString();
    }

    private void writeAlarmToSharedPrefs(Alarm a) {
        String alarmID = getIntentExtra("alarmID");
        String s = getAlarmObjectAsJson(a);
        SharedPreferences sPrefs = getSharedPreferences("Sleepin", MODE_PRIVATE);
        SharedPreferences.Editor pe = sPrefs.edit();
        pe.putString(alarmID, s);
        pe.apply();

        // debug purposes
        for (Map.Entry<String, ?> e : sPrefs.getAll().entrySet()) {
            Log.i("DB", e.getKey() + " : " + e.getValue());
        }
    }

    private String getIntentExtra(String key) {
        Intent intent = getIntent();
        return intent.getStringExtra(key);
    }

    private String getAlarmObjectAsJson(Alarm a) {
        Gson g = new Gson();
        return g.toJson(a);
    }

    /**
     * Cancels changes made to alarm and returns back to AlarmListActivity
     * @param v The view that the event was triggered from (Cancel button)
     */
    public void cancelEditCreate(View v) {
        finish();
    }

}
