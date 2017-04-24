package com.example.oscar.myflashcardsproject.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.oscar.myflashcardsproject.data.FlashCardsContract.*;
import com.example.oscar.myflashcardsproject.dialogs.AddQuestionDialogFragment;

/**
 * Created by Oscar on 13/04/2017.
 */

public class FlashCardsService extends IntentService {

    private static final String TAG = FlashCardsService.class.getSimpleName();

    public static final String KEY_QUESTION = "EXTRA_QUESTION";
    public static final String KEY_ANSWER = "EXTRA_ANSWER";
    public static final String KEY_TYPE = "EXTRA_TYPE";

    public FlashCardsService() {
        super("FlashCardsService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String question = intent.getStringExtra(KEY_QUESTION);
        String answer = intent.getStringExtra(KEY_ANSWER);
        String type = intent.getStringExtra(KEY_TYPE);

        Log.i(TAG, question + " " + answer + " " + type);

        ContentValues questionValues = new ContentValues();
        questionValues.put(QuestionEntry.COLUMN_QUESTION, question);
        questionValues.put(QuestionEntry.COLUMN_ANSWER, answer);
        questionValues.put(QuestionEntry.COLUMN_TYPE, type);

        getContentResolver().insert(QuestionEntry.CONTENT_URI, questionValues);
    }
}
