package com.xiberty.propongo;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowApplication;


public abstract class BaseTest {

    @Before
    public void setUp() throws Exception {
        //Start All here
        MockitoAnnotations.initMocks(this);

        //For Robolectric, we will grant permission for INTERNET
        if (RuntimeEnvironment.application !=null){
            ShadowApplication shadowApp = Shadows.shadowOf(RuntimeEnvironment.application);
            shadowApp.grantPermissions("android.permission.INTERNET");
        }
    }
}
