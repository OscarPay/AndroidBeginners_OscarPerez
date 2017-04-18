package com.example.oscar.myflashcardsproject.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.oscar.myflashcardsproject.data.FlashCardsContract.*;

/**
 * Created by Oscar on 12/04/2017.
 */

public class FlashCardsProvider extends ContentProvider {

    //Codes for the uri matcher
    private static final int FLASH_CARDS = 100;
    private static final int FLASH_CARDS_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private FlashCardsDBHelper flashCardsDBHelper;

    @Override
    public boolean onCreate() {
        flashCardsDBHelper = FlashCardsDBHelper.getInstance(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case FLASH_CARDS: {
                cursor = flashCardsDBHelper.getReadableDatabase().query(
                        QuestionEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case FLASH_CARDS_ID: {
                cursor = flashCardsDBHelper.getReadableDatabase().query(
                        QuestionEntry.TABLE_NAME,
                        projection,
                        QuestionEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = flashCardsDBHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch (match) {
            case FLASH_CARDS: {
                long id = db.insert(QuestionEntry.TABLE_NAME, null, values);

                if (id > 0) {
                    returnUri = QuestionEntry.CONTENT_URI;
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }

                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknow uri: " + uri);
            }
        }

        getContext().getContentResolver().notifyChange(uri, null); // notifies content resolver
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = flashCardsDBHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case FLASH_CARDS: {
                rowsDeleted = db.delete(QuestionEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknow uri: " + uri);
            }
        }

        //Because a null deletes all rows
        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null); // notifies content resolver
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = flashCardsDBHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case FLASH_CARDS: {
                rowsUpdated = db.update(QuestionEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknow uri: " + uri);
            }
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null); // notifies content resolver
        }

        return rowsUpdated;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        SQLiteDatabase db = flashCardsDBHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        switch (match){
            case FLASH_CARDS: {
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values){
                        long id = db.insert(QuestionEntry.TABLE_NAME, null, value);
                        if (id != -1){
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null); // notifies content resolver
                return returnCount;
            }
            default: {
                return super.bulkInsert(uri, values);
            }
        }

    }

    private static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FlashCardsContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, FlashCardsContract.BASE_PATH, FLASH_CARDS);
        matcher.addURI(authority, FlashCardsContract.BASE_PATH + "/#", FLASH_CARDS_ID);

        return matcher;
    }
}
