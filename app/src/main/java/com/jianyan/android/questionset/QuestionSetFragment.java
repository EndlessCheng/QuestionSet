package com.jianyan.android.questionset;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class QuestionSetFragment extends Fragment {
    private static final String TAG = QuestionSetFragment.class.getSimpleName();

    private Button mGenerateQuestionSetButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question_set, container, false);

        mGenerateQuestionSetButton = (Button) v.findViewById(R.id.generate_question_set_button);
        mGenerateQuestionSetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                xmlToText();
                startActivity(new Intent(getActivity(), QuestionPagerActivity.class));
            }
        });

        return v;
    }

    private void xmlToText() {
        ArrayList<Question> quesitionList = new ArrayList<Question>();

        Question question = null;
        StringBuilder htmlStringBuilder = new StringBuilder("");
        try {
            for (XmlResourceParser xrp = getResources().getXml(R.xml.questions); xrp.getEventType() != XmlResourceParser.END_DOCUMENT; xrp.next()) {
                if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                    String tagName = xrp.getName();
                    switch (tagName) {
                        case "question":
                            question = new Question();
                            break;
                        case "title":
                        case "option":
                            htmlStringBuilder = new StringBuilder("");
                            break;
                        case "string":
                            htmlStringBuilder.append(xrp.nextText());
                            break;
                        case "img":
                            htmlStringBuilder.append("<img src='").
                                    append(getResources().getIdentifier(xrp.getAttributeValue(null, "src"), "drawable", "com.jianyan.android.questionset"))
                                    .append("'/>");
                            break;
                    }
                } else if (xrp.getEventType() == XmlResourceParser.END_TAG) {
                    String tagName = xrp.getName();
                    switch (tagName) {
                        case "question":
                            quesitionList.add(question);
                            break;
                        case "title":
                            question.setTitle(htmlStringBuilder.toString());
                            break;
                        case "option":
                            question.addOption(htmlStringBuilder.toString());
                            break;
                    }
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        QuestionSetManager.getInstance().setQuestionList(quesitionList);
    }
}
