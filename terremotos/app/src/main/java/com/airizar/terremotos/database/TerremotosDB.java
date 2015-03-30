package com.airizar.terremotos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.airizar.terremotos.model.Coordenada;
import com.airizar.terremotos.model.Terremoto;

import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by cursomovil on 27/03/15.
 */
public class TerremotosDB {
    private static final String ID = "_id";
    private static final String PLACE = "place";
    private static final String MAGNITUDE = "magnitude";
    private static final String LAT = "lat";
    private static final String LON = "lon";
    private static final String DEPTH = "depth";
    private static final String URL_TERREMOTOS = "url";
    private static final String TIME = "time";
    private static final String DB = "DB";
    private SQLiteDatabase db;
    private TerremotosOpenHelper helper;

    public TerremotosDB(Context context) {
        helper = new TerremotosOpenHelper(context, TerremotosOpenHelper.DATABASE_NAME, null, TerremotosOpenHelper.DATABASE_VERSION);
        this.db = helper.getWritableDatabase();
    }

    public ArrayList<Terremoto> getTerremotos(double magnitud) {
        String magnitudString=String.valueOf(magnitud);
        String[] result_columns = new String[]{"_id", "place", "magnitude", "lat", "lon", "depth", "url", "time"};
        String where=MAGNITUDE+">?";
        String whereArgs[] = {magnitudString};
        String groupBy = null;
        String having = null;
        String order = null;
        Cursor cursor=db.query(TerremotosOpenHelper.DATABASE_TABLE,result_columns,where,whereArgs,groupBy,having,order);
        ArrayList<Terremoto> terremotos=new ArrayList<>();

        //crear funcion para obtener los indices
        int	ID_INDEX	= cursor.getColumnIndexOrThrow(ID);
        int	PLACE_INDEX	= cursor.getColumnIndexOrThrow(PLACE);
        int	MAGNITUDE_INDEX	= cursor.getColumnIndexOrThrow(MAGNITUDE);
        int	LAT_INDEX	= cursor.getColumnIndexOrThrow(LAT);
        int	LON_INDEX	= cursor.getColumnIndexOrThrow(LON);
        int	DEPTH_INDEX	= cursor.getColumnIndexOrThrow(DEPTH);
        int	URL_INDEX	= cursor.getColumnIndexOrThrow(URL_TERREMOTOS);
        int	TIME_INDEX	= cursor.getColumnIndexOrThrow(TIME);

        while(cursor.moveToNext()){
            Terremoto terremoto=new Terremoto();
            terremoto.set_id(cursor.getString(ID_INDEX));
            terremoto.setLugar(cursor.getString(PLACE_INDEX));
            terremoto.setMagnitud(cursor.getDouble(MAGNITUDE_INDEX));
            terremoto.setCoord(new Coordenada(cursor.getDouble(LAT_INDEX),cursor.getDouble(LON_INDEX),cursor.getDouble(DEPTH_INDEX)));
            terremoto.setUrl(cursor.getString(URL_INDEX));
            terremoto.setTime(cursor.getLong(TIME_INDEX));
            terremotos.add(terremoto);
        }
        cursor.close();
        return terremotos;

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
        db.insert(TerremotosOpenHelper.DATABASE_TABLE, null, newValues);
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
