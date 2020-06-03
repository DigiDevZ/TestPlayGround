package com.zoportfolio.testalarmmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //Testing is complete, and I can now move this to the main project.

    private static final String TAG = "MainActivity.TAG";

    private static final String RAND_STRING = "GOJIRA WALKS";
    private static final String RAND_STRING_2 = "GOJIRA WALKS FURTHER";

    public static final String NOTIFICATION_CHANNELID_TESTREMINDER = "TESTREMINDER_100";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

        Button btnTestAlarmManagerElapsed = findViewById(R.id.btn_testAlarmManagerElapsed);
        btnTestAlarmManagerElapsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testAlarmManagerElapsedRealTime();
            }
        });

        Button btnTestAlarmManagerRealTimeClock = findViewById(R.id.btn_testAlarmManagerRealTimeClock);
        btnTestAlarmManagerRealTimeClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testAlarmManagerRealTimeClock();
            }
        });

    }


    //elapsed real time -> This is best used when needing to do some work based on the passage of time ie. every 30 seconds.
    //real time clock -> This is best suited for when you need to do work at a certain time of day based on locale/timezone.

    //Both of the above alarm types have a wakeup version that will wake up the device and fire the code at that exact moment.

    private void testAlarmManagerElapsedRealTime() {

        //Create the alarm manager and the intent.
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, TestBroadcast.class);
        intent.putExtra(RAND_STRING, "Gojira walks again");
        //FLAG_ONE_SHOT and FLAG_UPDATE_CURRENT are the two constants I should look into using for the app project.
        //ONE_SHOT creates a one-off of the pending intent.
        //UPDATE_CURRENT updates the intent with the new extra data.
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);


        //Set the alarm manager to go off in 10 seconds after the button press.
        if(alarmManager != null) {
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + 10 * 1000,
                    alarmIntent);
        }

    }

    private void testAlarmManagerRealTimeClock() {

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, TestBroadcast.class);
        intent.putExtra(RAND_STRING_2, "Gojira now raids");

        PendingIntent alarmIntent = PendingIntent.getBroadcast(this,0, intent, PendingIntent.FLAG_ONE_SHOT);

        if(alarmManager != null) {

            //Use the Calendar Java utils class, to set a specified time.
            // Will have to set the alarm time to be at the notification time.
            // IMPORTANT: is to set the TimeInMillis property of the calendar instance, using System.currentTimeMillis.
            Calendar time = Calendar.getInstance();
//            time.setTimeInMillis(System.currentTimeMillis());
////            time.set(Calendar.HOUR_OF_DAY, 23);
////            time.set(Calendar.MINUTE, 38);
////            time.set(Calendar.SECOND, 3);
            time.add(Calendar.SECOND, 5);

            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    time.getTimeInMillis(),
                    alarmIntent);
        }

    }


    private void createNotificationChannel() {

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && manager != null) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNELID_TESTREMINDER, "Test channel for reminders.", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("This channel is used to test the alarmManager and broadcastReceiver classes.");

            //NOTE IMPORTANT: Unsure if this is to blame on the emulator being tested right now,
            // but the lights, vibration, and sound does not work for the notification.
            // Should test in the morning with my testing device.

            //After testing, the vibration and sound works! The light still does not,
            // but I think that is because my testing device does not have that hardware.

            //Set the lights for the channel.
            channel.enableLights(true);
            channel.setLightColor(Color.GREEN);

            //Set the vibration to the channel.
            channel.enableVibration(true);

            long VIBRATION_DURATION = 500L;
            long WAITING_DURATION = 500L;
            long[] vibrationPattern = {WAITING_DURATION, VIBRATION_DURATION, WAITING_DURATION, VIBRATION_DURATION};

            channel.setVibrationPattern(vibrationPattern);

            //Set the sound to the channel as well.
            //using the default notification sound and the audio attribute of SONIFICATION, which has to be built.
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            channel.setSound(alarmSound, attributes);

            //Set the visibility of the notification to public.
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            //Create the notification channel.
            manager.createNotificationChannel(channel);
        }
        //TODO: Need to look up how to display notifications on a lower api than 26


    }


}
