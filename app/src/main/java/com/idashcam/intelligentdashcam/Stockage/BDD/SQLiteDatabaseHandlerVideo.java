package com.idashcam.intelligentdashcam.Stockage.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.idashcam.intelligentdashcam.Analitycs.Video.VideoConfiguration;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by alexandre on 11/03/2015.
 */
public class SQLiteDatabaseHandlerVideo extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "videoConfDB";
    private static final String TABLE_NAME = "VideoConfTable";
    private static final String KEY_ID = "id";
    private static final String KEY_MAX_DURATION_TIME_VIDEO = "durationMax";
    private static final String KEY_MAX_LENGHT_IN_MO_VIDEO = "lenghtMax";
    private static final String KEY_QUALITY = "qualityVideo";
    private static final String[] COLONNES = {KEY_ID, KEY_MAX_DURATION_TIME_VIDEO, KEY_MAX_LENGHT_IN_MO_VIDEO, KEY_QUALITY};

    public SQLiteDatabaseHandlerVideo(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("SQLite DB Constructeur:", "Constructeur");
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {

        String CREATION_TABLE_EXEMPLE = "CREATE TABLE " + TABLE_NAME + " ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_MAX_DURATION_TIME_VIDEO + " INTEGER, "
                + KEY_MAX_LENGHT_IN_MO_VIDEO + " INTEGER, "
                + KEY_QUALITY + " INTEGER)";

        arg0.execSQL(CREATION_TABLE_EXEMPLE);
        Log.i("SQLite DB", "Creation");

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(arg0);
        Log.i("SQLite DB", "Upgrade");
    }

    public void deleteOne(VideoConfiguration videoConfiguration) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, // table
                "id = ?", new String[]{String.valueOf(videoConfiguration.getId())});
        db.close();
        Log.i("SQLite DB : Delete : ", videoConfiguration.toString());
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"",null);
        db.close();
        Log.i("SQLite DB : AllDelete:", "All Delete!");
    }

    public VideoConfiguration showOne(int id) {
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
        VideoConfiguration videoConfiguration = new VideoConfiguration();
        videoConfiguration.setId(Integer.parseInt(cursor.getString(0)));
        videoConfiguration.setMaxDurationVideo(Integer.parseInt(cursor.getString(1)));
        videoConfiguration.setMaxLenghtVideoInMo(Integer.parseInt(cursor.getString(2)));
        videoConfiguration.setQualityVideo(Integer.parseInt(cursor.getString(3)));
        Log.i("SQLite DB : Show one : id= " + id, videoConfiguration.toString());
        return videoConfiguration;
    }

    public List<VideoConfiguration> showAll() {
        List<VideoConfiguration> videoConfigurationLinkedList = new LinkedList<VideoConfiguration>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        VideoConfiguration videoConfiguration = null;
        if (cursor.moveToFirst()) {
            do {
                videoConfiguration = new VideoConfiguration();
                videoConfiguration.setId(Integer.parseInt(cursor.getString(0)));
                videoConfiguration.setMaxDurationVideo(Integer.parseInt(cursor.getString(1)));
                videoConfiguration.setMaxLenghtVideoInMo(Integer.parseInt(cursor.getString(2)));
                videoConfiguration.setQualityVideo(Integer.parseInt(cursor.getString(3)));
                videoConfigurationLinkedList.add(videoConfiguration);
            } while (cursor.moveToNext());
        }
        Log.i("SQLite DB : Show All : ", videoConfigurationLinkedList.toString());
        return videoConfigurationLinkedList;
    }

    public void addOne(VideoConfiguration videoConfiguration) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MAX_DURATION_TIME_VIDEO, videoConfiguration.getMaxDurationVideo());
        values.put(KEY_MAX_LENGHT_IN_MO_VIDEO, videoConfiguration.getMaxLenghtVideoInMo());
        values.put(KEY_QUALITY,videoConfiguration.getQualityVideo());
// insertion
        db.insert(TABLE_NAME, // table
                null, values);

        db.close();
        Log.i("SQLite DB : Add one : id= " + videoConfiguration.getId(), videoConfiguration.toString());
    }

    public int updateOne(VideoConfiguration videoConfiguration) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MAX_DURATION_TIME_VIDEO, videoConfiguration.getMaxDurationVideo());
        values.put(KEY_MAX_LENGHT_IN_MO_VIDEO, videoConfiguration.getMaxLenghtVideoInMo());
        values.put(KEY_QUALITY,videoConfiguration.getQualityVideo());

        int i = db.update(TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[]{String.valueOf(videoConfiguration.getId())});

        db.close();
        Log.i("SQLite DB : Update one : id= " + videoConfiguration.getId(), videoConfiguration.toString());

        return i;
    }
}
