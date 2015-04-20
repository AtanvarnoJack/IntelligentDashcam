package com.idashcam.intelligentdashcam.Stockage.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.idashcam.intelligentdashcam.Analitycs.Map.MapConfiguration;
import com.idashcam.intelligentdashcam.Analitycs.Video.VideoConfiguration;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by alexandre on 16/04/2015.
 */
public class SQLiteDatabaseHandlerMap extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mapConfDB";
    private static final String TABLE_NAME = "MapConfTable";
    private static final String KEY_ID = "id";
    private static final String KEY_MAP_TYPE = "mapType";
    private static final String KEY_MAP_VISIBILITY = "mapVisibility";
    private static final String KEY_INTERVAL = "interval";
    private static final String KEY_FAST_INTERVAL = "fastInterval";
    private static final String KEY_ACCURACY = "accuracy";
    private static final String KEY_ZOOM = "zoom";
    private static final String KEY_TRANSPARENCY = "transparency";  // Boolean store like 0 or 1 value
    private static final String[] COLONNES = {KEY_ID, KEY_MAP_TYPE, KEY_MAP_VISIBILITY, KEY_INTERVAL,KEY_FAST_INTERVAL,KEY_ACCURACY,KEY_ZOOM,KEY_TRANSPARENCY};

    public SQLiteDatabaseHandlerMap(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("SQLite DB Constructeur:", "Constructeur");
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {

        String CREATION_TABLE_EXEMPLE = "CREATE TABLE " + TABLE_NAME + " ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_MAP_TYPE + " TEXT, "
                + KEY_MAP_VISIBILITY + " TEXT, "
                + KEY_INTERVAL + " INTEGER, "
                + KEY_FAST_INTERVAL + " INTEGER, "
                + KEY_ACCURACY + " TEXT, "
                + KEY_ZOOM + " INTEGER, "
                + KEY_TRANSPARENCY + " INTEGER)";

        arg0.execSQL(CREATION_TABLE_EXEMPLE);
        Log.i("SQLite DB", "Creation");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(arg0);
        Log.i("SQLite DB", "Upgrade");
    }

    public void deleteOne(MapConfiguration mapConfiguration) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, // table
                "id = ?", new String[]{String.valueOf(mapConfiguration.getId())});
        db.close();
        Log.i("SQLite DB : Delete : ", mapConfiguration.toString());
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"",null);
        db.close();
        Log.i("SQLite DB : AllDelete:", "All Delete!");
    }

    public MapConfiguration showOne(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLONNES, // b. column names
                " id = ?", // c. selections
                new String[]{String.valueOf(id)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor != null)
            cursor.moveToFirst();
        MapConfiguration mapConfiguration = new MapConfiguration();
        mapConfiguration.setId(Integer.parseInt(cursor.getString(0)));
        mapConfiguration.setMapType(cursor.getString(1));
        mapConfiguration.setMapVisibility(cursor.getString(2));
        mapConfiguration.setInterval(Integer.parseInt(cursor.getString(3)));
        mapConfiguration.setFastInterval(Integer.parseInt(cursor.getString(4)));
        mapConfiguration.setAccuracy(cursor.getString(5));
        mapConfiguration.setZoom(Integer.parseInt(cursor.getString(6)));

        int transparencyFound = Integer.parseInt(cursor.getString(7));
        if (transparencyFound == 0){
            mapConfiguration.setTransparency(false);
        }else{
            mapConfiguration.setTransparency(true);
        }
        Log.i("SQLite DB : Show one : id= " + id, mapConfiguration.toString());
        return mapConfiguration;
    }

    public List<MapConfiguration> showAll() {
        List<MapConfiguration> mapConfigurationLinkedList = new LinkedList<MapConfiguration>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        MapConfiguration mapConfiguration = null;
        if (cursor.moveToFirst()) {
            do {
                mapConfiguration = new MapConfiguration();
                mapConfiguration.setId(Integer.parseInt(cursor.getString(0)));
                mapConfiguration.setMapType(cursor.getString(1));
                mapConfiguration.setMapVisibility(cursor.getString(2));
                mapConfiguration.setInterval(Integer.parseInt(cursor.getString(3)));
                mapConfiguration.setFastInterval(Integer.parseInt(cursor.getString(4)));
                mapConfiguration.setAccuracy(cursor.getString(5));
                mapConfiguration.setZoom(Integer.parseInt(cursor.getString(6)));

                int transparencyFound = Integer.parseInt(cursor.getString(7));
                if (transparencyFound == 0){
                    mapConfiguration.setTransparency(false);
                }else{
                    mapConfiguration.setTransparency(true);
                }
                mapConfigurationLinkedList.add(mapConfiguration);
            } while (cursor.moveToNext());
        }
        Log.i("SQLite DB : Show All : ", mapConfigurationLinkedList.toString());
        return mapConfigurationLinkedList;
    }

    public void addOne(MapConfiguration mapConfiguration) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MAP_TYPE, mapConfiguration.getMapTypeString());
        values.put(KEY_MAP_VISIBILITY, mapConfiguration.getMapVisibilityString());
        values.put(KEY_INTERVAL,mapConfiguration.getInterval());
        values.put(KEY_FAST_INTERVAL,mapConfiguration.getFastInterval());
        values.put(KEY_ACCURACY,mapConfiguration.getAccuracyString());
        values.put(KEY_ZOOM,mapConfiguration.getZoom());
        int transparency;
        if (mapConfiguration.isTransparency()){
            transparency = 1;
        }else{
            transparency = 0;
        }
        values.put(KEY_TRANSPARENCY,transparency);
// insertion
        db.insert(TABLE_NAME, // table
                null, values);

        db.close();
        Log.i("SQLite DB : Add one : id= " + mapConfiguration.getId(), mapConfiguration.toString());
    }

    public int updateOne(MapConfiguration mapConfiguration) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_MAP_TYPE, mapConfiguration.getMapTypeString());
        values.put(KEY_MAP_VISIBILITY, mapConfiguration.getMapVisibilityString());
        values.put(KEY_INTERVAL,mapConfiguration.getInterval());
        values.put(KEY_FAST_INTERVAL,mapConfiguration.getFastInterval());
        values.put(KEY_ACCURACY,mapConfiguration.getAccuracyString());
        values.put(KEY_ZOOM,mapConfiguration.getZoom());
        int transparency;
        if (mapConfiguration.isTransparency()){
            transparency = 1;
        }else{
            transparency = 0;
        }
        values.put(KEY_TRANSPARENCY,transparency);

        int i = db.update(TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[]{String.valueOf(mapConfiguration.getId())});

        db.close();
        Log.i("SQLite DB : Update one : id= " + mapConfiguration.getId(), mapConfiguration.toString());

        return i;
    }
}
