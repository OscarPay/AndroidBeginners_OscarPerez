package com.example.oscar.myflashcardsproject.tasks;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.example.oscar.myflashcardsproject.data.FlashCardsContract.*;
import com.example.oscar.myflashcardsproject.helpers.JsonReader;
import com.example.oscar.myflashcardsproject.models.Question;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Oscar on 18/04/2017.
 */

public class InitDataTask extends AsyncTask<Void, Integer, Void> {

    private static final String TAG = InitDataTask.class.getSimpleName();

    private Context context;
    private ProgressDialog dialog;

    public InitDataTask(Context context) {
        this.context = context;
        this.dialog = new ProgressDialog(context);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.setMessage("initializing data ...");
        this.dialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {

        //Read json from Disk (assets folder)
        String dataJson = JsonReader.loadJSONFromAssets(context, "questions.json");
        Log.i(TAG, dataJson);

        //Parse json using Gson
        Gson gson = new Gson();
        Question[] questions = gson.fromJson(dataJson, Question[].class);

        ContentValues[] questionValuesArray = new ContentValues[questions.length];

        //Transform from Question to ContentValues
        for (int i = 0; i < questions.length; i++) {
            ContentValues questionValues = new ContentValues();

            questionValues.put(QuestionEntry.COLUMN_QUESTION, questions[i].getQuestion());
            questionValues.put(QuestionEntry.COLUMN_ANSWER, questions[i].getAnswer());
            questionValues.put(QuestionEntry.COLUMN_TYPE, questions[i].getSubject());
            questionValuesArray[i] = questionValues;
        }

        context.getContentResolver().bulkInsert(QuestionEntry.CONTENT_URI, questionValuesArray);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        this.dialog.dismiss();
    }
}
