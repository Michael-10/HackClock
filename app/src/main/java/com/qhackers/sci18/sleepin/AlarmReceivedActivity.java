package com.qhackers.sci18.sleepin;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.sql.Time;

public class AlarmReceivedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_received);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView tvTime = (TextView) findViewById(R.id.tv_time);
        String lTime = "11:05";
        tvTime.setText(lTime);

        Intent intent = getIntent();
        String s = intent.getStringExtra("alarm");
        Toast.makeText(getApplicationContext(), "Alarm is: " + s, Toast.LENGTH_SHORT).show();
        Gson g = new Gson();
        Alarm alarm = g.fromJson(s, Alarm.class);

        Uri ringtoneUri = Uri.parse(alarm.getRingtone());
        Toast.makeText(getApplicationContext(), "Ringtone is: " + alarm.getRingtone(), Toast.LENGTH_SHORT).show();
        try {
            MediaPlayer mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(this, ringtoneUri);
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
    }

    public void dismissButtonClick(View view) {

    }

    public void snoozeButtonClick(View view) {

    }
}
