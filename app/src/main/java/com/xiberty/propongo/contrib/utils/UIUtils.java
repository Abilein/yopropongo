package com.xiberty.propongo.contrib.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;

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
    /**
     * Method to Convert in Date
     * **/
    public static String convertToDate(String date) {
        String year = date.substring(0,4);
        String month = date.substring(5,7);
        String day = date.substring(8,10
        );
        Log.e("Date", month+ " "+day);
        String new_date = "";
        new_date+= day+" ";
        new_date+= convertToMonth(month)+", ";
        new_date+= year;

        return new_date;
    }

    private static String convertToMonth(String month) {
        switch (month){
            case "01": return "Enero";
            case "02": return "Febrero";
            case "03": return "Marzo";
            case "04": return "Abril";
            case "05": return "Mayo";
            case "06": return "Junio";
            case "07": return "Julio";
            case "08": return "Agosto";
            case "09": return "Septiembre";
            case "10": return "Octubre";
            case "11": return "Noviembre";
            case "12": return "Diciembre";
            default: return "";
        }
    }


}
