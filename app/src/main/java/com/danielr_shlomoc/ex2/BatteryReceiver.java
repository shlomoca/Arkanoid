package com.danielr_shlomoc.ex2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

public class BatteryReceiver extends BroadcastReceiver {

    private int batteryLevel;
    private boolean isCharging;
    private MainActivity mainActivity;


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        switch (action)
        {
            case Intent.ACTION_BATTERY_CHANGED:
                // get battery level from the received Intent
                batteryLevel = intent.getIntExtra("level", 0);

                // get true or false depends if the phone is charging.
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING;

                this.mainActivity.notification();
                break;
        }
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public boolean isCharging() {
        return isCharging;
    }

    public void setMainActivityHandler(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
}
