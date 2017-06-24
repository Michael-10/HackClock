package com.qhackers.sci18.sleepin;

/**
 * Class to hold information about an alarm.
 */
public class Alarm {

    private boolean isSet;
    private String alarmTime;

    public Alarm(String alarmTime, boolean isSet) {
        this.alarmTime = alarmTime;
        this.isSet = isSet;
    }


    public boolean isSet() {
        return isSet;
    }

    public void setSet(boolean set) {
        isSet = set;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }
}
