package com.example.oscar.myflashcardsproject.models;

import java.io.Serializable;

/**
 * Created by Oscar on 10/04/2017.
 */

public class Question implements Serializable {

    String id;
    String question;
    String answer;
    String subject;

    public Question(String id, String question, String answer, String subject) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.subject = subject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String toString(){
        return this.question + " " + this.answer  + "" + this.subject;
    }
}
