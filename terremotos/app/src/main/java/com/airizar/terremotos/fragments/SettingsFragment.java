package com.airizar.terremotos.fragments;


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

import com.airizar.terremotos.MainActivity;
import com.airizar.terremotos.R;
import com.airizar.terremotos.SettingsActivity;
import com.airizar.terremotos.tasks.TareaDescargaTerremotos;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.userpreferences);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
      String AUTO_REFRESH=getString(R.string.AUTO_REFRESH);
        String PREF_UPDATE_INTERVAL=getString(R.string.PREF_UPDATE_INTERVAL);
        String MAGNITUDE_VALUES=getString(R.string.MAGNITUDE_VALUES);
        if(key.equals(AUTO_REFRESH)){
           Log.d(TareaDescargaTerremotos.TERREMOTO,key);
        }else if(key.equals(PREF_UPDATE_INTERVAL)){
            Log.d(TareaDescargaTerremotos.TERREMOTO,key);
        }
    }
}
