package com.airizar.terremotos;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.LabeledIntent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import com.airizar.terremotos.Alarms.AlarmMGR;
import com.airizar.terremotos.services.ServicioDescargaTerremotos;

/**
 * Created by cursomovil on 26/03/15.
 */
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //addPreferencesFromResource(R.xml.userpreferences);
        //añadir fragmento
        /*
        Inyectamos el fragmento en el content (contenedor de la Actividad), que es donde iria realmente el layout
         */
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //Log.d(CHANGE_KEY, "key " + key);
        String AUTO_REFRESH = getString(R.string.AUTO_REFRESH);
        String PREF_UPDATE_INTERVAL = getString(R.string.PREF_UPDATE_INTERVAL);
        long interval=Long.parseLong(sharedPreferences.getString(PREF_UPDATE_INTERVAL,"1"))*60*1000;
        if (key.equals(AUTO_REFRESH)) {

            if(sharedPreferences.getBoolean(key,true)){

                AlarmMGR.setAlarm(this,interval);

            }else{
                AlarmMGR.cancel(this);
            }


        } else if (key.equals(PREF_UPDATE_INTERVAL)) {
           AlarmMGR.updateAlarm(this,interval);
        }

    }
}
