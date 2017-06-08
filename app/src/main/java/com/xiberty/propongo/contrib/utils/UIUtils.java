package com.xiberty.propongo.contrib.utils;

import android.support.v4.app.Fragment;

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
}
