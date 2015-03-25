package com.airizar.terremotos.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.airizar.terremotos.R;
import com.airizar.terremotos.adapters.TerremotoAdapter;
import com.airizar.terremotos.model.Terremoto;
import com.airizar.terremotos.tasks.TareaDescargaTerremotos;

import org.json.JSONObject;

import java.util.ArrayList;


public class TerremotoListFragment extends ListFragment implements TareaDescargaTerremotos.AnnadirTerremotoInterface {

    private static final String TAG = "CONECCTION";
    private static final String TERREMOTO = "TERREMOTO";
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
}
