package com.airizar.terremotos.fragments;


import android.content.Intent;

import com.airizar.terremotos.fragments.abstracts.AbstractMapFragment;
import com.airizar.terremotos.model.Terremoto;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaFragmentDetalle extends AbstractMapFragment{

    @Override
    public void cargarTerremotos() {
        terremotos.clear();
        //tambien se puede obtener el id y terremotodDB.getElementById(String id);
        Intent intent = getActivity().getIntent();
        Terremoto terremoto = (Terremoto) intent.getSerializableExtra(TerremotoListFragment.TERREMOTO_ITEM);
        terremotos.add(terremoto);

    }


}