package com.example.hangouts.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hangouts.PreferenceCard;
import com.example.hangouts.databinding.PreferenceCardBinding;

import java.util.List;

public class PreferenceCardAdapter extends RecyclerView.Adapter<PreferenceCardAdapter.ViewHolder> {

    private Context context;
    private List<PreferenceCard> preferenceCards;

    public PreferenceCardAdapter(Context context, List<PreferenceCard> preferenceCards) {
        this.context = context;
        this.preferenceCards = preferenceCards;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PreferenceCardBinding binding = PreferenceCardBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return preferenceCards.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PreferenceCard preferenceCard = preferenceCards.get(position);
        holder.bind(preferenceCard);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvStringValue;

        public ViewHolder(@NonNull PreferenceCardBinding binding) {
            super(binding.getRoot());
            tvStringValue = binding.tvStringValue;
        }

        public void bind(PreferenceCard preferenceCard) {
            tvStringValue.setText(preferenceCard.getStringValue());
        }
    }
}
