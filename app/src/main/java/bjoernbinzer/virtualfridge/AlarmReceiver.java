package bjoernbinzer.virtualfridge;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Tamara on 12.12.2015.
 */
public class AlarmReceiver extends BroadcastReceiver {

    NotificationManager notificationManager;

    //Start alarm service when receiving broadcast signal
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, AlarmService.class);
        context.startService(service);
    }
}
