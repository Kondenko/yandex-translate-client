package com.vladimirkondenko.yamblz.utils.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vladimirkondenko.yamblz.Const;
import com.vladimirkondenko.yamblz.R;
import com.vladimirkondenko.yamblz.model.entities.Languages;

import java.util.LinkedHashMap;

public class LanguageSpinnerAdapter extends BaseAdapter {

    private static final int layout = R.layout.item_translation_language_spinner;

    private Context context;

    private LinkedHashMap<String, String> dataset = new LinkedHashMap<>();
    private String[] keys;

    private LayoutInflater inflater;

    public LanguageSpinnerAdapter(@NonNull Context context) {
        this(context, new Languages());
    }

    public LanguageSpinnerAdapter(@NonNull Context context, Languages languages) {
        setLangs(languages);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setLangs(Languages langs) {
        setLangs(langs, false);
    }

    public void setLangs(Languages languages, boolean addAutodetectOption) {
        if (addAutodetectOption) {
            dataset.put(Const.LANG_CODE_AUTO, context.getString(R.string.main_detect_language));
        }
        dataset.putAll(languages.getLanguages());
        keys = dataset.keySet().toArray(new String[dataset.size()]);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataset.size();
    }

    public String getItemValue(int i) {
        return dataset.get(getItem(i));
    }

    @Override
    public String getItem(int i) {
        if (keys.length == 0) return "";
        String item = keys[i];
        return item != null ? item : "";
    }

    public int getItemPosition(String lang) {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i].equals(lang)) return i;
        }
        return -1;
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
        String value = getItemValue(position);
        textView.setText(value);
        return textView;
    }


}
