package com.example.oscar.myflashcardsproject.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Oscar on 12/04/2017.
 */

public class FlashCardsContract {

    public static final String CONTENT_AUTHORITY = "com.example.oscar.myflashcardsproject";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String BASE_PATH  = "flashcards";

    public static final class QuestionEntry implements BaseColumns{
        public static final String TABLE_NAME = "questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_TYPE = "type";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(BASE_PATH).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + BASE_PATH;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + BASE_PATH;
    }

}
