package com.vladimirkondenko.yamblz.utils.adapters;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.vladimirkondenko.yamblz.R;
import com.vladimirkondenko.yamblz.databinding.ItemAllTranslationsListBinding;
import com.vladimirkondenko.yamblz.model.entities.Translation;
import com.vladimirkondenko.yamblz.screens.history.HistoryPresenter;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class TranslationsAdapter extends RealmRecyclerViewAdapter<Translation, TranslationsAdapter.TranslationViewHolder> {

    private HistoryPresenter.AdapterPresenter presenter;

    public TranslationsAdapter(HistoryPresenter.AdapterPresenter presenter, OrderedRealmCollection<Translation> data) {
        super(data, true);
        setHasStableIds(true);
        this.presenter = presenter;
    }

    @Override
    public TranslationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemAllTranslationsListBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_all_translations_list, parent, false);
        return new TranslationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(TranslationViewHolder holder, int position) {
        Translation item = getData().get(position);
        if (item != null) {
            holder.bind(item);
        }
    }

    @Override
    public int getItemCount() {
        return getData().size();
    }

    public class TranslationViewHolder extends RecyclerView.ViewHolder {

        private final ItemAllTranslationsListBinding binding;

        public TranslationViewHolder(ItemAllTranslationsListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Translation translation) {
            binding.setTranslation(translation);
            binding.buttonTransationBookmark.setOnCheckedChangeListener(isChecked -> {
                        presenter.setBookmarked(translation, isChecked);
                    }
            );
            binding.executePendingBindings();
        }

    }

}
