package com.example.oscar.myflashcardsproject.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.oscar.myflashcardsproject.data.FlashCardsContract.*;

/**
 * Created by Oscar on 12/04/2017.
 */

public class FlashCardsDBHelper extends SQLiteOpenHelper {

    private static FlashCardsDBHelper INSTANCE;

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "flashcards.db";

    public static synchronized FlashCardsDBHelper getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = new FlashCardsDBHelper(context.getApplicationContext());
        }
        return INSTANCE;
    }

    private FlashCardsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " + QuestionEntry.TABLE_NAME +
                "(" +
                QuestionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                QuestionEntry.COLUMN_QUESTION + " TEXT NOT NULL, " +
                QuestionEntry.COLUMN_ANSWER + " TEXT NOT NULL, " +
                QuestionEntry.COLUMN_TYPE + " TEXT " +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + QuestionEntry.TABLE_NAME);
            onCreate(db);
        }
    }
}
