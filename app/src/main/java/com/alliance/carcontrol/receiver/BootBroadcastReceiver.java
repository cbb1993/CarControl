package com.alliance.carcontrol.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.alliance.carcontrol.MainActivity;

/**
 * Created by 坎坎.
 * Date: 2019/4/18
 * Time: 9:21
 * describe:
 */
public class BootBroadcastReceiver extends BroadcastReceiver {

    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_BOOT) || intent.getAction().equals(Intent.ACTION_LOCKED_BOOT_COMPLETED)) { //开机启动完成后，要做的事情 
            Log.e("-----","====");
            Intent myIntent = new Intent(context, MainActivity.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(myIntent);
        }
    }
}
