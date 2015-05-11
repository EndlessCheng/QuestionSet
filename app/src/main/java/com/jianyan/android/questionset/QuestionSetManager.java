package com.jianyan.android.questionset;

import android.content.Context;

import java.util.ArrayList;

public class QuestionSetManager {
    private ArrayList<Question> mQuestionList;

    private static QuestionSetManager mQuestionSetManager;

    private QuestionSetManager() {
        mQuestionList = new ArrayList<Question>();
    }

    public static QuestionSetManager getInstance() {
        if (null == mQuestionSetManager) {
            mQuestionSetManager = new QuestionSetManager();
        }
        return mQuestionSetManager;
    }

    public void setQuestionList(ArrayList<Question> mQuestionList) {
        this.mQuestionList = mQuestionList;
    }

    public ArrayList<Question> getQuestionList() {
        return mQuestionList;
    }

    public Question getQuestion(int questionId) {
        return mQuestionList.get(questionId);
    }
}
