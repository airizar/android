package com.airizar.todolist;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    //genericos, se puede utilizar para guardar cualquier objeto
    private ArrayList<String> todos;
    private ArrayAdapter<String> aa;
    private Button btnAdd = null;
    private ListView lstTodo = null;
    private TextView txtTodo = null;
    private final String TODOS = "todos";
    private final String TXTINPUT="txtInput";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.todos = new ArrayList<String>();

        //Plantilla de layout que escribe un scrio
        ///android.R.layout.simple_list_item_1
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todos);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        txtTodo = (TextView) findViewById(R.id.txtTodo);
        lstTodo = (ListView) findViewById(R.id.lstTodo);
        lstTodo.setAdapter(aa);
        this.addEventListeners();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //bundle (cajon de sastre), se puede meter cualquier cosa
        Log.d("CHANGE", "onSaveInstanceState()");
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(this.TODOS, this.todos);
        outState.putString(this.TXTINPUT,this.txtTodo.getText().toString());
        Log.d("CHANGE", "onSaveInstanceState()");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("CHANGE", "onRestoreInstanceState()");
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState!=null){
            this.restoreState(savedInstanceState);
        }
    }

private void restoreState(Bundle savedInstanceState) {

    //Despues de girar el adapter esta apuntando a una posicion de memoria que esta vacio, por lo que
    //al asignar a todos el valor, lo sobreEscribe y esta vacio. por lo tanto hay que añadirlos
    this.todos.addAll(savedInstanceState.getStringArrayList(this.TODOS));
    this.txtTodo.setText(savedInstanceState.getString(this.TXTINPUT));
}


    private void addEventListeners() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todo = txtTodo.getText().toString();
                todos.add(0, todo);
                txtTodo.setText("");
                aa.notifyDataSetChanged();
            }
        });

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
}
