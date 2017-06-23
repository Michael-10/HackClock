package com.qhackers.sci18.sleepin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Class which displays the user's alarms.
 * The user can create, edit or delete alarms from here, as well
 * as toggle the alarms on or off.
 */
public class AlarmListActivity extends AppCompatActivity {

    private ArrayList<Alarm> alarms;
    private AlarmAdapter alarmAdapter;
    private ListView lvAlarms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alarms = new ArrayList<>();
        alarmAdapter = new AlarmAdapter(this, R.layout.list_item_alarm, alarms);
        lvAlarms = (ListView) findViewById(R.id.lv_alarms);
        lvAlarms.setAdapter(alarmAdapter);

        // Dummy data, can take out
        for (int i = 0; i < 3; ++i) {
            if (i % 2 == 0) {
                alarms.add(new Alarm("7:30", true));
            } else {
                alarms.add(new Alarm("8:30", false));
            }
        }
        // Call this everytime data in the alarms ArrayList has changed.
        alarmAdapter.notifyDataSetChanged();

        // Brings user to new activity where they can create an alarm.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// TODO create the AlarmInfoActivity class (or whatever it should be called).
//                Intent iAlarmInfo = new Intent(AlarmListActivity.this, AlarmInfoActivity.class);
//                startActivity(iAlarmInfo);
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
