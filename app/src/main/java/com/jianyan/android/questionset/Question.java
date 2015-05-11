package com.jianyan.android.questionset;

import java.util.ArrayList;

public class Question {
    private String title;
    private ArrayList<String> options;

    public Question() {
        options = new ArrayList<String>();
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void addOption(String option) {
        options.add(option);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
