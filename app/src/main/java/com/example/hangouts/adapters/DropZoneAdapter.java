package com.example.hangouts.adapters;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hangouts.databinding.DropZoneBinding;
import com.example.hangouts.models.DropZone;
import com.example.hangouts.viewmodels.DragDropCuisineViewModel;

import java.util.List;

public class DropZoneAdapter extends RecyclerView.Adapter<DropZoneAdapter.ViewHolder> {

    private Context context;
    public List<DropZone> dropZones;

    public DropZoneAdapter(Context context, List<DropZone> dropZones) {
        this.context = context;
        this.dropZones = dropZones;
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


        public ViewHolder(@NonNull DropZoneBinding binding) {
            super(binding.getRoot());
            tvDropZoneValue = binding.tvDropZoneValue;
            clDropZone = binding.clDropZone;
        }

        public void bind(DropZone dropZone) {
            String dropValueText = String.valueOf(dropZone.getDropValue());
            tvDropZoneValue.setText(dropValueText);
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
                        ClipData.Item item = event.getClipData().getItemAt(0);
                        String dragText = item.getText().toString();
                        Toast.makeText(context, dragText+" AT DROP ZONE "
                               + dropValueText , Toast.LENGTH_SHORT).show();
                    case DragEvent.ACTION_DRAG_LOCATION:
                        return true;
                }
                return true;
            });
        }
    }
}
