package com.example.hangouts.homeScreen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hangouts.HomeViewModel;
import com.example.hangouts.R;
import com.example.hangouts.databinding.ItemPastHangoutBinding;
import com.example.hangouts.models.Hangout;
import com.parse.GetCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public class PastHangoutsAdapter extends RecyclerView.Adapter<PastHangoutsAdapter.ViewHolder> {

    private Context context;
    private HomeViewModel homeViewModel;
    private List<Hangout> pastHangouts = new ArrayList<>();

    public PastHangoutsAdapter(Context context){
//        homeViewModel = new ViewModelProvider((FragmentActivity) context).get(HomeViewModel.class);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPastHangoutBinding binding = ItemPastHangoutBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hangout hangout = pastHangouts.get(position);
        holder.bind(hangout);
    }

    @Override
    public int getItemCount() {
        return pastHangouts.size();
    }

    public void setPastHangouts(List<Hangout> hangoutList){
        this.pastHangouts = hangoutList;
        notifyItemChanged(0);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ItemPastHangoutBinding binding;

        public ViewHolder(@NonNull ItemPastHangoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(this);
        }

        public void bind(Hangout hangout) {
            hangout.fetchIfNeededInBackground(new GetCallback<Hangout>() {
                @Override
                public void done(Hangout fetchedHangout, ParseException e) {
                    binding.tvPastItemAlias.setText(fetchedHangout.getAlias());
                    binding.tvPastItemLocation.setText(fetchedHangout.getLocationString());
                    String stringNumber = String.valueOf(fetchedHangout.getMembers().length());
                    binding.tvPastItemMembers.setText(context.getResources()
                            .getString(R.string.hangout_rv_members_label, stringNumber));
                }
            });
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Hangout hangout = pastHangouts.get(position);
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.homeFragmentContainer, HangoutDetailsFragment.newInstance(hangout))
                        .addToBackStack("")
                        .commit();
            }
        }
    }
}
