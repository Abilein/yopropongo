package com.xiberty.propongo.council.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.xiberty.propongo.contrib.utils.UIUtils;

/**
 * Created by growcallisaya on 4/5/17.
 */

public class PageAdapter extends FragmentPagerAdapter {

    private final String TAG = PageAdapter.class.getSimpleName().toUpperCase();
    private SparseArray<UIUtils.PageItem> pages;
    private FragmentManager fm;

    public PageAdapter(SparseArray<UIUtils.PageItem> pages, FragmentManager fm) {
        super(fm);
        this.fm = fm;
        this.pages = pages;
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pages.get(position).title;
    }

    @Override
    public Fragment getItem(int position) {

        return pages.get(position).fragment;
    }
}