package com.example.hangouts.homeScreen;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hangouts.databinding.ItemActiveHangoutBinding;
import com.example.hangouts.models.Hangout;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public class ActiveHangoutsAdapter extends RecyclerView.Adapter<ActiveHangoutsAdapter.ViewHolder> {

    private Context context;
    private List<Hangout> activeHangouts = new ArrayList<>();

//    public ActiveHangoutsAdapter(Context context, List<Hangout> activeHangouts){
//        this.context = context;
//        this.activeHangouts = activeHangouts;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemActiveHangoutBinding binding = ItemActiveHangoutBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hangout hangout = activeHangouts.get(position);
        holder.bind(hangout);
    }

    @Override
    public int getItemCount() {
        return activeHangouts.size();
    }

//    public void updateActiveHangoutsList(List<Hangout> newHangoutsList){
//        if(activeHangouts != null){
//            activeHangouts.clear();
//        }
//        activeHangouts = newHangoutsList;
//        notifyDataSetChanged();
//    }

    public void setActiveHangouts(List<Hangout> hangoutList){
        this.activeHangouts = hangoutList;
        notifyDataSetChanged();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{
        ItemActiveHangoutBinding binding;
        public ViewHolder(@NonNull ItemActiveHangoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Hangout hangout) {
            try{
                hangout = hangout.fetchIfNeeded();
            }catch (ParseException e){
                e.printStackTrace();
            }
            binding.tvItemAlias.setText(hangout.getAlias());
            binding.tvItemLocation.setText(hangout.getLocationString());
            binding.tvItemMembers.setText(String.valueOf(hangout.getMembers().length()));
        }
    }
}
