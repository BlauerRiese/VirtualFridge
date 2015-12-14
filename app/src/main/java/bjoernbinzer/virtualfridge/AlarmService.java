package bjoernbinzer.virtualfridge;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by D060183 on 12.12.2015.
 */
public class AlarmService extends Service {

    private static final int NOTIFICATION_ID = 1;
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    final ArrayList<FridgeItem> productList = new ArrayList<FridgeItem>();
    ArrayList<String> productNameList = new ArrayList<String>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //Handle notification on start
    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {
        super.onStartCommand(intent, flags, startID);
        handleCommand(intent);
        createNotification(productNameList);
        return START_STICKY;
    }

    //Handle notification
    public void handleCommand(Intent intent) {
        //Get date of today
        Calendar today = Calendar.getInstance();
        int day = today.get(Calendar.DATE);
        int month = today.get(Calendar.MONTH);

        //Add 1 to month as system time starts counting at 0 for January
        month++;
        int year = today.get(Calendar.YEAR);

        //Create date format "dd.MM.yyyy"
        String date = String.valueOf(day) + "." + String.valueOf(month) + "." + String.valueOf(year);


        //Get date of tomorrow
        today.add(Calendar.DATE, 1);
        int day2 = today.get(Calendar.DATE);
        int month2 = today.get(Calendar.MONTH);

        //Add 1 to month as system time starts counting at 0 for January
        month2++;
        int year2 = today.get(Calendar.YEAR);

        //Create date format "dd.MM.yyyy"
        String date2 = String.valueOf(day2) + "." + String.valueOf(month2) + "." + String.valueOf(year2);


        //Get date of day after tomorrow
        today.add(Calendar.DATE, 1);
        int day3 = today.get(Calendar.DATE);
        int month3 = today.get(Calendar.MONTH);

        //Add 1 to month as system time starts counting at 0 for January
        month3++;
        int year3 = today.get(Calendar.YEAR);
        //Create date format "dd.MM.yyyy"
        String date3 = String.valueOf(day3) + "." + String.valueOf(month3) + "." + String.valueOf(year3);

        //Read from DB which products are due within the next 3 days
        Cursor cursor = FridgeDB.getEntryByDate(date, date2, date3);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String item;

        //Read DB results
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                Date durability = new Date();
                try {
                    durability = sdf.parse(cursor.getString(2));
                } catch(Exception e) {};
                double quantity = Double.parseDouble(cursor.getString(4));
                String uom = cursor.getString(5);
                double price = Double.parseDouble(cursor.getString(3));
                String category = cursor.getString(6);
                FridgeItem product = new FridgeItem(id, name, durability, quantity, uom, price, category);
                productList.add(product);
                if(uom.equals("Stück")){
                    item = quantity + " " +name;
                }else{
                    item = quantity + " " + uom + " " +name;
                }
                productNameList.add(item);
            } while (cursor.moveToNext());
        }
    }

    //Create notification to notify user via the homescreen about products that need to be consumed soon
    public void createNotification(ArrayList<String> productNameList) {
        //Set notification icon, title and text
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_fridge)
                .setContentTitle("Zeit zu kochen!")
                .setContentText("Einige Produkte laufen demnächst ab.");

        //Set long text of notification if user expands notification text
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("Zeit zu kochen!");
        inboxStyle.addLine("Einige Produkte laufen demnächst ab:");

        //List products that are due soon in notification
        String[] products = new String[productNameList.size()];
        for (int i = 0; i < productNameList.size(); i++) {
            inboxStyle.addLine(productNameList.get(i));
        }

        //Apply all settings to the notification
        builder.setStyle(inboxStyle);

        //Open first screen of the application if user clicks on notification
        Intent resultIntent = new Intent(this, SplashScreen.class);

        //Create TaskStackBuilder to handle back navigtion of notification
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        //Update current pending intent
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        //Get notification service of the system
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Build the notification and notify user on home screen
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
