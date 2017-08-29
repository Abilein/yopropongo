package com.xiberty.propongo.contrib.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;


public class XEditText extends AppCompatEditText {

    public XEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public XEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XEditText(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/latoLight.ttf");
            setTypeface(tf);
        }
    }

}