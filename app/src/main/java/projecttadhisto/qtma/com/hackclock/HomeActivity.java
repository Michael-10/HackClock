package projecttadhisto.qtma.com.hackclock;

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
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

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
                startActivity(iAddAlarm);
            }
        });

        // Create arraylist to store alarms
        ArrayList<String> alarms = new ArrayList<>();
        // Need adapter to display alarms
        AlarmAdapter adapter = new AlarmAdapter(alarms, this);
        ListView lvAlarms = (ListView) findViewById(R.id.lvAlarm);
        // links the adapter to the listview on HomeActivity
        lvAlarms.setAdapter(adapter);

        Intent extras = getIntent();
        String value = extras.getStringExtra("setAlarm");
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
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
}
