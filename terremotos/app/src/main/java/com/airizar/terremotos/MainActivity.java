package com.airizar.terremotos;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.airizar.terremotos.tasks.TareaDescargaTerremotos;


public class MainActivity extends ActionBarActivity implements TareaDescargaTerremotos.AnnadirTerremotoInterface {

    public static final int PREFERENCES_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        descargaTerremotos();
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
            Intent prefIntent = new Intent(this, SettingsActivity.class);
            startActivityForResult(prefIntent, PREFERENCES_ACTIVITY);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void notifyTotal(int total) {
        String msg = getString(R.string.num_terremotos, total);
        Toast t = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        t.show();
    }

    public void descargaTerremotos() {
        TareaDescargaTerremotos tarea = new TareaDescargaTerremotos(this, this);
        //crea un nuevo thread y llama al do in background
        tarea.execute(getString(R.string.urlTerremotos));
    }
}
