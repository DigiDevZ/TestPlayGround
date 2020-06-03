package com.zoportfolio.testalarmmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity.TAG";

    private static final String RAND_STRING = "GOJIRA WALKS";
    private static final String RAND_STRING_2 = "GOJIRA WALKS FURTHER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

            //Use the Calendar Java utils class, to get the time that will be the next 5 seconds.
            Calendar time = Calendar.getInstance();
            //time.getTimeInMillis();
            time.add(Calendar.SECOND, 5);

            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    time.getTimeInMillis(),
                    alarmIntent);
        }

    }


}
