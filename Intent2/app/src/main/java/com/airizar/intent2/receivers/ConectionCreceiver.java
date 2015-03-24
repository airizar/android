package com.airizar.intent2.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

public class ConectionCreceiver extends BroadcastReceiver {

    private static final String RECEIVER ="RECEIVER" ;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(RECEIVER, "ConnectionReceiver on Receive()");

        if(intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)){
            Log.d(RECEIVER,"ACTION "+intent.getAction());
        }else if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
            Log.d(RECEIVER,"ACTION "+intent.getAction());

        }
    }
}
