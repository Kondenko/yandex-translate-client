package com.vladimirkondenko.yamblz.screens.history;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vladimirkondenko.yamblz.App;
import com.vladimirkondenko.yamblz.R;
import com.vladimirkondenko.yamblz.dagger.modules.HistoryModule;
import com.vladimirkondenko.yamblz.databinding.FragmentHistoryBinding;
import com.vladimirkondenko.yamblz.model.entities.Translation;
import com.vladimirkondenko.yamblz.utils.adapters.TranslationsAdapter;
import com.vladimirkondenko.yamblz.utils.events.BookmarkedEvent;
import com.vladimirkondenko.yamblz.utils.events.Bus;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import io.realm.OrderedRealmCollection;

public class HistoryFragment extends Fragment implements HistoryView {

    private static final String TAG = "HistoryFragment";

    @Inject
    public HistoryPresenter presenter;

    private FragmentHistoryBinding binding;
    private TranslationsAdapter adapter = null;

    public HistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        App.get().plusHistorySubcomponent(new HistoryModule(this)).inject(this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        binding.recyclerviewHistoryTranslations.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewHistoryTranslations.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                presenter.removeFromHistory(adapter.getData().get(position));
            }
        });
        itemTouchHelper.attachToRecyclerView(binding.recyclerviewHistoryTranslations);
        presenter.attachView(this);
        presenter.onCreateView();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroyView();
        presenter.detachView();
        App.get().clearHistorySubcomponent();
    }

    @Override
    public void displayHistory(OrderedRealmCollection<Translation> translations) {
        adapter = new TranslationsAdapter(presenter.getAdapterPresenter(), translations);
        binding.recyclerviewHistoryTranslations.setAdapter(adapter);
    }

}
