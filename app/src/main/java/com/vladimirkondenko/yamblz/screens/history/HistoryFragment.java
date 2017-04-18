package com.vladimirkondenko.yamblz.screens.history;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vladimirkondenko.yamblz.R;
import com.vladimirkondenko.yamblz.databinding.FragmentHistoryBinding;

public class HistoryFragment extends Fragment {


    public HistoryFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentHistoryBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        return binding.getRoot();
    }

}
