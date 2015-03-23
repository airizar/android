package com.airizar.todolistfragment;

import android.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.airizar.todolistfragment.fragment.InputFragment;
import com.airizar.todolistfragment.fragment.TodoFragment;
import com.airizar.todolistfragment.model.Todo;

//debe implementar el listener de fragment, para que el fragmento le pueda pasar los datos
public class MainActivity extends ActionBarActivity implements InputFragment.TodoItemListener {

    private static String TODO = "TODO";
    private InputFragment.TodoItemListener listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //La clase listFragment de java no tiene el metodo addtodo por lo que tendremos que intentar convertir
        //el fragmento obtenido por id para saver si implementa la interfaz que tiene el metodo addtodo
        //listFragment.addTodo
        //ListFragment listFragment=(ListFragment)getFragmentManager().findFragmentById(R.id.listFragment);

        try {
            listFragment = (InputFragment.TodoItemListener) getFragmentManager().findFragmentById(R.id.listFragment);
        } catch (ClassCastException ex) {
            throw new ClassCastException(this.toString() + " must implement TodoItemListener");
        }
    }


    @Override
    public void addTodo(Todo todo) {
        Log.d(TODO, todo.toString());

        listFragment.addTodo(todo);
    }
}
