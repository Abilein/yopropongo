package com.xiberty.warpp.contrib.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import android.support.v7.widget.AppCompatTextView;

public class XTextView extends AppCompatTextView {

    public XTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public XTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Light.ttf");
            setTypeface(tf);
        }
    }

}