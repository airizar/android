package com.airizar.terremotos.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.airizar.terremotos.R;
import com.airizar.terremotos.fragments.abstracts.AbstractMapFragment;
import com.airizar.terremotos.services.ServicioDescargaTerremotos;

public class MapaFragment extends AbstractMapFragment{

    private SharedPreferences pref;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        pref= PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public void cargarTerremotos() {

        terremotos.clear();
        int minmag=Integer.parseInt(pref.getString(getString(R.string.MAGNITUDE_VALUES), "0"));
        terremotos.addAll(terremotosDB.getAllByMagnitude(minmag));

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment,menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_refresh){
            Intent descarga=new Intent(getActivity(),ServicioDescargaTerremotos.class);
            getActivity().startService(descarga);
        }
        return super.onOptionsItemSelected(item);
    }
    public void comentarios(){
        /**
         * A simple {@link Fragment} subclass.
         */

/**
 *public class MapaFragment extends MapFragment implements GoogleMap.OnMapLoadedCallback {
 *
 *    GoogleMap map;
 *    List<Terremoto> terremotos;
 *
 *    @Override
 *    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 *        View layout = super.onCreateView(inflater, container, savedInstanceState);
 *
 *
 *        map = getMap();
 *        map.setOnMapLoadedCallback(this);
 *
 *        return layout;
 *
 *    }
 *
 *
 *
 *    public void setTerremotos(List<Terremoto> terremotos) {
 *       this.terremotos = terremotos;
 *    }
 *
 *    @Override
 *    public void onMapLoaded() {
 *        LatLngBounds.Builder builder = new LatLngBounds.Builder();
 *
 *        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
 *
 *        for (int i = 0; i < terremotos.size(); i++) {
 *            LatLng point = new LatLng(terremotos.get(i).getCoord().getLat(),terremotos.get(i).getCoord().getLon());
 *            MarkerOptions marker = new MarkerOptions()
 *                    .position(point)
 *                    .title(terremotos.get(i).getLugar())
 *                    .snippet("texto a poner");
 *
 *            map.addMarker(marker);
 *            builder.include(marker.getPosition());
 *
 *        }
 *
 *        LatLngBounds bounds = builder.build();
 *
 *        CameraUpdate cu;
 *       //Despues de animar la camara en el callback al acabar el movimiento aplicarle el zoom deseado
 *        if(terremotos.size()==1){
 *            cu = CameraUpdateFactory.newLatLngZoom(new LatLng(terremotos.get(0).getCoord().getLat(),
 *                    terremotos.get(0).getCoord().getLon()), 9);
 *        }
 *        else {
 *            cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);
 *        }
 *        map.animateCamera(cu);
 *
 *    }
}*/

    }
}