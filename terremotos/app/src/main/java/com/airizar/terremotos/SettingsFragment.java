package com.airizar.terremotos;


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
        }
       /* if(key.equals(MAGNITUDE_VALUES)) {
            Double val = Double.parseDouble(sharedPreferences.getString(key, "0"));
            Log.d(CHANGE_KEY,"valor key: "+val);
            //Las sharedPreferences estan accesiblres desde cualquier actividad, por lo que no hace
            //falta implementar ninguna interfaz ni nada para pasar datos
        }
*/
    }
}
