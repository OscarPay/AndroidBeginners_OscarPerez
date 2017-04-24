package com.example.oscar.myflashcardsproject.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oscar.myflashcardsproject.R;
import com.example.oscar.myflashcardsproject.adapters.QuestionAdapter;
import com.example.oscar.myflashcardsproject.adapters.QuestionCursorAdapter;
import com.example.oscar.myflashcardsproject.data.FlashCardsContract.*;
import com.example.oscar.myflashcardsproject.models.Question;
import com.example.oscar.myflashcardsproject.tasks.InitDataTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oscar on 10/04/2017.
 */

public class QuestionsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = QuestionsFragment.class.getSimpleName();

    @BindView(R.id.rvQuestions)
    RecyclerView rvQuestions;

    private List<Question> questions;
    private QuestionCursorAdapter cursorAdapter;

    public QuestionsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questions, container, false);
        ButterKnife.bind(this, view);

        getActivity().getSupportLoaderManager().initLoader(0, null, this);
        //initializeData();

        rvQuestions.setHasFixedSize(true);
        rvQuestions.setLayoutManager(new LinearLayoutManager(this.getContext()));
        //rvQuestions.setAdapter(new QuestionAdapter(questions, this.getContext()));
        cursorAdapter = new QuestionCursorAdapter(null, this.getContext());
        rvQuestions.setAdapter(cursorAdapter);

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), QuestionEntry.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.i(TAG, "Number of questions: " + data.getCount());

        if (data.getCount() == 0) {
            new InitDataTask(getActivity()).execute();
        } else {
            cursorAdapter.swapCursor(data);
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }

    private void initializeData() {
        questions = new ArrayList<>();

        questions.add(new Question("1", "What?", "That", "Default"));
        questions.add(new Question("2", "Where?", "Here", "Default"));
        questions.add(new Question("3", "When?", "Now", "Default"));
        questions.add(new Question("4", "Why?", "Because yes", "Default"));
        questions.add(new Question("5", "Who?", "Me", "Default"));
    }
}
