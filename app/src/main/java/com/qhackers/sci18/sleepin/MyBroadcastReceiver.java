package com.qhackers.sci18.sleepin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent();
        Alarm alarm = intent.getParcelableExtra("alarm");
        i.putExtra("alarm", alarm);
        i.setClassName("com.qhackers.sci18.sleepin", "com.qhackers.sci18.sleepin.AlarmReceivedActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
