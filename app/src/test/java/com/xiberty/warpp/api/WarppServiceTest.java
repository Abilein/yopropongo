package com.xiberty.warpp.api;

import android.telecom.Call;

import com.xiberty.warpp.BaseTest;
import com.xiberty.warpp.BuildConfig;
import com.xiberty.warpp.Constants;
import com.xiberty.warpp.contrib.api.WS;
import com.xiberty.warpp.credentials.CredentialService;
import com.xiberty.warpp.credentials.responses.UserProfile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import retrofit2.Response;

import static junit.framework.Assert.assertTrue;

/**
 * Created by growcallisaya on 19/4/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class WarppServiceTest extends BaseTest{

    private CredentialService mService;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        CredentialService credentialService = WS.makeService(CredentialService.class);
        this.mService = credentialService;
    }

    @Test
    public void registerUser_test() throws Exception {
        retrofit2.Call<UserProfile> call = mService.register(Constants.CLIENT_ID,
                Constants.CLIENT_SECRET, "grow","grow@grow.com", "123456");

        //Va sere sincrono la respuesta
        Response<UserProfile> response = call.execute();
        assertTrue(response.isSuccessful());
    }
}
