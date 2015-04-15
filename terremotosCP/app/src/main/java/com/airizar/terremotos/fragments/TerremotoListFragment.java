package com.airizar.terremotos.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.airizar.terremotos.DetalleTerremoto;
import com.airizar.terremotos.R;
import com.airizar.terremotos.adapters.TerremotoAdapter;
import com.airizar.terremotos.providers.TerremotosDB;
import com.airizar.terremotos.model.Terremoto;
import com.airizar.terremotos.services.ServicioDescargaTerremotos;

import org.json.JSONObject;

import java.util.ArrayList;

public class TerremotoListFragment extends ListFragment {

//public class TerremotoListFragment extends ListFragment implements TareaDescargaTerremotos.AnnadirTerremotoInterface {

    private static final String TAG = "CONECCTION";
    public static final String TERREMOTO = "TERREMOTO";
    public static final String TERREMOTO_ITEM = "TERREMOTO ITEM";
    public static final String MAP_INFO = "DATA";
    private static final String FRAGMENT ="FRAGMENTList" ;
    private JSONObject json;
    private ArrayList<Terremoto> listaTerremotos;
    //
    private ArrayAdapter<Terremoto> aa;
    private SharedPreferences pref;
    private TerremotosDB terremotoDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        listaTerremotos = new ArrayList();
        terremotoDB = new TerremotosDB(getActivity());
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Log.d(FRAGMENT, "onCreate");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        // aa= new ArrayAdapter<Terremoto>(getActivity(), R.layout.terremoto_list_item,listaTerremotos);
        aa = new TerremotoAdapter(getActivity(), R.layout.terremoto_list_item, listaTerremotos);
        Log.d(FRAGMENT, "onCreateView");
        setListAdapter(aa);
        return view;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Terremoto terremoto = listaTerremotos.get(position);
        //como el fragmento no es un contexto le pido su actividad con getActivity
        Intent detailIntent = new Intent(getActivity(), DetalleTerremoto.class);
        //para pasarle los datos utilizar putExtra (como bunddle)
        detailIntent.putExtra(TERREMOTO_ITEM, terremoto);

        startActivity(detailIntent);
    }

    @Override
    public void onResume() {
        super.onResume();
        int magnitud = Integer.parseInt(pref.getString(getString(R.string.MAGNITUDE_VALUES), "0"));
        listaTerremotos.clear();
        listaTerremotos.addAll(terremotoDB.getAllByMagnitude(magnitud));
        Log.d(FRAGMENT, "onResume");
        aa.notifyDataSetChanged();
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
    private void comentarios(){
    /*Eliminado al añadir la base de datos*/

    /* public void annadirTerremoto(Terremoto terremoto) {
        double minMagnitude=Double.parseDouble(pref.getString(getString(R.string.MAGNITUDE_VALUES), "0"));
        if(minMagnitude<=terremoto.getMagnitud()){
            listaTerremotos.add(0,terremoto);
            aa.notifyDataSetChanged();
        }
    }
    */
    /* @Override
    public void notifyTotal(int total) {
        String msg=getString(R.string.num_terremotos,total);
        Toast t= Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT);
        t.show();
    }*/
    /*public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listaTerremotos = new ArrayList<>();
        terremotoDB=new TerremotosDB(getActivity());
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //this , le digo al asyncTask que me avise a mi, como no soy de clase TareaDescargaTerremotosInterface
        //tengo que implementar la interface y definir el terremoto
        /*BD
        TareaDescargaTerremotos tarea=new TareaDescargaTerremotos(getActivity(),this);
        //crea un nuevo thread y llama al do in background
        tarea.execute(getString(R.string.urlTerremotos));
         */
        /*Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                actualizarTerremotos();
            }
        });
        t.start();
    }*/
    }
}
