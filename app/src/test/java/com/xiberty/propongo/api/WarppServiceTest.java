package com.xiberty.propongo.api;

import com.xiberty.propongo.BaseTest;
import com.xiberty.propongo.BuildConfig;
import com.xiberty.propongo.Constants;
import com.xiberty.propongo.contrib.api.WS;
import com.xiberty.propongo.credentials.CredentialService;
import com.xiberty.propongo.credentials.responses.UserProfile;

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
