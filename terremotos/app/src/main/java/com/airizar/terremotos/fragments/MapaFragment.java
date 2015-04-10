package com.airizar.terremotos.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airizar.terremotos.model.Terremoto;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapaFragment extends MapFragment implements GoogleMap.OnMapLoadedCallback {

    GoogleMap map;
    List<Terremoto> terremotos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);
        map = getMap();
        map.setOnMapLoadedCallback(this);

        return layout;

    }
    public void setTerremotos(List<Terremoto> terremotos) {
        this.terremotos = terremotos;
    }

    @Override
    public void onMapLoaded() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        for (int i = 0; i < terremotos.size(); i++) {
            LatLng point = new LatLng(terremotos.get(i).getCoord().getLat(),
                    terremotos.get(i).getCoord().getLon());
            MarkerOptions marker = new MarkerOptions()
                    .position(point)
                    .title(terremotos.get(i).getLugar())
                    .snippet("texto a poner");

            map.addMarker(marker);
            builder.include(marker.getPosition());

        }

        LatLngBounds bounds = builder.build();

        CameraUpdate cu;
         /*
            LatLng ne = bounds.northeast;
            LatLng sw = bounds.southwest;
            restar para saber que latitud y que longitud se abarca, y si es menor que unos minimos abrandar el bounds
         */

        //Sustituir el anterior por este que solo comprueba que si tenemos un solo punto para hacer zoom
        if(terremotos.size()==1){
            cu = CameraUpdateFactory.newLatLngZoom(new LatLng(terremotos.get(0).getCoord().getLat(),
                    terremotos.get(0).getCoord().getLon()), 9);
        }
        else {
            cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);
        }
        map.animateCamera(cu);

    }
}