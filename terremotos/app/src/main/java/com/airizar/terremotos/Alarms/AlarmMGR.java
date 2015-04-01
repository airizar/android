package com.airizar.terremotos.Alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.airizar.terremotos.services.ServicioDescargaTerremotos;

/**
 * Created by cursomovil on 1/04/15.
 */
public class AlarmMGR {
    public static void setAlarm(Context context,long interval){
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        int alarmType=AlarmManager.RTC;
        Intent intentToFire=new Intent(context, ServicioDescargaTerremotos.class);
        PendingIntent alarmIntent=PendingIntent.getService(context,0,intentToFire,0);
        alarmManager.setRepeating(alarmType,0,interval,alarmIntent);
    }


    public static void cancel(Context context) {
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intentToFire=new Intent(context, ServicioDescargaTerremotos.class);
        PendingIntent alarmIntent=PendingIntent.getService(context,0,intentToFire,0);
        alarmManager.cancel(alarmIntent);
    }

    public static void updateAlarm(Context context,long interval) {
        setAlarm(context,interval);
    }
}
