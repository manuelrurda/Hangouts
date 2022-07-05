package com.example.hangouts.onboardingScreen;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.hangouts.R;


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

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ScaleAnimation anim = new ScaleAnimation(0f, 1f, 0f,
                1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        int animDurationMs = 100;
        anim.setDuration(animDurationMs);
        anim.setFillAfter(true);
        this.startAnimation(anim);
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

        setViewParams();
        setCardParams();
    }

    private void setViewParams() {
        // No view background
        this.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));

        int cardDimenDp = (int) getResources().getDimension(R.dimen.preference_card_size);
        LayoutParams viewParams = new LayoutParams(
                cardDimenDp, cardDimenDp);
        // Center view in layout
        viewParams.gravity = Gravity.CENTER;
        this.setLayoutParams(viewParams);
    }

    private void setCardParams() {
        tvCardValue = findViewById(R.id.tvCardValue);
        tvCardValue.setText(cardValue);

        cvCard = findViewById(R.id.cvCard);

        int cardMarginDp = (int) getResources().getDimension(R.dimen.drag_drop_card_margin);
        CardView.LayoutParams cardParams = new CardView.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        cardParams.bottomMargin = cardMarginDp;
        cardParams.topMargin = cardMarginDp;
        cardParams.leftMargin = cardMarginDp;
        cardParams.rightMargin = cardMarginDp;
        cvCard.setLayoutParams(cardParams);
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
