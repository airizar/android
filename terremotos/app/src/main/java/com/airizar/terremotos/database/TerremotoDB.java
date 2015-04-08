package com.airizar.terremotos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Amaia on 30/03/2015.
 */
public class TerremotoDB {

    private final SQLiteDatabase db;
    private TerremotoOpenHelper helper;

    public TerremotoDB(Context context) {

        this.helper = new TerremotoOpenHelper(context, TerremotoOpenHelper.DATABASE_NAME, null, TerremotoOpenHelper.DATABASE_VERSION);
        this.db = helper.getWritableDatabase();
    }

    private static class TerremotoOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "earthQuakes.db";
        private static final String DATABASE_TABLE = "EARTHQUAKES";
        private static final int DATABASE_VERSION = 1;


        //private  static  final String DATABASE_CREATE= "CREATE TABLE" + DATABASE_TABLE + "_id PRIMARY KEY, place TEXT, magnitude REAL, lat REAL,long REAL, depth REAL, url TEXT, time INTEGER";
        private static final String DATABASE_CREATE = "CREATE Table " + DATABASE_TABLE +
                "(_id  TEXT PRIMARY KEY, place TEXT, magnitude REAL, lat REAL , Long REAL, url TEXT,depth REAL, time INTEGER)";


        public TerremotoOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}