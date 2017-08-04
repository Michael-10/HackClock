package com.qhackers.sci18.sleepin;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

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
        ringtone = in.readString();
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
        parcel.writeString(ringtone);
    }

    public String getRingtone() {
        return ringtone;
    }

    public void setRingtone(String ringtoneUri) {
        this.ringtone = ringtoneUri;
    }

    /**
     * Schedules a PendingIntent for the alarm.
     * @param aContext Activity context
     */
    public void scheduleAlarm(Context aContext) {
        AlarmManager am = (AlarmManager) aContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(aContext, MyBroadcastReceiver.class);
        intent.putExtra("alarm", this);
        String id = this.getId().replaceAll("[^0-9]+", "");     // this.getId returns a string such as "alarm1". We only need the "1".
        PendingIntent alarmIntent = PendingIntent.getBroadcast(aContext, Integer.parseInt(id), intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, this.getHour());
        calendar.set(Calendar.MINUTE, this.getMinute());

        long calendarTime = calendar.getTimeInMillis();
        am.setExact(AlarmManager.RTC_WAKEUP, calendarTime, alarmIntent);
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

    @Override
    public String toString() {
        if (this.getRingtone() != null) {
            return "Hour: " + this.getHour() + "\nMinute: " + this.getMinute() + "\nRingtone: " + this.getRingtone();
        } else {
            return "Hour: " + this.getHour() + "\nMinute: " + this.getMinute();
        }
    }
}
