package com.example.oscar.myflashcardsproject.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.oscar.myflashcardsproject.R;
import com.example.oscar.myflashcardsproject.data.FlashCardsContract.*;

/**
 * Created by Oscar on 25/04/2017.
 */

public class FlashCardsWidgetProvider extends AppWidgetProvider {

    private static final String TAG = FlashCardsWidgetProvider.class.getSimpleName();

    // our actions for our buttons
    public static String ACTION_WIDGET_NEXT_QUESTION = "NextQuestion";
    public static String ACTION_WIDGET_SEE_ANSWER = "SeeAnswer";

    private String question = "";
    private String answer = "";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        Log.i(TAG, "onUpdate()");

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Get the layout for the App Widget and attach an onclick listener to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            // Create an Intent to Next Question
            Intent intent = new Intent(context, FlashCardsWidgetProvider.class);
            intent.setAction(ACTION_WIDGET_NEXT_QUESTION);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.btnNextQuestionWidget, pendingIntent);

            // Create an Intent to See Answer
            intent = new Intent(context, FlashCardsWidgetProvider.class);
            intent.setAction(ACTION_WIDGET_SEE_ANSWER);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.btnSeeAnswerWidget, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive()");

        if (intent.getAction().equals(ACTION_WIDGET_NEXT_QUESTION)) {
            Log.i(TAG, ACTION_WIDGET_NEXT_QUESTION);
            //views.setTextViewText(R.id.tvQuestionWidget, question);
            //views.setTextViewText(R.id.tvAnswerWidget, "");
        } else if (intent.getAction().equals(ACTION_WIDGET_SEE_ANSWER)) {
            //views.setTextViewText(R.id.tvAnswerWidget, answer);
            Log.i(TAG, ACTION_WIDGET_SEE_ANSWER);
        } else {
            super.onReceive(context, intent);
        }
    }

    public void getRandomQuestion(Context context) {
        Cursor result = context.getContentResolver().query(QuestionEntry.CONTENT_URI, new String[]{QuestionEntry.COLUMN_QUESTION, QuestionEntry.COLUMN_ANSWER, QuestionEntry.COLUMN_TYPE}, null, null, "RANDOM() LIMIT 1");

        if (result.moveToFirst() == false) {
            //el cursor está vacío
            question = "No existen tarjetas";
            return;
        }

        question = result.getString(result.getColumnIndex(QuestionEntry.COLUMN_QUESTION));
        answer = result.getString(result.getColumnIndex(QuestionEntry.COLUMN_ANSWER));

        return;
    }
}
