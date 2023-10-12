package com.example.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME = "notes.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "notes";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_NOTE = "note";

    public DbHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("aaa", "onCreate");
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_SUBJECT + " TEXT PRIMARY KEY, " +
                        COLUMN_NOTE + " TEXT);";
        db.execSQL(query);

        for (String subject: Storage.Companion.getSubjects()) {
            query = "INSERT INTO " + TABLE_NAME + " VALUES ('" + subject + "', " + "''" + ");";
            db.execSQL(query);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
