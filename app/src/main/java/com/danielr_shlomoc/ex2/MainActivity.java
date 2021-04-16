package com.danielr_shlomoc.ex2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private BatteryReceiver batteryReceiver = null;
    private NotificationManager notificationManager;
    private IntentFilter filter;
    private static final String CHANNEL_ID = "channel_main";
    private static final CharSequence CHANNEL_NAME = "Main Channel";
    private boolean notification;
    public static boolean pause;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("lifeCycle","in onCreate()");
        setContentView(R.layout.activity_main);
        //set landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //remove app title
        Objects.requireNonNull(getSupportActionBar()).hide();
        //remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        notification = false;
        // Get reference Notification Manager system Service
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        batteryReceiver = new BatteryReceiver();
        batteryReceiver.setMainActivityHandler(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifeCycle","in onStart()");
        filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        // Register the receiver to start listening for battery change messages
        registerReceiver(batteryReceiver, filter);



    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifeCycle","in onStop()");
        // Un Register the receiver to stop listening for battery change messages
        unregisterReceiver(batteryReceiver);
    }



    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifeCycle","in onPause()");

        pause = true;


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifeCycle","in onResume()");
        pause = false;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifeCycle","in onDestroy()");
    }



    public void notification() {

        int level = batteryReceiver.getBatteryLevel();

        if(level >= 10)
            notification = false;

        if (level < 10 && !batteryReceiver.isCharging() && !notification) {
            notification = true;
            Log.d("myLog", "in notify");
            // Create & show the Notification. on Build.VERSION < OREO notification avoid CHANEL_ID
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle("Battery low")
                    .setContentText("Please connect the device to a power source")
                    .setPriority(NotificationCompat.PRIORITY_HIGH).build();
            notificationManager.notify(1, notification);
        }


    }
}