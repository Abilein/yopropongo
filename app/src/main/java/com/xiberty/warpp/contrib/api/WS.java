package com.xiberty.warpp.contrib.api;


import android.text.TextUtils;

import com.xiberty.warpp.Constants;
import com.xiberty.warpp.credentials.responses.OAuthCredential;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WS {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    private static Retrofit.Builder builder = new Retrofit.Builder()
                                    .baseUrl(Constants.SERVER_URL)
                                    .addConverterFactory(GsonConverterFactory.create());

    public static Retrofit retrofit = builder.build();




    // Make service from SIMPLE Endpoints
    public static <T> T makeService(Class<T> serviceClass) {
        return makeService(serviceClass, null);
    }

    // Make service from OAUTH2 AccessToken
    public static <T> T makeService(Class<T> serviceClass, final OAuthCredential credential) {

        if (credential != null && !TextUtils.isEmpty(credential.accessToken())) {

            AuthInterceptor interceptor = new AuthInterceptor(credential);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }
        return retrofit.create(serviceClass);
    }


}
