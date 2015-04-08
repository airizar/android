package com.airizar.terremotos;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airizar.terremotos.services.ServicioDescargaTerremotos;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String CHANGE_KEY = "CHANGE_KEY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.userpreferences);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(CHANGE_KEY, "key " + key);
        String AUTO_REFRESH = getString(R.string.AUTO_REFRESH);
        String PREF_UPDATE_INTERVAL = getString(R.string.PREF_UPDATE_INTERVAL);
        if (key.equals(AUTO_REFRESH)) {
            //start/stop autorefresh

        } else if (key.equals(PREF_UPDATE_INTERVAL)) {
            //Change auto regresh interval
            Log.d(CHANGE_KEY, "value: "+sharedPreferences.getLong(PREF_UPDATE_INTERVAL,0));
        }
       /* if(key.equals(MAGNITUDE_VALUES)) {
            Double val = Double.parseDouble(sharedPreferences.getString(key, "0"));
            Log.d(CHANGE_KEY,"valor key: "+val);
            //Las sharedPreferences estan accesiblres desde cualquier actividad, por lo que no hace
            //falta implementar ninguna interfaz ni nada para pasar datos
        }
*/
    }

    private void setAlarm(long timeOrLengthofWait)  {

        //	Get	a	reference	to	the	Alarm	Manager

        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        //	Set	the	alarm	to	wake	the	device	if	sleeping.
        int alarmType = AlarmManager.ELAPSED_REALTIME_WAKEUP;
        //	Trigger	the	device	in	10	seconds.
        timeOrLengthofWait = 10000;
        //	Create	a	Pending	Intent	that	will	broadcast	and	action
        String ALARM_ACTION = "ALARM_ACTION";
        Intent intentToFire = new Intent(getActivity(),ServicioDescargaTerremotos.class);

        PendingIntent alarmIntent = PendingIntent.getService(getActivity(), 0, intentToFire, 0);
        //	Set	the	alarm
        alarmManager.set(alarmType, timeOrLengthofWait, alarmIntent);
    }
}
