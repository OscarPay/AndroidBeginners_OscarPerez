package com.example.oscar.myflashcardsproject.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.oscar.myflashcardsproject.R;
import com.example.oscar.myflashcardsproject.models.Question;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnswerActivity extends AppCompatActivity {

    @BindView(R.id.tvDetailsQuestion)
    TextView tvDetailsQuestion;
    @BindView(R.id.tvDetailsAnswer)
    TextView tvDetailsAnswer;

    Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            question = (Question) extras.getSerializable("QUESTION");
            //this.setTitle(question.getQuestion());
            tvDetailsQuestion.setText(question.getQuestion());
            tvDetailsAnswer.setText(question.getAnswer());
        }
    }
}
