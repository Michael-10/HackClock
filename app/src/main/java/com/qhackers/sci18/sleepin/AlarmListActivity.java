package com.qhackers.sci18.sleepin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Class which displays the user's alarms.
 * The user can create, edit or delete alarms from here, as well
 * as toggle the alarms on or off.
 */
public class AlarmListActivity extends AppCompatActivity {

    private ArrayList<Alarm> alarmList;
    private AlarmAdapter alarmAdapter;
    private ListView lvAlarms;
    private int alarmKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alarmList = new ArrayList<>();
        alarmAdapter = new AlarmAdapter(this, R.layout.list_item_alarm, alarmList);
        lvAlarms = (ListView) findViewById(R.id.lv_alarms);
        lvAlarms.setAdapter(alarmAdapter);

        // SharedPreferences initialization
        SharedPreferences sPrefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sPrefs.edit();

        // Clear SharedPreferences
        prefsEditor.clear();

        Gson gson = new Gson();

        // Test alarms TODO take these out
        Alarm alarm1 = new Alarm(5, 30, true, true, "alarm!!");
        Alarm alarm2 = new Alarm(7, 30, false, true, "alarm2!!");
        Alarm alarm3 = new Alarm(8, 40, true, true, "alarm3!!");
        String json1 = gson.toJson(alarm1);
        String json2 = gson.toJson(alarm2);
        String json3 = gson.toJson(alarm3);
//        Set<String> alarmSet = new HashSet<>();
//        alarmSet.add(json1);
//        alarmSet.add(json2);

        // Writing to SharedPreferences (USING STRINGSET)
//        prefsEditor.putStringSet("alarms", alarmSet);
//        prefsEditor.apply();

        // Writing to SharedPreferences (NOT USING STRINGSET)
        prefsEditor.putString("alarm1", json1);
        prefsEditor.putString("alarm2", json2);
        prefsEditor.putString("alarm3", json3);
        prefsEditor.apply();

        // Reading from SharedPreferences (USING STRINGSET)
//        Set<String> jsonReceived = sPrefs.getStringSet("alarms", new HashSet<String>());
//        if (!jsonReceived.isEmpty()) {
//            Iterator<String> iterator = jsonReceived.iterator();
//            while (iterator.hasNext()) {
//                String alarmString = iterator.next();
//                Alarm tempAlarm = gson.fromJson(alarmString, Alarm.class);
//                alarmList.add(tempAlarm);
//            }
//            alarmAdapter.notifyDataSetChanged();
//        }

        // Reading from SharedPreferences (NOT USING STRINGSET)
        Map<String, ?> alarmData = sPrefs.getAll();
        if (!alarmData.isEmpty()) {
            Log.d("sharedPrefs", alarmData.toString());
            for (Map.Entry<String, ?> entry : alarmData.entrySet()) {
                String alarmString = entry.getValue().toString();
                Alarm tempAlarm = gson.fromJson(alarmString, Alarm.class);
                alarmList.add(tempAlarm);
            }
            alarmAdapter.notifyDataSetChanged();
        }

//        String jsonReceived = sPrefs.getString("alarm1", "");
//        Alarm alarmReceived = gson.fromJson(jsonReceived, Alarm.class);
//        Log.d("sharedPrefsTest", jsonReceived);

        // Brings user to new activity where they can create an alarm.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iAlarmInfo = new Intent(AlarmListActivity.this, AlarmInfoActivity.class);
                startActivity(iAlarmInfo);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarms, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
