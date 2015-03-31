package com.airizar.terremotos.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.airizar.terremotos.R;
import com.airizar.terremotos.database.TerremotosDB;
import com.airizar.terremotos.model.Coordenada;
import com.airizar.terremotos.model.Terremoto;
import com.airizar.terremotos.tasks.TareaDescargaTerremotos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ServicioDescargaTerremotos extends Service {
    private TerremotosDB terremotosDB;
    @Override
    public void onCreate() {
        super.onCreate();
        terremotosDB=new TerremotosDB(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                actualizarTerremotos(getString(R.string.urlTerremotos));
            }
        });
        t.start();
        return Service.START_STICKY;
    }

    private int actualizarTerremotos(String urlTerremotos) {
        //String urlTerrremotos = getString(R.string.urlTerremotos);
        int count = 0;
        try {
            URL url = new URL(urlTerremotos);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader streamReader = new BufferedReader(
                        new InputStreamReader(
                                httpConnection.getInputStream(), "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                JSONObject json = new JSONObject(responseStrBuilder.toString());
                JSONArray terremotos = json.getJSONArray("features");
                count = terremotos.length();
                for (int i = terremotos.length() - 1; i >= 0; i--) {
                    procesarTerremotos(terremotos.getJSONObject(i));
                }
            }
        } catch (MalformedURLException e) {
            Log.d(TareaDescargaTerremotos.TAG, "Malformed	URL	Exception.", e);
        } catch (IOException e) {
            Log.d(TareaDescargaTerremotos.TAG, "IO	Exception.", e);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return count;
    }

    private void procesarTerremotos(JSONObject jsonObject) {
        try {
            String id = jsonObject.getString("id");
            JSONArray coordJson = jsonObject.getJSONObject("geometry").getJSONArray("coordinates");
            Coordenada coords = new Coordenada(coordJson.getDouble(0), coordJson.getDouble(1), coordJson.getDouble(2));
            JSONObject jsonPropiedades = jsonObject.getJSONObject("properties");
            Terremoto terremoto = new Terremoto();
            terremoto.set_id(id);
            terremoto.setCoord(coords);
            terremoto.setLugar(jsonPropiedades.getString("place"));
            terremoto.setMagnitud(jsonPropiedades.getDouble("mag"));
            terremoto.setTime(jsonPropiedades.getLong("time"));
            terremoto.setUrl(jsonPropiedades.getString("url"));
            Log.d(TareaDescargaTerremotos.TERREMOTO, id + " : " + terremoto.toString());
            //para sincronizarme con la vista y avisarle de que tengo un dato util para la vista,
            // mediante publishprogress (que llamara a on progressupdate)
            terremotosDB.annadirTerremoto(terremoto);
            //publishProgress(terremoto);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
