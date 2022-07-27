package com.example.hangouts.homeScreen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
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
import com.example.hangouts.homeScreen.hangoutResults.HangoutResultsFragment;
import com.example.hangouts.homeScreen.utils.DateTimeUtil;
import com.example.hangouts.models.Hangout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
                    if(Objects.equals(hangout.getHost().getObjectId(), ParseUser.getCurrentUser().getObjectId())){
                        binding.btnCloseHangout.setVisibility(View.VISIBLE);
                        binding.btnCloseHangout.setOnClickListener(this::onClickClose);
                    }
                }

                private void onClickClose(View view) {
                    Resources resources = context.getResources();
                    new MaterialAlertDialogBuilder(context)
                            .setTitle(resources.getString(R.string.close_hangout_dialog_title))
                            .setMessage(resources.getString(R.string.close_hangout_dialog_text))
                            .setNegativeButton(resources.getString(R.string.close_hangout_dialog_cancel),
                                    this::onClickCancel)
                            .setPositiveButton(resources.getString(R.string.close_hangout_dialog_accept),
                                    this::onClickAccept)
                            .show();
                }

                private void onClickAccept(DialogInterface dialogInterface, int i) {
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.homeFragmentContainer, HangoutResultsFragment.newInstance(hangout))
                            .addToBackStack("")
                            .commit();
                }

                private void onClickCancel(DialogInterface dialogInterface, int i) {}
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
