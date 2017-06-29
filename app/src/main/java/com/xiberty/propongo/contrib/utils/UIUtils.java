package com.xiberty.propongo.contrib.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;

public class UIUtils {
    /**
     * Metho to receive a fragment
     *
     * **/
    public static class PageItem {
        public Fragment fragment;
        public String title;
        public int icon;

        public PageItem(){}

        public PageItem(Fragment fragment, String title, int icon){
            this.fragment = fragment;
            this.title = title;
            this.icon = icon;
        }

        public PageItem(Fragment fragment, String title){
            this.fragment = fragment;
            this.title = title;
        }

    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        Resources resources = context.getResources();
        return resources.getDisplayMetrics();
    }

    public static float convertDpToPixel(float dp, Context context) {
        return dp * (getDisplayMetrics(context).densityDpi / 160f);
    }
    public static int convertDpToPixelSize(float dp, Context context) {
        float pixels = convertDpToPixel(dp, context);
        final int res = (int) (pixels + 0.5f);
        if (res != 0) {
            return res;
        } else if (pixels == 0) {
            return 0;
        } else if (pixels > 0) {
            return 1;
        }
        return -1;
    }


}
