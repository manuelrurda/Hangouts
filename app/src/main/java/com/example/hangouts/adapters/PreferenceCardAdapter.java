package com.example.hangouts.adapters;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hangouts.databinding.PreferenceCardBinding;
import com.example.hangouts.models.PreferenceCard;

import java.util.List;

public class PreferenceCardAdapter extends RecyclerView.Adapter<PreferenceCardAdapter.ViewHolder> {

    public static final String TAG = "CardAdapter";

    private Context context;
    public List<PreferenceCard> PreferenceCards;

    public PreferenceCardAdapter(Context context) {
        this.context = context;
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
        return PreferenceCards.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PreferenceCard preferenceCard = PreferenceCards.get(position);
        holder.bind(preferenceCard);
    }

    public void setPreferenceCards(List<PreferenceCard> preferenceCards) {
        this.PreferenceCards = preferenceCards;
        notifyDataSetChanged();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCardValue;
        private CardView cvCard;

        public ViewHolder(@NonNull PreferenceCardBinding binding) {
            super(binding.getRoot());
            tvCardValue = binding.tvCardValue;
            cvCard = binding.cvCard;
        }

        public void bind(PreferenceCard preferenceCard) {
            String cardValue = preferenceCard.getValue();
            tvCardValue.setText(cardValue);
            cvCard.setOnLongClickListener(view -> {
                ClipData.Item valueText = new ClipData.Item(cardValue);
                ClipData dragData = new ClipData(cardValue,
                        new String[] {ClipDescription.MIMETYPE_TEXT_PLAIN}, valueText);

                View.DragShadowBuilder shadow = new View.DragShadowBuilder(cvCard);
                view.startDragAndDrop(dragData, shadow, null, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            });
        }
    }

}