package com.qhackers.sci18.sleepin;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

/**
 * Class which displays the user's alarms.
 * The user can create, edit or delete alarms from here, as well
 * as toggle the alarms on or off.
 */
public class AlarmListActivity extends AppCompatActivity {

    private ArrayList<Alarm> alarmList;
    private AlarmAdapter alarmAdapter;
    private ListView lvAlarms;

    private SharedPreferences sPrefs;
    private SharedPreferences.Editor prefsEditor;
    private Gson gson;

    private AlarmManager am;
    private PendingIntent alarmIntent;
    private Calendar cal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeComponents();
        alarmListClick();
        alarmListLongClick();
//        readSharedPreferences();
        initializeFAB();
//        stopAlarmManager();
    }

    /**
     * Used to stop all alarms that are set.
     */
    private void stopAlarmManager() {
        AlarmManager aManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        aManager.cancel(pIntent);
    }

    /**
     * When an alarm is clicked, the data from the alarm is sent to AlarmInfoActivity for editing.
     */
    private void alarmListClick() {
        lvAlarms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Alarm alarmClicked = (Alarm) lvAlarms.getItemAtPosition(pos);
                String alarmId = alarmClicked.getId();
                Intent iAlarmInfo = new Intent(AlarmListActivity.this, AlarmInfoActivity.class);
                iAlarmInfo.putExtra("action", "edit");
                iAlarmInfo.putExtra("alarmId", alarmClicked);
                startActivity(iAlarmInfo);
            }
        });
    }

    /**
     * When an alarm is long clicked, the alarm is deleted.
     */
    private void alarmListLongClick() {
        lvAlarms.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long id) {
                final AlertDialog.Builder b = new AlertDialog.Builder(AlarmListActivity.this);
                b.setIcon(android.R.drawable.ic_dialog_alert);
                b.setMessage("Delete?");

                b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Alarm alarmForDelete = (Alarm) lvAlarms.getItemAtPosition(pos);
                        String alarmId = alarmForDelete.getId();
                        sPrefs.edit().remove(alarmId).commit();
                        onResume();
                    }
                });

                b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                b.show();

                return true;
            }
        });
    }

    /**
     * When the user clicks the floating action button, they are taken to the AlarmInfoActivity
     * where they can create a new alarm.
     */
    private void initializeFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iAlarmInfo = new Intent(AlarmListActivity.this, AlarmInfoActivity.class);
                iAlarmInfo.putExtra("action", "create");
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

    private void initializeComponents() {
        setContentView(R.layout.activity_alarm_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alarmList = new ArrayList<>();
        alarmAdapter = new AlarmAdapter(this, R.layout.list_item_alarm, alarmList);
        lvAlarms = (ListView) findViewById(R.id.lv_alarms);
        lvAlarms.setAdapter(alarmAdapter);

        // SharedPreferences initialization
        sPrefs = getSharedPreferences("Sleepin", MODE_PRIVATE);
        prefsEditor = sPrefs.edit();
        gson = new Gson();
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

    @Override
    public void onResume() {
        super.onResume();
        readSharedPreferences();
    }

    /**
     * Reads from SharedPreferences data and creates alarm objects to display in the alarmList ListView.
     */
    private void readSharedPreferences() {
        alarmList.clear();
        Map<String, ?> alarmData = sPrefs.getAll();
//        Log.d("DB", "here: \n" + alarmData.toString());
        if (!alarmData.isEmpty()) {
            for (Map.Entry<String, ?> entry : alarmData.entrySet()) {
                if (!entry.getKey().equals("maxID")) {
                    String alarmString = entry.getValue().toString();
                    Alarm tempAlarm = gson.fromJson(alarmString, Alarm.class);
                    alarmList.add(tempAlarm);

//                    cal = Calendar.getInstance();
//                    cal.setTimeInMillis(System.currentTimeMillis());
//                    cal.set(Calendar.HOUR_OF_DAY, tempAlarm.getHour());
//                    cal.set(Calendar.MINUTE, tempAlarm.getMinute());

//                    am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//                    Intent intent = new Intent(this, MyBroadcastReceiver.class);
//                    alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
//
//                    am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 60 * 1000, alarmIntent);

                }
            }
            Collections.sort(alarmList, new Comparator<Alarm>() {
                @Override
                public int compare(Alarm alarm1, Alarm alarm2) {
                    int hourComp = alarm1.getHour() - alarm2.getHour();
                    if (hourComp != 0) {
                        return hourComp;
                    } else {
                        return alarm1.getMinute() - alarm2.getMinute();
                    }
                }
            });
            alarmAdapter.notifyDataSetChanged();
        }
    }

}
