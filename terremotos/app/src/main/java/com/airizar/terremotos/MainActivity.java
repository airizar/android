package com.airizar.terremotos;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.airizar.terremotos.fragments.MapaFragment;
import com.airizar.terremotos.fragments.MapaFragmentDetalle;
import com.airizar.terremotos.fragments.TerremotoListFragment;
import com.airizar.terremotos.listeners.TabListener;
import com.airizar.terremotos.services.ServicioDescargaTerremotos;
import com.airizar.terremotos.tasks.TareaDescargaTerremotos;


public class MainActivity extends Activity implements TareaDescargaTerremotos.AnnadirTerremotoInterface {

    public static final int PREFERENCES_ACTIVITY = 1;

    private static final int MAPS_ACTIVITY = 2;
    private static final String TERREMOTO_PREFS = "TERREMOTO_PREFS";
    private static final String LAUNCHED_BEFORE = "LAUNCHED_BEFORE";
    private static final String TAB_INDEX = "TAB_INDEX";
    private TabHost mTabHost;
    private MapaFragment mapFragment;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createTabs();
        descargaTerremotos();
    }

    private void createTabs() {


        actionBar=getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tabLista	=	actionBar.newTab();

        tabLista.setText(getString(R.string.lista))
                .setContentDescription("Descripcion del contenido")

                .setTabListener(new TabListener<TerremotoListFragment>
                        (this,	R.id.fragmentContainer,TerremotoListFragment.class));

        actionBar.addTab(tabLista);
        ActionBar.Tab tabMapa	=	actionBar.newTab();

        tabMapa.setText(getString(R.string.mapa))
                .setContentDescription("Descripcion del contenido")

                .setTabListener(new TabListener<MapaFragment>
                        (this,	R.id.fragmentContainer,MapaFragment.class));

        actionBar.addTab(tabMapa);

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
        Intent download=new Intent(this, ServicioDescargaTerremotos.class);
        startService(download);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int currentTabIndex=actionBar.getSelectedNavigationIndex();
        outState.putInt(TAB_INDEX, currentTabIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int tabIndex=savedInstanceState.getInt(TAB_INDEX);
        actionBar.setSelectedNavigationItem(tabIndex);
    }

    public void comentarios(){
    //Comentado para cambiar a usar los servicios
//        public void descargaTerremotos() {
//            TareaDescargaTerremotos tarea = new TareaDescargaTerremotos(this, this);
//            //crea un nuevo thread y llama al do in background
//            tarea.execute(getString(R.string.urlTerremotos));
//        }
    }

}
