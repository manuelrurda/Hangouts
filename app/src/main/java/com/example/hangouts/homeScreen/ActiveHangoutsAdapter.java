package com.example.hangouts.homeScreen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hangouts.R;
import com.example.hangouts.databinding.ItemActiveHangoutBinding;
import com.example.hangouts.homeScreen.utils.DateTimeUtil;
import com.example.hangouts.models.Hangout;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActiveHangoutsAdapter extends RecyclerView.Adapter<ActiveHangoutsAdapter.ViewHolder> {

    private Context context;
    private List<Hangout> activeHangouts = new ArrayList<>();

    public ActiveHangoutsAdapter(Context context){
        this.context = context;
    }

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

    public void setActiveHangouts(List<Hangout> hangoutList){
        this.activeHangouts = hangoutList;
        notifyDataSetChanged();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ItemActiveHangoutBinding binding;

        public ViewHolder(@NonNull ItemActiveHangoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(this);
        }

        public void bind(Hangout hangout) {
            hangout.fetchIfNeededInBackground(new GetCallback<Hangout>() {
                @Override
                public void done(Hangout fetchedHangout, ParseException e) {
                    binding.tvItemAlias.setText(fetchedHangout.getAlias());
                    binding.tvItemLocation.setText(fetchedHangout.getLocationString());
                    String stringNumber = String.valueOf(fetchedHangout.getMembers().length());
                    binding.tvItemMembers.setText(context.getResources()
                            .getString(R.string.hangout_rv_members_label, stringNumber));
                    Date deadlineDate = fetchedHangout.getDeadline();
                    binding.tvItemDate.setText(DateTimeUtil
                            .getDateString(deadlineDate));
                    binding.tvItemTime.setText(DateTimeUtil
                            .getTimeString(deadlineDate));
                }
            });
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Hangout hangout = activeHangouts.get(position);
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.homeFragmentContainer, HangoutDetailsFragment.newInstance(hangout))
                        .addToBackStack("")
                        .commit();
            }
        }
    }
}
