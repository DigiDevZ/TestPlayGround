package com.zoportfolio.testalarmmanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class TestBroadcast extends BroadcastReceiver {

    private static final String TAG = "TestBroadcast.TAG";

    private static final String RAND_STRING = "GOJIRA WALKS";
    private static final String RAND_STRING_2 = "GOJIRA WALKS FURTHER";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null) {
            if(intent.hasExtra(RAND_STRING)) {
                //createToast(context, intent.getStringExtra(RAND_STRING));
                createNotification(context, intent.getStringExtra(RAND_STRING));
            }else if(intent.hasExtra(RAND_STRING_2)) {
                //createToast(context, intent.getStringExtra(RAND_STRING_2));
                createNotification(context, intent.getStringExtra(RAND_STRING_2));
            }
        }
    }

    private void createToast(Context _context, String _message) {
        //Create the toast.
        Toast.makeText(_context, _message, Toast.LENGTH_SHORT).show();
    }

    private void createNotification(Context _context, String _message) {
        //TODO: Test creating a notification with this project before moving into the main project.

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Bitmap largeIcon = BitmapFactory.decodeResource(_context.getResources(), R.drawable.ic_notify_reminder);

            Notification notification = new Notification.Builder(_context, MainActivity.NOTIFICATION_CHANNELID_TESTREMINDER)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.ic_notify_reminder)
                    .setContentTitle("New message from TestAlarmManager Application")
                    .setContentText(_message)
                    .build();

            int notificationId = 64;

            NotificationManager manager = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);
            if(manager != null) {
                manager.notify(notificationId, notification);
            }
        }


        //TODO: Look into creating a notification on a API level lower than O//26
    }

}
