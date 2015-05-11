package com.jianyan.android.questionset;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestionFragment extends ListFragment {
    private static final String TAG = QuestionFragment.class.getSimpleName();

//    public static final String VIEW_PAGER = "questionset.VIEW_PAGER";
//    public static final String QUESTION_ID = "questionset.QUESTION_ID";

    private ViewPager mViewPager;
    private int mQuestionListSize;
    private int mQuestionId;
    private Question mQuestion;

    public static QuestionFragment newInstance(ViewPager viewPager, int questionId) {
        QuestionFragment fragment = new QuestionFragment();
        fragment.mViewPager = viewPager;
        fragment.mQuestionId = questionId;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mQuestionListSize = QuestionSetManager.getInstance().getQuestionList().size();
        mQuestion = QuestionSetManager.getInstance().getQuestion(mQuestionId);

        ArrayList<String> titleAndOptions = new ArrayList<String>();
        titleAndOptions.add("" + (mQuestionId + 1) + ". " + mQuestion.getTitle());
        ArrayList<String> options = mQuestion.getOptions();
        for (int i = 0; i < options.size(); ++i) {
            titleAndOptions.add(Character.toString((char) ('A' + i)) + ". " + options.get(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.array_item, titleAndOptions);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i(TAG, "position: " + position);
        if (mQuestionId + 1 == mQuestionListSize) {
            Toast.makeText(getActivity(), "测试结束", Toast.LENGTH_LONG).show();
        } else {
            mViewPager.setCurrentItem(mQuestionId + 1);
        }
    }
}
