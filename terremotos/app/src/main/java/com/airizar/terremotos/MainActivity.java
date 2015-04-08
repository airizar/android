package com.airizar.terremotos;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.airizar.terremotos.Alarms.AlarmMGR;
import com.airizar.terremotos.maps.MapsActivity;
import com.airizar.terremotos.services.ServicioDescargaTerremotos;
import com.airizar.terremotos.tasks.TareaDescargaTerremotos;


public class MainActivity extends ActionBarActivity implements TareaDescargaTerremotos.AnnadirTerremotoInterface {

    public static final int PREFERENCES_ACTIVITY = 1;
    private static final int MAPS_ACTIVITY = 2;
    private static final String TERREMOTO_PREFS = "TERREMOTO_PREFS";
    private static final String LAUNCHED_BEFORE = "LAUNCHED_BEFORE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        descargaTerremotos();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent prefIntent = new Intent(this, SettingsActivity.class);
            startActivityForResult(prefIntent, PREFERENCES_ACTIVITY);
        }else if(id == R.id.map_markers) {
            Intent prefIntent = new Intent(this, MapsActivity.class);
            startActivityForResult(prefIntent, MAPS_ACTIVITY);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void notifyTotal(int total) {
        String msg = getString(R.string.num_terremotos, total);
        Toast t = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        t.show();
    }

    public void descargaTerremotos() {
        //Intent download=new Intent(this, ServicioDescargaTerremotos.class);
        //startService(download);
        SharedPreferences prefs = getSharedPreferences(TERREMOTO_PREFS, Activity.MODE_PRIVATE);
        if (!prefs.getBoolean(LAUNCHED_BEFORE, false)) {
            long defaultInterval = getResources().getInteger(R.integer.default_interval) * 60 * 1000;
            AlarmMGR.setAlarm(this, defaultInterval);
            prefs.edit().putBoolean(LAUNCHED_BEFORE,true).apply();
        }
    }

    public void comentarios() {
        //Comentado para cambiar a usar los servicios
//        public void descargaTerremotos() {
//            TareaDescargaTerremotos tarea = new TareaDescargaTerremotos(this, this);
//            //crea un nuevo thread y llama al do in background
//            tarea.execute(getString(R.string.urlTerremotos));
//        }
    }

}
