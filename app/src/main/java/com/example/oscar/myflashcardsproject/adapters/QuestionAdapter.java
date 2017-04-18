package com.example.oscar.myflashcardsproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oscar.myflashcardsproject.R;
import com.example.oscar.myflashcardsproject.activities.AnswerActivity;
import com.example.oscar.myflashcardsproject.models.Question;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oscar on 10/04/2017.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    List<Question> questions;
    Context context;

    public QuestionAdapter(List<Question> questions, Context context) {
        this.questions = questions;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Question question = questions.get(position);

        holder.tvQuestion.setText(question.getQuestion());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tvQuestion)
        TextView tvQuestion;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Question question =  questions.get(getAdapterPosition());

            Intent intent = new Intent(context, AnswerActivity.class);
            intent.putExtra("QUESTION", question);
            context.startActivity(intent);
        }
    }

}
