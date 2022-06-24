package com.example.hangouts.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchUIUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hangouts.PreferenceCard;
import com.example.hangouts.R;
import com.example.hangouts.adapters.PreferenceCardAdapter;
import com.example.hangouts.databinding.FragmentSwipeCusineBinding;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

public class SwipeCusineFragment extends Fragment {

    public static final String TAG = "SwipeCusineFragment";
    FragmentSwipeCusineBinding binding;

    public SwipeCusineFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSwipeCusineBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CardStackView csPreferenceCards = binding.csPreferenceCards;

        List<PreferenceCard> preferenceCards = new ArrayList<>();
        preferenceCards.add(new PreferenceCard("Italian"));
        preferenceCards.add(new PreferenceCard("Mexican"));
        preferenceCards.add(new PreferenceCard("Chinese"));
        preferenceCards.add(new PreferenceCard("Thai"));

        PreferenceCardAdapter adapter = new PreferenceCardAdapter(getContext(), preferenceCards);
        csPreferenceCards.setLayoutManager(getStackLayoutManager());
        csPreferenceCards.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = getItemTouchHelper();

        itemTouchHelper.attachToRecyclerView(csPreferenceCards);
    }

    private ItemTouchHelper getItemTouchHelper() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT
                                | ItemTouchHelper.RIGHT, 0) {

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        int startPosition  = viewHolder.getAbsoluteAdapterPosition();
                        int endPosition = target.getAbsoluteAdapterPosition();
                        Toast.makeText(getContext(), "MOVED", Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {}

                    @Override
                    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                        switch (actionState){
                            case ItemTouchHelper.ACTION_STATE_IDLE:
                                // The user used onMove()
//                                Toast.makeText(getContext(), "Dragging & Dropping are over", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {    //called when you dropped the item
                        super.clearView(recyclerView, viewHolder);

                        Toast.makeText(recyclerView.getContext(), "Item dropped on position: " + viewHolder.getLayoutPosition(), Toast.LENGTH_SHORT).show();
                    }
                });
        return itemTouchHelper;
    }

    private RecyclerView.LayoutManager getStackLayoutManager() {
        CardStackLayoutManager cardStackLayoutManager = new CardStackLayoutManager(getContext());
        cardStackLayoutManager.setStackFrom(StackFrom.Bottom);
        cardStackLayoutManager.setTranslationInterval(4.0f);
        cardStackLayoutManager.setSwipeableMethod(SwipeableMethod.None);
        return cardStackLayoutManager;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}