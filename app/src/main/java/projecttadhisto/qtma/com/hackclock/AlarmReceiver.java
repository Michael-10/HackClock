package projecttadhisto.qtma.com.hackclock;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        Ringtone ringtone = RingtoneManager.getRingtone(context, uri);
//        ringtone.play();
        Log.d("HomeActivity", "Hello");
        // For our recurring task, we'll just display a message
//        Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
//        Log.e("Receiver", "Receiver entered");

        Intent serviceIntent = new Intent(context, RingtonePlayerService.class);
        context.startService(serviceIntent);

        Intent i = new Intent();
        i.setClassName("projecttadhisto.qtma.com.hackclock", "projecttadhisto.qtma.com.hackclock.AlarmActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

        Log.d("HomeActivity", "Cancelled!");
        System.exit(0);
        Log.d("HomeActivity", "Did this stop?");
    }
}