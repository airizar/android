package com.airizar.terremotos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.airizar.terremotos.model.Coordenada;
import com.airizar.terremotos.model.Terremoto;

import java.lang.reflect.Array;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cursomovil on 27/03/15.
 */
public class TerremotosDB {
    //Seran publicas para que desde fuera no tengan que saber los nombres de las columnas
    public static final String ID = "_id";
    public static final String PLACE = "place";
    public static final String MAGNITUDE = "magnitude";
    public static final String LAT = "lat";
    public static final String LON = "lon";
    public static final String DEPTH = "depth";
    public static final String URL_TERREMOTOS = "url";
    public static final String TIME = "time";
    public static final String[] ALL_COLLUMNS={ID,PLACE,MAGNITUDE,LAT,LON,DEPTH,URL_TERREMOTOS,TIME};
    private static final String DB = "DB";
    private SQLiteDatabase db;
    private TerremotosOpenHelper helper;

    public TerremotosDB(Context context) {
        helper = new TerremotosOpenHelper(context, TerremotosOpenHelper.DATABASE_NAME, null, TerremotosOpenHelper.DATABASE_VERSION);
        this.db = helper.getWritableDatabase();
    }
    //Polimorfismo, al utilizar list despues puedo cambiar el resultado a cualquier clase que
    // implemente a list sin tener ningun problema
    public List<Terremoto> query(String where,String[] whereArgs) {
   // public ArrayList<Terremoto> query(String where,String[] whereArgs) {
        //String magnitudString = String.valueOf(magnitud);

        String groupBy = null;
        String having = null;
        String order = TIME+ " DESC";
        Cursor cursor = db.query(TerremotosOpenHelper.DATABASE_TABLE, ALL_COLLUMNS, where, whereArgs, groupBy, having, order);
        ArrayList<Terremoto> terremotos = new ArrayList<>();
        //HashMap para obtener los indices de las columnas
        HashMap<String,Integer> indexes=new HashMap<>();
        for (int i = 0; i < ALL_COLLUMNS.length ; i++) {
            indexes.put(ALL_COLLUMNS[i],cursor.getColumnIndex(ALL_COLLUMNS[i]));
        }

        while (cursor.moveToNext()) {
            Terremoto terremoto = new Terremoto();
            terremoto.set_id(cursor.getString(indexes.get(ID)));
            terremoto.setLugar(cursor.getString(indexes.get(PLACE)));
            terremoto.setMagnitud(cursor.getDouble(indexes.get(MAGNITUDE)));
            terremoto.setCoord(new Coordenada(cursor.getDouble(indexes.get(LAT)), cursor.getDouble(indexes.get(LON)), cursor.getDouble(indexes.get(DEPTH))));
            terremoto.setUrl(cursor.getString(indexes.get(URL_TERREMOTOS)));
            terremoto.setTime(cursor.getLong(indexes.get(TIME)));
            terremotos.add(terremoto);
        }
        cursor.close();
        return terremotos;

    }
    public List<Terremoto> getAll(){
        return query(null,null);
    }
    public List<Terremoto> getAllByMagnitude(int magnitude){
        String where=MAGNITUDE+ " >=?";
        String[] whereArgs={String.valueOf(magnitude)};
        return query(where,whereArgs);
    }

    public void annadirTerremoto(Terremoto terremoto) {
        ContentValues newValues = new ContentValues();
        newValues.put(ID, terremoto.get_id());
        newValues.put(PLACE, terremoto.getLugar());
        newValues.put(MAGNITUDE, terremoto.getMagnitud());
        newValues.put(LAT, terremoto.getCoord().getLat());
        newValues.put(LON, terremoto.getCoord().getLon());
        newValues.put(DEPTH, terremoto.getCoord().getProdundidad());
        newValues.put(URL_TERREMOTOS, terremoto.getUrl());
        newValues.put(TIME, terremoto.getTime().getTime());
        try {
            db.insertOrThrow(TerremotosOpenHelper.DATABASE_TABLE, null, newValues);
        }catch (SQLiteException ex){
            Log.d(DB,ex.toString());
        }
    }




    private static class TerremotosOpenHelper extends SQLiteOpenHelper {
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
    }
}
