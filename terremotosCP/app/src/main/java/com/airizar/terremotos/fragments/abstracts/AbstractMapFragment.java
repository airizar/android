package com.airizar.terremotos.fragments.abstracts;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airizar.terremotos.providers.TerremotosDB;
import com.airizar.terremotos.model.Terremoto;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cursomovil on 13/04/15.
 */
abstract public class AbstractMapFragment extends MapFragment implements GoogleMap.OnMapLoadedCallback {
    private static final String FRAGMENT = "FRAGMENT";
    private GoogleMap map;
    protected List<Terremoto> terremotos = new ArrayList<Terremoto>();
    protected TerremotosDB terremotosDB;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);
        map = getMap();
        //este this no es el contexto del abstracto,es el contexto de la subclase que se ejecute
        map.setOnMapLoadedCallback(this);
        Log.d(FRAGMENT, "onCreateView");
        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        terremotosDB = new TerremotosDB(getActivity());
        Log.d(FRAGMENT, "onCreate");
    }

    @Override
    public void onMapLoaded() {
        this.cargarTerremotos();
        this.mostrarMapa();

    }

   private void mostrarMapa() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        for (int i = 0; i < terremotos.size(); i++) {
            LatLng point = new LatLng(terremotos.get(i).getCoord().getLat(),terremotos.get(i).getCoord().getLon());
            MarkerOptions marker = new MarkerOptions()
                    .position(point)
                    .title(terremotos.get(i).getLugar())
                    .snippet("texto a poner");

            map.addMarker(marker);
            builder.include(marker.getPosition());

        }

        LatLngBounds bounds = builder.build();

        CameraUpdate cu;
        //Despues de animar la camara en el callback al acabar el movimiento aplicarle el zoom deseado
       if(terremotos.size()>0){
        if(terremotos.size()==1){
            cu = CameraUpdateFactory.newLatLngZoom(new LatLng(terremotos.get(0).getCoord().getLat(),
                    terremotos.get(0).getCoord().getLon()), 9);
        }
        else {
            cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);
        }
        map.animateCamera(cu);}
    }
    abstract protected void cargarTerremotos();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(FRAGMENT, "onAttach");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(FRAGMENT, "onActivityCreated");
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(FRAGMENT, "onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(FRAGMENT, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(FRAGMENT, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(FRAGMENT, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(FRAGMENT, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(FRAGMENT, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(FRAGMENT, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(FRAGMENT, "onDettach");
    }
    /*onAttach(Activity) called once the fragment is associated with its activity.

    onActivityCreated(Bundle) tells the fragment that its activity has completed its own Activity.onCreate().
    onViewStateRestored(Bundle) tells the fragment that all of the saved state of its view hierarchy has been restored.
    onStart() makes the fragment visible to the user (based on its containing activity being started).
    onResume() makes the fragment interacting with the user (based on its containing activity being resumed).
    As a fragment is no longer being used, it goes through a reverse series of callbacks:

    onPause() fragment is no longer interacting with the user either because its activity is being paused or a fragment operation is modifying it in the activity.
    onStop() fragment is no longer visible to the user either because its activity is being stopped or a fragment operation is modifying it in the activity.
    onDestroyView() allows the fragment to clean up resources associated with its View.
    onDestroy() called to do final cleanup of the fragment's state.
    onDetach() called immediately prior to the fragment no longer being associated with its activity.*/
}
