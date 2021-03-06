package com.airizar.terremotos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.airizar.terremotos.fragments.MapaFragment;
import com.airizar.terremotos.fragments.MapaFragmentDetalle;
import com.airizar.terremotos.fragments.TerremotoListFragment;
import com.airizar.terremotos.model.Terremoto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class DetalleTerremoto extends Activity {
    private TextView lblLugar;
    private TextView lblMag;
    private TextView lblLat;
    private TextView lblLon;
    private TextView lblProf;
    private TextView lblFecha;
    private Terremoto terremoto;
    private MapaFragmentDetalle mapFragment;
    private static final String DETALLE = "DETALLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_terremoto);
        Intent intent = getIntent();
        terremoto = (Terremoto) intent.getSerializableExtra(TerremotoListFragment.TERREMOTO_ITEM);
        Log.d(DETALLE, terremoto.get_id());
        getLayoutElements();
        setDataOnElements();
    }

    private void setDataOnElements() {
        lblLugar.setText(terremoto.getLugar());
        lblMag.setText(String.valueOf(terremoto.getMagnitud()));
        lblProf.setText(String.valueOf(terremoto.getCoord().getProdundidad()));
        lblLon.setText(String.valueOf(terremoto.getCoord().getLon()));
        lblLat.setText(String.valueOf(terremoto.getCoord().getLat()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        lblFecha.setText(sdf.format(terremoto.getTime()));
        //mostrarMapa(terremoto);
    }

    private void getLayoutElements() {
        lblFecha = (TextView) findViewById(R.id.lblFecha);
        lblLat = (TextView) findViewById(R.id.lblLat);
        lblLon = (TextView) findViewById(R.id.lblLon);
        lblLugar = (TextView) findViewById(R.id.lblLugar);
        lblMag = (TextView) findViewById(R.id.lblMag);
        lblProf = (TextView) findViewById(R.id.lblProf);
        /*mapFragment = (MapaFragmentDetalle) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.cargarTerremotos();*/
    }
   /* Eliminado al añadir la clase abstracta
        private void mostrarMapa(Terremoto terremoto) {
        List<Terremoto> terremotos = new ArrayList<>();
        terremotos.add(terremoto);
        mapFragment.setTerremotos(terremotos);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalle_terremoto, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
