package com.qhackers.sci18.sleepin;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.Map;

public class AlarmInfoActivity extends AppCompatActivity {

    private Alarm alarmForEdit;
    private boolean permissionGranted;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getAction();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getAction() {
        String action = getIntentExtra("action");

        if (action.equals("edit")) {
            Intent intent = getIntent();
            alarmForEdit = intent.getParcelableExtra("alarmId");

            TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
            int alarmHour = alarmForEdit.getHour();
            tp.setHour(alarmHour);
            int alarmMin = alarmForEdit.getMinute();
            tp.setMinute(alarmMin);

            CheckBox cbVibrate = (CheckBox) findViewById(R.id.isVibrate);
            boolean vibrate = alarmForEdit.isVibrate();
            cbVibrate.setChecked(vibrate);

            EditText etAlarmName = (EditText) findViewById(R.id.alarmName);
            String alarmName = alarmForEdit.getAlarmName();
            etAlarmName.setText(alarmName, TextView.BufferType.EDITABLE);
        }
    }

    /**
     * Writes the alarm info to the database.
     *
     * @param v - The view that the event was triggered from (OK button)
     */
    public void saveAlarmChanges(View v) {
        final TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
        int currentApiVersion = Build.VERSION.SDK_INT;
        int hour = getTimePickerHour(tp, currentApiVersion);
        int minute = getTimePickerMinute(tp, currentApiVersion);
        boolean isVibrate = isVibrate();
        String alarmName = getAlarmName();
        String id = getAlarmID();
        Alarm a = new Alarm(hour, minute, true, isVibrate, alarmName, id);

        final TextView tv = (TextView) findViewById(R.id.selectRingtone);
        String ringtone = tv.getText().toString();
        a.setRingtone(ringtone);
        writeAlarmToSharedPrefs(a);

        // debug purposes
        Log.d("savedAlarm", "Hour is: " + hour + " minute is: " + minute + " isVibrate is: " + isVibrate + " alarm name is: " + alarmName);

        finish();
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
        final CheckBox cb = (CheckBox) findViewById(R.id.isVibrate);
        return cb.isChecked();
    }

    @NonNull
    private String getAlarmName() {
        final EditText et = (EditText) findViewById(R.id.alarmName);
        return et.getText().toString();
    }

    private void writeAlarmToSharedPrefs(Alarm a) {
        String s = getAlarmObjectAsJson(a);
        SharedPreferences sPrefs = getSharedPreferences("Sleepin", MODE_PRIVATE);
        SharedPreferences.Editor pe = sPrefs.edit();
        pe.putString(a.getId(), s);
        pe.apply();

        // debug purposes
        for (Map.Entry<String, ?> e : sPrefs.getAll().entrySet()) {
            Log.i("DB", e.getKey() + " : " + e.getValue());
        }
    }

    private String getAlarmID() {
        String action = getIntentExtra("action");
        String id;
        if (action.equals("edit")) {
            id = alarmForEdit.getId();
        } else {
            int suffix = getMaxID();
            id = "alarm" + suffix;
        }
        return id;
    }

    private String getAlarmObjectAsJson(Alarm a) {
        Gson g = new Gson();
        return g.toJson(a);
    }

    private String getIntentExtra(String key) {
        Intent intent = getIntent();
        return intent.getStringExtra(key);
    }

    private int getMaxID() {
        SharedPreferences s = getSharedPreferences("Sleepin", MODE_PRIVATE);
        int maxID = s.getInt("maxID", 0);
        SharedPreferences.Editor e = s.edit();

        // If maxID does not exist, create it
        if (maxID == 0) {
            e.putInt("maxID", 1);
        } else {
            e.putInt("maxID", ++maxID);
        }
        e.commit();

        return maxID;
    }

    /**
     * Cancels changes made to alarm and returns back to AlarmListActivity
     *
     * @param v The view that the event was triggered from (Cancel button)
     */
    public void cancelAlarmChanges(View v) {
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void chooseRingtone(View v) {

        String[] perms = {"android.permission.READ_EXTERNAL_STORAGE"};
        int permsRequestCode = 200;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, perms, permsRequestCode);
        }

        if (permissionGranted) {
            Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
            this.startActivityForResult(intent, 5);
        }


    }

    @Override

    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {

        switch (permsRequestCode) {
            case 200: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                    Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
                    this.startActivityForResult(intent, 5);
                } else {
                    permissionGranted = false;
                    Toast.makeText(getApplicationContext(), "Did not grant permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }

        switch (permsRequestCode) {
            case 200:
                boolean readStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                break;
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        if (resultCode == Activity.RESULT_OK && requestCode == 5) {
            Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            TextView tv = (TextView) findViewById(R.id.selectRingtone);
            if (uri != null) {
                tv.setText(uri.toString());
                try {
                    MediaPlayer mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setDataSource(this, uri);
                    final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                        mMediaPlayer.setLooping(false);
                        mMediaPlayer.prepare();
                        mMediaPlayer.start();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Failed to play ringtone", Toast.LENGTH_SHORT).show();
                }
            } else {
                tv.setText("Ringtone not selected");
            }
        }
    }

}
