package com.qhackers.sci18.sleepin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String alarm = intent.getStringExtra("alarm");

        Intent myIntent = new Intent();
        Toast.makeText(context, "Alarm is 1: " + alarm, Toast.LENGTH_SHORT).show();
        myIntent.setClassName("com.qhackers.sci18.sleepin", "com.qhackers.sci18.sleepin.AlarmReceivedActivity");
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.putExtra("alarm", alarm);

        context.startActivity(myIntent);
    }
}
