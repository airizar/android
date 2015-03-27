package com.airizar.terremotos.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.airizar.terremotos.model.Terremoto;

/**
 * Created by cursomovil on 27/03/15.
 */
public class TerremotosDB {
    private SQLiteDatabase db;
    private TerremotosOpenHelper helper;

    public TerremotosDB(Context context){
        helper=new TerremotosOpenHelper(context,TerremotosOpenHelper.DATABASE_NAME,null,TerremotosOpenHelper.DATABASE_VERSION);
        this.db=helper.getWritableDatabase();
    }
    private static class TerremotosOpenHelper extends SQLiteOpenHelper{
        private static final String DATABASE_NAME="terremotos.db";
        private static final String DATABASE_TABLE="TERREMOTOS";
        private static final int DATABASE_VERSION=1;


        private static final String DATABASE_CREATE="CREATE TABLE "+DATABASE_TABLE+"(_id TEXT PRIMARY KEY, place TEXT, magnitude REAL, lat REAL, lon REAL, depth REAL, url TEXT, time INTEGER)";

        public TerremotosOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP IF EXIST "+ DATABASE_NAME);
        }
    }
}
