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
public class SettingsFragment extends PreferenceFragment {
    private static final String CHANGE_KEY = "SCHANGE_KEY";
    private AlarmManager alarmManager;
    private Intent intentToFire;

    private PendingIntent alarmIntent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.userpreferences);


    }

    /*  @Override

    Parece que esto da problemas intermitentes, por lo que en vez de implementar el fragmento lo
    implementara la actividad
   public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

       if(key.equals(MAGNITUDE_VALUES)) {
            Double val = Double.parseDouble(sharedPreferences.getString(key, "0"));
            Log.d(CHANGE_KEY,"valor key: "+val);
            //Las sharedPreferences estan accesiblres desde cualquier actividad, por lo que no hace
            //falta implementar ninguna interfaz ni nada para pasar datos
        }

    }*/

}
