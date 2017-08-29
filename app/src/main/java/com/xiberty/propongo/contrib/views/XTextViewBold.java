package com.xiberty.propongo.contrib.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class XTextViewBold extends AppCompatTextView {

    public XTextViewBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public XTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XTextViewBold(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/latoRegular.ttf");
            setTypeface(tf);
        }
    }

}