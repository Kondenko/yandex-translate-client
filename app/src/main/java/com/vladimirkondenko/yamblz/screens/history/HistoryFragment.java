package com.vladimirkondenko.yamblz.screens.history;


import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.widget.RxRadioGroup;
import com.vladimirkondenko.yamblz.App;
import com.vladimirkondenko.yamblz.R;
import com.vladimirkondenko.yamblz.dagger.modules.HistoryModule;
import com.vladimirkondenko.yamblz.databinding.FragmentHistoryBinding;
import com.vladimirkondenko.yamblz.model.entities.Translation;
import com.vladimirkondenko.yamblz.screens.main.MainActivity;
import com.vladimirkondenko.yamblz.utils.Utils;
import com.vladimirkondenko.yamblz.utils.adapters.TranslationsAdapter;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.realm.OrderedRealmCollection;

public class HistoryFragment extends Fragment implements HistoryView {

    private static final String TAG = "HistoryFragment";

    @Inject
    public HistoryPresenter presenter;

    private FragmentHistoryBinding binding;
    private TranslationsAdapter adapter = null;
    private ItemTouchHelper itemTouchHelper = null;

    private Disposable tabsSubscription;

    public HistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        App.get().plusHistorySubcomponent(new HistoryModule(this)).inject(this);
        presenter.attachView(this);
        // RadioButton icons
        Drawable historyDrawable = Utils.getTintedIcon(getContext(), R.drawable.ic_history_black_24px);
        Drawable bookmarkDrawable = Utils.getTintedIcon(getContext(), R.drawable.ic_bookmark_black_24px);
        binding.radiobuttonHistoryTabHistory.setCompoundDrawablesRelativeWithIntrinsicBounds(historyDrawable, null, null, null);
        binding.radiobuttonHistoryTabBookmarks.setCompoundDrawablesRelativeWithIntrinsicBounds(bookmarkDrawable, null, null, null);
        // Tabs
        tabsSubscription = RxRadioGroup.checkedChanges(binding.radiogroupHistoryTabs)
                .subscribe(integer -> presenter.selectTab(TabCodes.tabToScreenId(integer)));
        // RecyclerView
        binding.recyclerviewTranslations.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewTranslations.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.detachView();
        Utils.dispose(tabsSubscription);
        App.get().clearHistorySubcomponent();
    }

    @Override
    public void onHistorySelected() {
        ((MainActivity) this.getActivity()).setTitle(R.string.history_title_history);
    }

    @Override
    public void onBookmarksSelected() {
        ((MainActivity) this.getActivity()).setTitle(R.string.history_title_bookmarks);
    }

    @Override
    public void displayList(OrderedRealmCollection<Translation> translations) {
        adapter = new TranslationsAdapter(presenter.getAdapterPresenter(), translations);
        binding.recyclerviewTranslations.setAdapter(adapter);
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                presenter.remove(adapter.getData().get(position));
            }
        });
        itemTouchHelper.attachToRecyclerView(binding.recyclerviewTranslations);
    }

}
