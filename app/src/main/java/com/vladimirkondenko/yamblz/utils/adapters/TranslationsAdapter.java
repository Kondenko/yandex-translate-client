package com.vladimirkondenko.yamblz.utils.adapters;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerViewAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxAdapter;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.vladimirkondenko.yamblz.R;
import com.vladimirkondenko.yamblz.databinding.ItemTranslationsListBinding;
import com.vladimirkondenko.yamblz.model.entities.Translation;
import com.vladimirkondenko.yamblz.utils.events.BookmarkedEvent;
import com.vladimirkondenko.yamblz.utils.events.Bus;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class TranslationsAdapter extends RealmRecyclerViewAdapter<Translation, TranslationsAdapter.TranslationViewHolder> {

    public TranslationsAdapter(OrderedRealmCollection<Translation> data) {
        super(data, true);
        setHasStableIds(true);
    }

    @Override
    public TranslationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemTranslationsListBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_translations_list, parent, false);
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

        private final ItemTranslationsListBinding binding;

        public TranslationViewHolder(ItemTranslationsListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Translation translation) {
            binding.setTranslation(translation);
            binding.buttonTransationBookmark.setOnCheckedChangeListener((button, isChecked) ->
                    Bus.post(new BookmarkedEvent(translation.getId(), isChecked))
            );
            binding.executePendingBindings();
        }

    }

}
