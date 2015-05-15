package com.jianyan.android.questionset;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestionFragment extends ListFragment {
    private static final String TAG = QuestionFragment.class.getSimpleName();

//    public static final String VIEW_PAGER = "questionset.VIEW_PAGER";
//    public static final String QUESTION_ID = "questionset.QUESTION_ID";

    private static final int DIVIDER_HEIGHT = 30;

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

        ArrayList<Spanned> titleAndOptions = new ArrayList<Spanned>();
        titleAndOptions.add(htmlToSpanned("" + (mQuestionId + 1) + ". " + mQuestion.getTitle()));
        ArrayList<String> options = mQuestion.getOptions();
        for (int i = 0; i < options.size(); ++i) {
            titleAndOptions.add(htmlToSpanned(Character.toString((char) ('A' + i)) + ". " + options.get(i)));
        }

        OptionAdapter adapter = new OptionAdapter(titleAndOptions);
        setListAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView lv = getListView();
        lv.getDivider().setAlpha(0);
        lv.setDividerHeight(DIVIDER_HEIGHT);
    }

    private class OptionAdapter extends ArrayAdapter<Spanned> {

        public OptionAdapter(ArrayList<Spanned> options) {
            super(getActivity(), 0, options);
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int position) {
            return 0 != position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_option, null); // *
            }

            Spanned s = getItem(position);

            TextView optionTextView = (TextView) convertView.findViewById(R.id.option_item_list_textView);
            optionTextView.setText(s);

            return convertView;
        }
    }

    private Spanned htmlToSpanned(String html) {
        final Html.ImageGetter imageGetter = new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                Drawable drawable = getResources().getDrawable(Integer.parseInt(source));
                int imageWidth = drawable.getIntrinsicWidth();
                int imageHeight = drawable.getIntrinsicHeight();

                DisplayMetrics mDisplayMetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
                int screenWidth = mDisplayMetrics.widthPixels;

                if (imageWidth * 2 > screenWidth) {
                    double rate = 1.0 * screenWidth / (imageWidth * 2);
                    imageWidth = (int) (imageWidth * rate);
                    imageHeight = (int) (imageHeight * rate);
                }
                drawable.setBounds(0, 0, imageWidth, imageHeight);
                return drawable;
            }
        };

        return Html.fromHtml(html, imageGetter, null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Log.i(TAG, "position: " + position);
        // if position == answer: 答对了
        if (mQuestionId + 1 == mQuestionListSize) {
            Toast.makeText(getActivity(), "测试结束", Toast.LENGTH_LONG).show();
        } else {
            mViewPager.setCurrentItem(mQuestionId + 1);
        }
    }
}
