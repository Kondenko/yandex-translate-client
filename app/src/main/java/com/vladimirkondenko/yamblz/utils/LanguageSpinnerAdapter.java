package com.vladimirkondenko.yamblz.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vladimirkondenko.yamblz.R;
import com.vladimirkondenko.yamblz.model.LanguagesHolder;

import java.util.LinkedHashMap;

public class LanguageSpinnerAdapter extends BaseAdapter {

    private static final int layout = R.layout.item_translation_language_spinner;

    private LinkedHashMap<String, String> dataset;
    private String[] keys;

    private LayoutInflater inflater;

    public LanguageSpinnerAdapter(@NonNull Context context, LanguagesHolder languagesHolder) {
        this.dataset = languagesHolder.languages;
        keys = dataset.keySet().toArray(new String[dataset.size()]);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataset.size();
    }

    @Override
    public String getItem(int i) {
        return dataset.get(keys[i]);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, parent);
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup parent) {
        return getCustomView(position, parent);
    }

    private View getCustomView(int position, ViewGroup parent) {
        TextView textView = (TextView) inflater.inflate(layout, parent, false);
        String value = getItem(position);
        textView.setText(value);
        return textView;
    }


}
