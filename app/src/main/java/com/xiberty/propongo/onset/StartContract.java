package com.xiberty.propongo.onset;

import android.content.Context;


public class StartContract {

    interface View { }

    interface StartActionListener {
        void isLoggedIn(Context context);
    }
}
