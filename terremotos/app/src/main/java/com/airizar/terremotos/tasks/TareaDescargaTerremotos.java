package com.airizar.terremotos.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.airizar.terremotos.R;
import com.airizar.terremotos.model.Coordenada;
import com.airizar.terremotos.model.Terremoto;

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

/**
 * Created by cursomovil on 25/03/15.
 * Terremoto: no se le pasa desde fuera
 */
public class TareaDescargaTerremotos extends AsyncTask<String,Terremoto,Integer> {

    private static final String TAG = "CONNECTION";
    private static final String TERREMOTO = "TERREMOTO";
    private final AnnadirTerremotoInterface target;

    public interface AnnadirTerremotoInterface{
       public void annadirTerremoto(Terremoto terremoto);
       public void notifyTotal(int total);
   }

    public TareaDescargaTerremotos(AnnadirTerremotoInterface target) {
        this.target=target;
    }

    /*
           Esto es nuestro Thread
         */
    @Override
    protected Integer doInBackground(String... params) {
        int count =0;
        if(params.length>0){
            count=actualizarTerremotos(params[0]);

        }
        return new Integer(count);
    }

    @Override
    protected void onProgressUpdate(Terremoto... terremoto) {
        super.onProgressUpdate(terremoto);
        target.annadirTerremoto(terremoto[0]);
    }

    @Override
    protected void onPostExecute(Integer count) {
        super.onPostExecute(count);
        target.notifyTotal(count.intValue());
    }

    private int actualizarTerremotos(String urlTerremotos) {
        //String urlTerrremotos = getString(R.string.urlTerremotos);
        int count=0;
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
                count=terremotos.length();
                for (int i = terremotos.length() - 1; i >= 0; i--) {
                    procesarTerremotos(terremotos.getJSONObject(i));
                }
            }
        } catch (MalformedURLException e) {
            Log.d(TAG, "Malformed	URL	Exception.", e);
        } catch (IOException e) {
            Log.d(TAG, "IO	Exception.", e);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return count;
    }
    private void procesarTerremotos(JSONObject jsonObject) {
        try {
            String id=jsonObject.getString("id");
            JSONArray coordJson=jsonObject.getJSONObject("geometry").getJSONArray("coordinates");
            Coordenada coords=new Coordenada(coordJson.getDouble(0),coordJson.getDouble(1),coordJson.getDouble(2));
            JSONObject jsonPropiedades=jsonObject.getJSONObject("properties");
            Terremoto terremoto=new Terremoto();
            terremoto.set_id(id);
            terremoto.setCoord(coords);
            terremoto.setLugar(jsonPropiedades.getString("place"));
            terremoto.setMagnitud(jsonPropiedades.getDouble("mag"));
            terremoto.setTime(jsonPropiedades.getLong("time"));
            terremoto.setUrl(jsonPropiedades.getString("url"));

            Log.d(TERREMOTO, id+" : "+terremoto.toString());
            //para sincronizarme con la vista y avisarle de que tengo un dato util para la vista,
            // mediante publishprogress (que llamara a on progressupdate)
            publishProgress(terremoto);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
