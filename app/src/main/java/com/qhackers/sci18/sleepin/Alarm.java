package com.qhackers.sci18.sleepin;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Calendar;

/**
 * Class to hold information about an alarm.
 */
public class Alarm implements Parcelable {

    private int hour;
    private int minute;
    private boolean isSet;      // Toggle the alarm on or off
    private boolean vibrate;    // Vibrate phone when the alarm goes off
    private String alarmName;   // Name of the alarm (optional)
    private String id;          // Key of the alarm to be stored in SharedPreferences
    private String ringtone;

    public Alarm(int hour, int minute, boolean isSet, boolean vibrate, String alarmName, String id) {
        this.hour = hour;
        this.minute = minute;
        this.isSet = isSet;
        this.vibrate = vibrate;
        this.alarmName = alarmName;
        this.id = id;
    }

    protected Alarm(Parcel in) {
        hour = in.readInt();
        minute = in.readInt();
        isSet = in.readByte() != 0;
        vibrate = in.readByte() != 0;
        alarmName = in.readString();
        id = in.readString();
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

    public boolean getIsSet() {
        return isSet;
    }

    public void setIsSet(boolean set) {
        isSet = set;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(hour);
        parcel.writeInt(minute);
        parcel.writeByte((byte) (isSet ? 1 : 0));
        parcel.writeByte((byte) (vibrate ? 1 : 0));
        parcel.writeString(alarmName);
        parcel.writeString(id);
    }

    public String getRingtone() {
        return ringtone;
    }

    public void setRingtone(String ringtoneUri) {
        this.ringtone = ringtoneUri;
    }

    /**
     * Schedules a PendingIntent for the alarm.
     * @param context
     */
    public void scheduleAlarm(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MyBroadcastReceiver.class);
//        ByteArrayOutputStream bao = new ByteArrayOutputStream();
//        try(ObjectOutputStream out = new ObjectOutputStream(bao)) {
//            out.writeObject(this);
//            out.flush();
//            byte[] data = bao.toByteArray();
//            Toast.makeText(context, "byte array is: " + data.toString(), Toast.LENGTH_SHORT).show();
//            intent.putExtra("alarm", data);
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(context, "Failed to turn object to byte array", Toast.LENGTH_SHORT).show();
//        }
        Gson g = new Gson();
        String s = g.toJson(this);
        intent.putExtra("alarm", s);
        String id = this.getId().replaceAll("[^0-9]+", "");
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, Integer.parseInt(id), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, this.getHour());
        calendar.set(Calendar.MINUTE, this.getMinute());

        am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
    }

    /**
     * Cancels the PendingIntent for the alarm.
     * @param context
     */
    public void cancelAlarm(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MyBroadcastReceiver.class);
        intent.putExtra("alarm", this);
        String id = this.getId().replaceAll("[^0-9]+", "");
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, Integer.parseInt(id), intent, 0);
        alarmIntent.cancel();
        am.cancel(alarmIntent);
    }
}
