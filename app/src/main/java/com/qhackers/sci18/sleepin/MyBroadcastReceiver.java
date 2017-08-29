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
        Toast.makeText(context, "Alarm received", Toast.LENGTH_SHORT).show();
        Log.d("received", "Alarm received");

        String s = intent.getStringExtra("alarm");
//        ByteArrayInputStream bai = new ByteArrayInputStream(intent.getByteArrayExtra("alarm"));
//        Alarm alarm = null;
//        try(ObjectInputStream in = new ObjectInputStream(bai)) {
//            alarm = (Alarm) in.readObject();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

//        Toast.makeText(context, "Ringtone is: " + alarm.getRingtone(), Toast.LENGTH_SHORT).show();
        Intent myIntent = new Intent();
        Toast.makeText(context, "Alarm is 1: " + s, Toast.LENGTH_SHORT).show();
        myIntent.setClassName("com.qhackers.sci18.sleepin", "com.qhackers.sci18.sleepin.AlarmReceivedActivity");
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.putExtra("alarm", s);
//        Intent myIntent = new Intent(context.getApplicationContext(), AlarmReceivedActivity.class);
//        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        myIntent.putExtra("myKey", "myValue");
        context.startActivity(myIntent);
    }
}
