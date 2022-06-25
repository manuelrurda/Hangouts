package com.example.hangouts.adapters;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hangouts.PreferenceCard;
import com.example.hangouts.databinding.PreferenceCardBinding;

import java.util.List;

public class PreferenceCardAdapter extends RecyclerView.Adapter<PreferenceCardAdapter.ViewHolder> {

    public static final String TAG = "CardAdapter";

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
        private ConstraintLayout dragView;

        public ViewHolder(@NonNull PreferenceCardBinding binding) {
            super(binding.getRoot());
            tvStringValue = binding.tvStringValue;
            dragView = binding.dragView;
        }

        public void bind(PreferenceCard preferenceCard) {
            tvStringValue.setText(preferenceCard.getStringValue());

            dragView.setOnLongClickListener(view -> {
                int clipAdapterPosition = this.getAbsoluteAdapterPosition();
                String clipValueText = tvStringValue.getText().toString();
                ClipData.Item valueText = new ClipData.Item(clipValueText);
                ClipData dragData = new ClipData(clipValueText,
                        new String[] {ClipDescription.MIMETYPE_TEXT_PLAIN}, valueText);

                View.DragShadowBuilder shadow = new View.DragShadowBuilder(dragView);
                view.startDragAndDrop(dragData, shadow, null, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            });


        }



    }
}
