package com.airizar.terremotos.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;

public class TerremotosProvider extends ContentProvider implements  {
    public static final Uri CONTENT_URI = Uri.parse("content://com.airizar.provider.terremotos/terremotos");

    private static final int ALLROWS = 1;
    private static final int SINGLE_ROW = 2;

    private static final UriMatcher uriMatcher;

    public static class Columns implements BaseColumns {

        //acceso Columns.ID,....
        //public static final String ID = "_id";
        public static final String PLACE = "place";
        public static final String MAGNITUDE = "magnitude";
        public static final String LAT = "lat";
        public static final String LON = "lon";
        public static final String DEPTH = "depth";
        public static final String URL_TERREMOTOS = "url";
        public static final String TIME = "time";
    }

//Populate	the	UriMatcher	object,	where	a	URI	ending	in
//'elements'	will	correspond	to	a	request	for	all	items,
//and	'elements/[rowID]'	represents	a	single	row.

    static {

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.airizar.provider.terremotos/", "terremotos", ALLROWS);
        uriMatcher.addURI("com.airizar.provider.terremotos/", "terremotos/#", SINGLE_ROW);

    }

    private TerremotoOpenHelper terremotoOpenHelper;

    public TerremotosProvider() {
    }

    @Override
    public boolean onCreate() {
        terremotoOpenHelper = new TerremotoOpenHelper(getContext(), TerremotoOpenHelper.DATABASE_NAME, null, TerremotoOpenHelper.DATABASE_VERSION);
        return true;
    }

    @Override
    public String getType(Uri uri) {
        //	Return	a	string	that	identifies	the	MIME	type
     //	for	a	Content	Provider	URI
       switch	(uriMatcher.match(uri))	{
            case	ALLROWS:
                return	"vnd.android.cursor.dir/vnd.airizar.provider.terremotos";
            case	SINGLE_ROW:
                return	"vnd.android.cursor.item/vnd.airizar.provider.terremotos";
            default:
                throw	new	IllegalArgumentException("Unsupported	URI:	"	+	uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db=terremotoOpenHelper.getWritableDatabase();
        //En caso de haber mas de una tabla switch para saber donde meter;
        /*switch (uriMatcher.match(uri)) {
            case SINGLE_ROW:
                String table=queryBuilder.setTables(TerremotoOpenHelper.DATABASE_TABLE);
            default:
                break;
        }*/
        long id=db.insert(TerremotoOpenHelper.DATABASE_TABLE,null,values);
        //devolver uri de acceso al elemento insertado
        if(id>-1){
            Uri insertedId= ContentUris.withAppendedId(CONTENT_URI,id);
            //lo que hace que se actualice al instante la aplicación, notifica a todos que se ha insertado
            //un dato nuevo
            getContext().getContentResolver().notifyChange(insertedId,null);
            return insertedId;
        }else{
            return null;
        }

    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db;
        try {
            db = terremotoOpenHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = terremotoOpenHelper.getReadableDatabase();
        }
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (uriMatcher.match(uri)) {
            case SINGLE_ROW:
                //get(0):dominio, get(1) lo que esta despues de la barra en nuestro caso terremoto
                String rowID = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(Columns._ID + " = ?");
                selectionArgs = new String[]{rowID};
                //Si hubiera mas de una tabla
                //queryBuilder.setTables(TerremotoOpenHelper.DATABASE_TABLE);

            default:
                break;
        }
        queryBuilder.setTables(TerremotoOpenHelper.DATABASE_TABLE);
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static class TerremotoOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "earthQuakes.db";
        private static final String DATABASE_TABLE = "EARTHQUAKES";
        private static final int DATABASE_VERSION = 1;


        //private  static  final String DATABASE_CREATE= "CREATE TABLE" + DATABASE_TABLE + "_id PRIMARY KEY, place TEXT, magnitude REAL, lat REAL,long REAL, depth REAL, url TEXT, time INTEGER";
        private static final String DATABASE_CREATE = "CREATE Table " + DATABASE_TABLE +
                "(_id  TEXT PRIMARY KEY, place TEXT, magnitude REAL, lat REAL , lon REAL, url TEXT,depth REAL, time INTEGER)";


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
