package com.example.hangouts;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;


public class PreferenceCardView extends CardView {

    private String cardValue = "";

    private TextView tvCardValue;
    private CardView cvCard;

    public PreferenceCardView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PreferenceCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PreferenceCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public PreferenceCardView(@NonNull Context context, String cardValue){
        super(context);
        init(context);
        setCardValue(cardValue);
    }


    public void setCardValue(String cardValue){
        this.cardValue = cardValue;
        tvCardValue.setText(cardValue);
    }

    public String getCardValue(){
        return this.cardValue;
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.preference_card_view, this, true);

        // No view background
        this.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
        // Center view in layout
        int dimensionInDp = (int) getResources().getDimension(R.dimen.drag_drop_card_size);
        CardView.LayoutParams params = new CardView.LayoutParams(
                dimensionInDp, dimensionInDp);
        params.gravity = Gravity.CENTER;

        this.setLayoutParams(params);

        tvCardValue = findViewById(R.id.tvCardValue);
        tvCardValue.setText(cardValue);

        cvCard = findViewById(R.id.cvCard);
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
