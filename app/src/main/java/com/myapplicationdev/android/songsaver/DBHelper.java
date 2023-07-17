package com.myapplicationdev.android.songsaver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Nuur Aisyah Binte Farouk on 3/7/2023.
 * C346-1D-E63A-A
 */
public class DBHelper extends SQLiteOpenHelper{
    // Start version with 1
    // increment by 1 whenever db schema changes.
    private static final int DATABASE_VER = 1;
    // Filename of the database
    private static final String DATABASE_NAME = "song.db";


    public static final String TABLE_SONGS = "song";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_SINGERS = "singers";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_STARS = "stars";



    public ArrayList<Song> getSongs() {
        ArrayList<Song> songs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGERS,COLUMN_YEAR,COLUMN_STARS};
        Cursor cursor = db.query(TABLE_SONGS, columns, null, null, null, null, COLUMN_TITLE + " asc", null);

        if (cursor.moveToFirst()) {
            Log.i("cursor", "moveToFirst() false");
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song obj = new Song(id,title,singers, year, stars);
                songs.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public ArrayList<Song> getSongs(int rating) {
        ArrayList<Song> songs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGERS,COLUMN_YEAR,COLUMN_STARS};
        String condition = COLUMN_STARS + " Like ?";
        String[] args = {"%" + rating + "%"};

        Cursor cursor = db.query(TABLE_SONGS, columns, condition, args, null, null, COLUMN_TITLE + " asc", null);

        if (cursor.moveToFirst()) {
            Log.i("cursor", "moveToFirst() false");
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song obj = new Song(id,title,singers, year, stars);
                Log.i("getSongs()", String.format("id: %d, title: %s, singers: %s, year: %d, stars: %d", id,title,singers,year,stars));

                songs.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_SONGS +  "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_SINGERS + " TEXT,"
                + COLUMN_YEAR + " INTEGER,"
                + COLUMN_STARS + " INTEGER)";
        db.execSQL(createTableSql);
        Log.i("info", "created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
        // Create table(s) again
        onCreate(db);

    }

    public void insertSong(String title, String singers, int year, int stars){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGERS,COLUMN_YEAR,COLUMN_STARS};
        Cursor cursor = db.query(TABLE_SONGS, columns, null, null, null, null, COLUMN_TITLE + " asc", null);

        cursor.moveToLast();
        ContentValues values = new ContentValues();
        // Store the column name as key and the description as value
        values.put(COLUMN_TITLE, title);
        // Store the column name as key and the date as value
        values.put(COLUMN_SINGERS, singers);
        // Insert the row into the TABLE_TASK
        values.put(COLUMN_YEAR, year);
        // Insert the row into the TABLE_TASK
        values.put(COLUMN_STARS, stars);
        // Insert the row into the TABLE_TASK
        db.insert(TABLE_SONGS, null, values);
        // Close the database connection
        Log.i("info", "Song inserted");
        db.close();
        // Get an instance of the database for writing
        // We use ContentValues object to store the values for
        //  the db operation
        cursor.close();
        db.close();

    }

    public int updateSong(Song data, String title, String singers, int year, int stars) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        // Store the column name as key and the date as value
        values.put(COLUMN_SINGERS, singers);
        // Insert the row into the TABLE_TASK
        values.put(COLUMN_YEAR, year);
        // Insert the row into the TABLE_TASK
        values.put(COLUMN_STARS, stars);
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        return db.update(TABLE_SONGS, values, condition, args);
    }

    public int deleteSong(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        return db.delete(TABLE_SONGS, condition, args);
    }
}
