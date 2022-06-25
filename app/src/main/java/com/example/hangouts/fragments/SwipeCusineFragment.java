package com.example.hangouts.fragments;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchUIUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.DragEvent;
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

    private ConstraintLayout clDragDropLayout;
    private CardStackView csPreferenceCards;

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
        csPreferenceCards = binding.csPreferenceCards;

        List<PreferenceCard> preferenceCards = new ArrayList<>();
        // Temporary
        preferenceCards.add(new PreferenceCard("Italian"));
        preferenceCards.add(new PreferenceCard("Mexican"));
        preferenceCards.add(new PreferenceCard("Chinese"));
        preferenceCards.add(new PreferenceCard("Thai"));

        PreferenceCardAdapter adapter = new PreferenceCardAdapter(getContext(), preferenceCards);
        csPreferenceCards.setLayoutManager(getStackLayoutManager());
        csPreferenceCards.setAdapter(adapter);

        clDragDropLayout = binding.clDragDropLayout;
        clDragDropLayout.setOnDragListener((v, event) ->{
            switch (event.getAction()){

                case DragEvent.ACTION_DRAG_STARTED:
                    return event.getClipDescription().hasMimeType(
                            ClipDescription.MIMETYPE_TEXT_PLAIN);

                case DragEvent.ACTION_DRAG_ENTERED:

                case DragEvent.ACTION_DRAG_EXITED:
                    view.invalidate();
                    return true;

                case DragEvent.ACTION_DRAG_LOCATION:
                    return true;

                case DragEvent.ACTION_DROP:
                    Log.d(TAG, "bind: DROP");
                    ClipData.Item item = event.getClipData().getItemAt(0);
                    String dragText = item.getText().toString();
                    Toast.makeText(getContext(), dragText, Toast.LENGTH_SHORT).show();
                    preferenceCards.remove(0);
                    adapter.notifyItemRemoved(0);
                    return true;
            }
            return true;
        });
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