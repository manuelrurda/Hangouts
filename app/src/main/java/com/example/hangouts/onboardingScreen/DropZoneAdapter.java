package com.example.hangouts.onboardingScreen;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hangouts.R;
import com.example.hangouts.databinding.DropZoneBinding;
import com.example.hangouts.onboardingScreen.models.DropZone;

import java.util.List;

public class DropZoneAdapter extends RecyclerView.Adapter<DropZoneAdapter.ViewHolder> {

    private Context context;
    public List<DropZone> dropZones;

    private DragDropCuisineViewModel viewModel;

    public DropZoneAdapter(Context context, List<DropZone> dropZones, DragDropCuisineViewModel viewModel) {
        this.context = context;
        this.dropZones = dropZones;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DropZoneBinding binding = DropZoneBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DropZone dropZone = dropZones.get(position);
        holder.bind(dropZone);
    }

    @Override
    public int getItemCount() {
        return dropZones.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDropZoneValue;
        private ConstraintLayout clDropZone;
        private GridLayout glDropZoneGrid;


        public ViewHolder(@NonNull DropZoneBinding binding) {
            super(binding.getRoot());
            tvDropZoneValue = binding.tvDropZoneValue;
            clDropZone = binding.clDropZone;
            glDropZoneGrid = binding.glDropZoneGrid;
        }

        public void bind(DropZone dropZone) {
            String dropValueText = String.valueOf(dropZone.getDropValue());
            tvDropZoneValue.setText(dropValueText);
            glDropZoneGrid.setColumnCount(2);
            setDragListener(dropZone);

        }

        private void setDragListener(DropZone dropZone) {
            clDropZone.setOnDragListener((v, event) ->{
                switch (event.getAction()){
                    case DragEvent.ACTION_DRAG_STARTED:
                        return event.getClipDescription().hasMimeType(
                                ClipDescription.MIMETYPE_TEXT_PLAIN);
                    case DragEvent.ACTION_DRAG_ENTERED:
                    case DragEvent.ACTION_DRAG_EXITED:
                        v.invalidate();
                        return true;

                    case DragEvent.ACTION_DROP:
                        dropCard(event, dropZone);
                    case DragEvent.ACTION_DRAG_LOCATION:
                        return true;
                }
                return true;
            });
        }

        private void dropCard(DragEvent event, DropZone dropZone) {
            // Unpack clip data
            ClipData.Item item = event.getClipData().getItemAt(0);
            String dragValue = item.getText().toString();
            dropZone.storeValue(dragValue);

            viewModel.removeFirstPreferenceCard();

            // Add preference card view and set params
            PreferenceCardView preferenceCardView = new PreferenceCardView(context, dragValue);
            int cardDimenDp = (int) context.getResources()
                    .getDimension(R.dimen.dropzone_preference_card_size);

            CardView.LayoutParams preferenceCardViewParams = new CardView.LayoutParams(
                    cardDimenDp, cardDimenDp);

            preferenceCardView.setCardValue(preferenceCardView.getCardValue().substring(0, 1));
            preferenceCardView.setLayoutParams(preferenceCardViewParams);
            glDropZoneGrid.addView(preferenceCardView);
        }
    }

    public static String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
}
