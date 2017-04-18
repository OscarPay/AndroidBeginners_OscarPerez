package com.example.oscar.myflashcardsproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oscar.myflashcardsproject.R;
import com.example.oscar.myflashcardsproject.activities.AnswerActivity;
import com.example.oscar.myflashcardsproject.data.FlashCardsContract.*;
import com.example.oscar.myflashcardsproject.models.Question;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oscar on 15/04/2017.
 */

public class QuestionCursorAdapter extends RecyclerView.Adapter<QuestionCursorAdapter.ViewHolder> {

    private Cursor cursor;
    private Context context;

    public QuestionCursorAdapter(Cursor cursor, Context context) {
        this.cursor = cursor;
        this.context = context;
    }

    public void swapCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.bindView(cursor);
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tvQuestion)
        TextView tvQuestion;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bindView(Cursor cursor) {
            String question = cursor.getString(cursor.getColumnIndex(QuestionEntry.COLUMN_QUESTION));
            tvQuestion.setText(question);
        }

        @Override
        public void onClick(View v) {
            cursor.moveToPosition(getAdapterPosition());

            Question question = new Question(
                    String.valueOf(cursor.getInt(cursor.getColumnIndex(QuestionEntry._ID))),
                    cursor.getString(cursor.getColumnIndex(QuestionEntry.COLUMN_QUESTION)),
                    cursor.getString(cursor.getColumnIndex(QuestionEntry.COLUMN_ANSWER)),
                    cursor.getString(cursor.getColumnIndex(QuestionEntry.COLUMN_TYPE))
            );

            Intent intent = new Intent(context, AnswerActivity.class);
            intent.putExtra("QUESTION", question);
            context.startActivity(intent);
        }

    }
}
