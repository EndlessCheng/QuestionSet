package com.jianyan.android.questionset;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.ArrayList;

public class QuestionPagerActivity extends FragmentActivity{
    private static final String TAG = QuestionPagerActivity.class.getSimpleName();

    private ArrayList<Question> mQuestionList;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mQuestionList = QuestionSetManager.getInstance().getQuestionList();

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int i) {
                return QuestionFragment.newInstance(mViewPager, i);
            }

            @Override
            public int getCount() {
                return mQuestionList.size();
            }
        });

        mViewPager.setCurrentItem(0);
    }
}
