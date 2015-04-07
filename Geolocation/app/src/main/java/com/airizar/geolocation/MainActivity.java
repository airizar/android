package com.airizar.geolocation;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.airizar.geolocation.listeners.GeoLocListener;


public class MainActivity extends ActionBarActivity implements GeoLocListener.AddLocationInterface{
    private TextView lblLat;
    private TextView lblLon;
    private TextView lblAlt;
    private TextView lblSpeed;

    private String provider;
    private  LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblLat = (TextView) findViewById(R.id.lblLat);
        lblLon = (TextView) findViewById(R.id.lblLon);
        lblAlt = (TextView) findViewById(R.id.lblAlt);
         lblSpeed = (TextView) findViewById(R.id.lblSpeed);

        getLocationProvider();
        listenLocationChanges();
    }

    private void listenLocationChanges() {
        int t=5000;
        int distance= 5;

        locationManager.requestLocationUpdates(provider,t,distance,new GeoLocListener(this));
    }

    private void getLocationProvider() {
        locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);

        Criteria criteria=new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedRequired(true);
        criteria.setAltitudeRequired(true);

        provider=locationManager.getBestProvider(criteria,true);

    }


    @Override
    public void addLocation(Location location) {
        lblAlt.setText(String.valueOf(location.getAltitude()));
        lblLat.setText(String.valueOf(location.getLatitude()));
        lblLon.setText(String.valueOf(location.getLongitude()));
        lblSpeed.setText(String.valueOf(location.getSpeed()));
    }
}
