package com.example.oscar.myflashcardsproject.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.oscar.myflashcardsproject.R;
import com.example.oscar.myflashcardsproject.services.FlashCardsService;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oscar on 11/04/2017.
 */

public class AddQuestionDialogFragment extends DialogFragment {

    private static final String TAG = AddQuestionDialogFragment.class.getSimpleName();

    @BindView(R.id.tvAddQuestion)
    EditText tvAddQuestion;
    @BindView(R.id.tvAddAnswer)
    EditText tvAddAnswer;
    @BindView(R.id.tvAddType)
    EditText tvAddType;

    public static AddQuestionDialogFragment getInstance(String idQuestion) {
        Bundle bundle = new Bundle();
        bundle.putString("ID_QUESTION", idQuestion);
        AddQuestionDialogFragment fragment = new AddQuestionDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.new_question_dialog, null, false);

        builder.setView(view);
        builder.setTitle("Add a new task");
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getContext(), FlashCardsService.class);
                intent.putExtra(FlashCardsService.KEY_QUESTION, tvAddQuestion.getText().toString());
                intent.putExtra(FlashCardsService.KEY_ANSWER, tvAddAnswer.getText().toString());
                intent.putExtra(FlashCardsService.KEY_TYPE, tvAddType.getText().toString());

                Log.d(TAG, tvAddQuestion.getText() + " " + tvAddAnswer.getText() + " " + tvAddType.getText());

                getActivity().startService(intent);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        ButterKnife.bind(this, view);
        return builder.create();
    }


}
