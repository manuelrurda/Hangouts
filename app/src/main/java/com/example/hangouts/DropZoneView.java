package com.example.hangouts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class DropZoneView extends ConstraintLayout {

    private int dropValue = 0;
    private ArrayList<String> content;

    private ConstraintLayout clDropZone;
    private TextView tvDropZoneValue;
    private GridLayout glDropZoneGrid;

    public DropZoneView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public DropZoneView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DropZoneView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setDropValue(int dropValue) {
        this.dropValue = dropValue;
        tvDropZoneValue.setText(String.valueOf(dropValue));
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.drop_zone, this, true);

        content = new ArrayList<>();
    }

    public int getDropValue() {
        return dropValue;
    }

    @Override
    public void addView(View child) {
        glDropZoneGrid.addView(child);
    }
}
