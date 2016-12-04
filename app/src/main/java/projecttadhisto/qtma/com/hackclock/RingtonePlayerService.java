package projecttadhisto.qtma.com.hackclock;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Henry on 2016-12-03.
 */
public class RingtonePlayerService extends Service {

    MediaPlayer media;

    @Override
    public void onCreate() {
        Toast.makeText(this, "In Service", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        media = MediaPlayer.create(this, R.raw.test);
        media.start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "UNBINDING", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }
}
