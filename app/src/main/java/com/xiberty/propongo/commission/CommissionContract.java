package com.xiberty.propongo.commission;

import android.content.Context;

import com.xiberty.propongo.council.responses.Commission;

import java.util.List;

/**
 * Created by growcallisaya on 15/5/17.
 */

public class CommissionContract {

    public interface Presenter{
        void getCommissions(Context context);
    }

    public interface CommissionView{
        void loadCommissions(List<Commission> items);
    }
}
