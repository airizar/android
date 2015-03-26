package com.airizar.terremotos.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.airizar.terremotos.DetalleTerremoto;
import com.airizar.terremotos.R;
import com.airizar.terremotos.adapters.TerremotoAdapter;
import com.airizar.terremotos.model.Terremoto;
import com.airizar.terremotos.tasks.TareaDescargaTerremotos;

import org.json.JSONObject;

import java.util.ArrayList;


public class TerremotoListFragment extends ListFragment implements TareaDescargaTerremotos.AnnadirTerremotoInterface {

    private static final String TAG = "CONECCTION";
    public static final String TERREMOTO = "TERREMOTO";
    public static final String TERREMOTO_ITEM = "TERREMOTO ITEM";
    private JSONObject json;
    private ArrayList<Terremoto> listaTerremotos;
    //
     private ArrayAdapter<Terremoto> aa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listaTerremotos=new ArrayList<>();
        //this , le digo al asyncTask que me avise a mi, como no soy de clase TareaDescargaTerremotosInterface
        //tengo que implementar la interface y definir el terremoto
        TareaDescargaTerremotos tarea=new TareaDescargaTerremotos(this);
        //crea un nuevo thread y llama al do in background
        tarea.execute(getString(R.string.urlTerremotos));
        /*Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                actualizarTerremotos();
            }
        });
        t.start();*/
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= super.onCreateView(inflater, container, savedInstanceState);
       // aa= new ArrayAdapter<Terremoto>(getActivity(), R.layout.terremoto_list_item,listaTerremotos);
        aa= new TerremotoAdapter(getActivity(), R.layout.terremoto_list_item,listaTerremotos);

        setListAdapter(aa);
        return view;
    }


    @Override
    public void annadirTerremoto(Terremoto terremoto) {
        listaTerremotos.add(0,terremoto);
        aa.notifyDataSetChanged();
    }

    @Override
    public void notifyTotal(int total) {
        String msg=getString(R.string.num_terremotos,total);
        Toast t= Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT);
        t.show();
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
}
