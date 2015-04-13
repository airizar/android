package com.airizar.terremotos.fragments.abstracts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airizar.terremotos.database.TerremotosDB;
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
    private GoogleMap map;
    protected List<Terremoto> terremotos = new ArrayList<Terremoto>();
    protected TerremotosDB terremotosDB;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);
        map = getMap();
        //este this no es el contexto del abstracto,es el contexto de la subclase que se ejecute
        map.setOnMapLoadedCallback(this);
        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        terremotosDB = new TerremotosDB(getActivity());
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
        if(terremotos.size()==1){
            cu = CameraUpdateFactory.newLatLngZoom(new LatLng(terremotos.get(0).getCoord().getLat(),
                    terremotos.get(0).getCoord().getLon()), 9);
        }
        else {
            cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);
        }
        map.animateCamera(cu);
    }
    abstract protected void cargarTerremotos();

}
