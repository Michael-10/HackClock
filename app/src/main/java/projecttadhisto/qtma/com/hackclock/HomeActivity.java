package projecttadhisto.qtma.com.hackclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    ArrayList<String> alarms;
    AlarmAdapter adapter;
    ListView lvAlarms;
    PendingIntent pendingIntent;
    int hour;
    int minute;

    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iAddAlarm = new Intent(HomeActivity.this, AddAlarmActivity.class);
                //start activity to have a result value
                startActivityForResult(iAddAlarm, 1);
            }
        });

        // Create arraylist to store alarms
        alarms = new ArrayList<>();
        // Need adapter to display alarms
        adapter = new AlarmAdapter(alarms, this);
        lvAlarms = (ListView) findViewById(R.id.lvAlarm);
        // links the adapter to the listview on HomeActivity
        lvAlarms.setAdapter(adapter);
        
        lvAlarms.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {

                final AlertDialog.Builder b = new AlertDialog.Builder(HomeActivity.this);
                b.setIcon(android.R.drawable.ic_dialog_alert);
                b.setMessage("Delete?");
                b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        TextView tvAlarm = (TextView) findViewById(R.id.list_item_alarm);
                        alarms.remove(tvAlarm.getText().toString());
                        adapter.notifyDataSetChanged();
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

    public void alarmStart() {
        Intent alarmIntent = new Intent(HomeActivity.this, AlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(HomeActivity.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // get alarm time
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);

        // set alarm
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }

    // get activity result to add a new alarm
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String val = data.getStringExtra("setAlarm");
                hour = data.getIntExtra("hour", -1);
                minute = data.getIntExtra("minute", -1);
                alarms.add(val);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Alarm set for " + val, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
            Intent iSettings = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(iSettings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void start() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 8000;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

    public void startAt10() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * 20;

        /* Set the alarm to start at 10:30 AM */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 9);

        /* Repeating on every 20 minutes interval */
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 20, pendingIntent);
    }
}
