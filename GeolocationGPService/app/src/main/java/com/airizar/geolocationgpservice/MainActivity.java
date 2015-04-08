package com.airizar.geolocationgpservice;

import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends ActionBarActivity  implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, LocationListener{
    private GoogleApiClient googleApiClient;
    private TextView lblEstado;
    private TextView lblLatitud;
    private TextView lblLongitud;
    private Button btnLocActual;
    private Button btnLocUpdate;
    private GoogleApiClient mGoogleApiClient;
    private boolean accesoGPS=false;
    private boolean status=false;
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepararElementosVista();
        prepararGPS();
    }

    private void prepararGPS() {
        if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)==ConnectionResult.SUCCESS){
            accesoGPS=true;
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void prepararElementosVista() {
        lblEstado=(TextView)findViewById(R.id.status);
        lblLatitud=(TextView)findViewById(R.id.lblLat);
        lblLongitud=(TextView)findViewById(R.id.lblLon);
        btnLocActual=(Button)findViewById(R.id.btnLocActual);
        btnLocUpdate=(Button)findViewById(R.id.btnLocUpdate);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(accesoGPS){
            mGoogleApiClient.connect();
        }
    }

    public void obtenerPosicionActual(View v){
        //si esta conectado
        if(status) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
               lblLatitud.setText(String.valueOf(mLastLocation.getLatitude()));
               lblLongitud.setText(String.valueOf(mLastLocation.getLongitude()));
            }
        }
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

        @Override
        public void onConnected(Bundle bundle) {
            status=true;
        }

        @Override
        public void onConnectionSuspended(int i) {

        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {

        }

        @Override
        public void onLocationChanged(Location location) {
            
        }




}
