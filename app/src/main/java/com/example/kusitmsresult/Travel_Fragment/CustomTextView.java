package com.example.kusitmsresult.Travel_Fragment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.kusitmsresult.R;

public class CustomTextView extends androidx.appcompat.widget.AppCompatTextView {
    private boolean stroke = false;
    private float strokeWidth = 0.0f;
    private int strokeColor;
    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle); initView(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }


    public CustomTextView(Context context) {
        super(context);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        stroke = a.getBoolean(R.styleable.CustomTextView_textStroke, false);
        strokeWidth = a.getFloat(R.styleable.CustomTextView_textStrokeWidth, 2.0f);
        strokeColor = a.getColor(R.styleable.CustomTextView_textStrokeColor, 0xB3FFFFFF);
    }

    @Override protected void onDraw(Canvas canvas) {
        if (stroke) {
            ColorStateList states = getTextColors();
            getPaint().setStyle(Style.STROKE);
            getPaint().setStrokeWidth(strokeWidth);
            setTextColor(strokeColor);
            super.onDraw(canvas);
            getPaint().setStyle(Style.FILL);
            setTextColor(states);
        }

        super.onDraw(canvas);
    }

}