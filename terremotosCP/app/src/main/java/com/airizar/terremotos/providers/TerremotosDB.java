package com.airizar.terremotos.providers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.airizar.terremotos.model.Coordenada;
import com.airizar.terremotos.model.Terremoto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cursomovil on 27/03/15.
 */
public class TerremotosDB {

    public static final String[] ALL_COLLUMNS = {TerremotosProvider.Columns._ID,
            TerremotosProvider.Columns.PLACE,
            TerremotosProvider.Columns.MAGNITUDE,
            TerremotosProvider.Columns.LAT,
            TerremotosProvider.Columns.LON,
            TerremotosProvider.Columns.DEPTH,
            TerremotosProvider.Columns.URL_TERREMOTOS,
            TerremotosProvider.Columns.TIME};
    private final Context context;

    //No hace falta el constructor, ya que solo obtenia el helper, y este trabajo ahora es del provider
    //Pero si hace falta para obtener el contexto que necesitamos para "instanciar" un resolver
    public TerremotosDB(Context context){
        this.context=context;
    }
    //Polimorfismo, al utilizar list despues puedo cambiar el resultado a cualquier clase que
    // implemente a list sin tener ningun problema
   public List<Terremoto> query(String where, String[] whereArgs) {

       ContentResolver cr=context.getContentResolver();




        String groupBy = null;
        String having = null;
        String order = TerremotosProvider.Columns.TIME + " DESC";
        Cursor cursor = cr.query(TerremotosProvider.CONTENT_URI, ALL_COLLUMNS, where, whereArgs,  order);
        ArrayList<Terremoto> terremotos = new ArrayList<>();
        //HashMap para obtener los indices de las columnas
        HashMap<String, Integer> indexes = new HashMap<>();
        for (int i = 0; i < ALL_COLLUMNS.length; i++) {
            indexes.put(ALL_COLLUMNS[i], cursor.getColumnIndex(ALL_COLLUMNS[i]));
        }

        while (cursor.moveToNext()) {
            Terremoto terremoto = new Terremoto();
            terremoto.set_id(cursor.getString(indexes.get(TerremotosProvider.Columns._ID)));
            terremoto.setLugar(cursor.getString(indexes.get(TerremotosProvider.Columns.PLACE)));
            terremoto.setMagnitud(cursor.getDouble(indexes.get(TerremotosProvider.Columns.MAGNITUDE)));
            terremoto.setCoord(new Coordenada(cursor.getDouble(indexes.get(TerremotosProvider.Columns.LON)), cursor.getDouble(indexes.get(TerremotosProvider.Columns.LAT)), cursor.getDouble(indexes.get(TerremotosProvider.Columns.DEPTH))));
            terremoto.setUrl(cursor.getString(indexes.get(TerremotosProvider.Columns.URL_TERREMOTOS)));
            terremoto.setTime(cursor.getLong(indexes.get(TerremotosProvider.Columns.TIME)));
            terremotos.add(terremoto);
        }
        cursor.close();
        return terremotos;

    }



    public List<Terremoto> getAllByMagnitude(int magnitude) {
        String where = TerremotosProvider.Columns.MAGNITUDE + " >=?";
        String[] whereArgs = {String.valueOf(magnitude)};
        return query(where, whereArgs);
    }
     /*  public List<Terremoto> getAll() {
        return query(null, null);
    }
    public List<Terremoto> getTerremotoById(String id){
        String where = TerremotosProvider.Columns._ID + " >=?";
        String[] whereArgs = {String.valueOf(id)};

        return query(where,whereArgs);
    }*/

    public void annadirTerremoto(Terremoto terremoto) {
        ContentValues newValues = new ContentValues();
        newValues.put(TerremotosProvider.Columns._ID, terremoto.get_id());
        newValues.put(TerremotosProvider.Columns.PLACE, terremoto.getLugar());
        newValues.put(TerremotosProvider.Columns.MAGNITUDE, terremoto.getMagnitud());
        newValues.put(TerremotosProvider.Columns.LAT, terremoto.getCoord().getLat());
        newValues.put(TerremotosProvider.Columns.LON, terremoto.getCoord().getLon());
        newValues.put(TerremotosProvider.Columns.DEPTH, terremoto.getCoord().getProdundidad());
        newValues.put(TerremotosProvider.Columns.URL_TERREMOTOS, terremoto.getUrl());
        newValues.put(TerremotosProvider.Columns.TIME, terremoto.getTime().getTime());
        ContentResolver cr=context.getContentResolver();
        cr.insert(TerremotosProvider.CONTENT_URI,newValues);
      /*  try {
            db.insertOrThrow(TerremotosOpenHelper.DATABASE_TABLE, null, newValues);
        } catch (SQLiteException ex) {
            Log.d(DB, ex.toString());
        }*/

    }


    /*private static class TerremotosOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "terremotos.db";
        private static final String DATABASE_TABLE = "TERREMOTOS";
        private static final int DATABASE_VERSION = 1;


        private static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE + "(_id TEXT PRIMARY KEY, place TEXT, magnitude REAL, lat REAL, lon REAL, depth REAL, url TEXT, time INTEGER)";

        public TerremotosOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP IF EXIST " + DATABASE_NAME);
        }
    }*/
}
