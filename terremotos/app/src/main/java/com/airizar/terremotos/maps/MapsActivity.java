package com.airizar.terremotos.maps;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.airizar.terremotos.R;
import com.airizar.terremotos.database.TerremotosDB;
import com.airizar.terremotos.model.Terremoto;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity {

    private static final int MAXIMO_MOSTRAR = 50;
    private static final String MAP = "MAP";
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private TerremotosDB terremotosDB;
    private ArrayList<Terremoto> listaTerremotos;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        listaTerremotos = new ArrayList<>();
        terremotosDB = new TerremotosDB(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        int magnitud = Integer.parseInt(pref.getString(getString(R.string.MAGNITUDE_VALUES), "0"));
        listaTerremotos.clear();
        listaTerremotos.addAll(terremotosDB.getAllByMagnitude(magnitud));
        int topeAMostrar=Math.min(listaTerremotos.size(),MAXIMO_MOSTRAR);
        for(int i=0;i<topeAMostrar;i++) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(listaTerremotos.get(i).getCoord().getLat(), listaTerremotos.get(i).getCoord().getLon())).title(listaTerremotos.get(i).getLugar()));
            Log.d(MAP, "Nuevo marker insertado");

        }
    }
}
