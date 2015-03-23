package com.airizar.todolistfragment.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.airizar.todolistfragment.DetailActivity;
import com.airizar.todolistfragment.R;

import com.airizar.todolistfragment.adapters.TodoAdapter;
import com.airizar.todolistfragment.fragment.dummy.DummyContent;
import com.airizar.todolistfragment.model.Todo;

import java.util.ArrayList;


public class TodoFragment extends ListFragment implements InputFragment.TodoItemListener {
    private final String TODOS_KEY = "Todos_key";

    public static final String TODO_ITEM = "Todo item";
    /*
        Los datos los guardaremos en el fragmento, para poder llevarnos este fragmento a otra activity sin
        necesidad de llevarme tambien el activity
         */
    private ArrayList<Todo> todos;
    // private ArrayAdapter<Todo> aa;
    private TodoAdapter aa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);
        todos = new ArrayList();
        //solo las actividades,servicios, broadcast... son contextos
        //contexto, layout,datos
        //aa= (TodoAdapter) new ArrayAdapter<>(getActivity(),R.layout.todo_list_item,todos);
        aa = new TodoAdapter(getActivity(), R.layout.todo_list_item, todos);
        //en este punto el loader desaparece porque ya hay un adapter pero la lista esta vacia
        if (savedInstanceState != null) {
            //todos.addAll(savedInstanceState.getStringArrayList(TODOS_KEY));
           // ArrayList<Todo> tmp = savedInstanceState.getParcelableArrayList(TODOS_KEY);
            ArrayList<Todo> tmp = (ArrayList<Todo>) savedInstanceState.getSerializable(TODOS_KEY);
            todos.addAll(tmp);

        }
        setListAdapter(aa);

        return layout;
    }

    @Override
    public void addTodo(Todo todo) {
        todos.add(0, todo);
        //notificar al adaptador que hay cambios
        aa.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // outState.putStringArrayList(TODOS_KEY,todos);
        //outState.putParcelableArrayList(TODOS_KEY, todos);
        outState.putSerializable(TODOS_KEY,todos);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //position: posicion del arrray en la que he pinchado
        Todo todo = todos.get(position);
        //como el fragmento no es un contexto le pido su actividad con getActivity
        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
        //para pasarle los datos utilizar putExtra (como bunddle)
        detailIntent.putExtra(TODO_ITEM, todo);
        startActivity(detailIntent);

    }
}
