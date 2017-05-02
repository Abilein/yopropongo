package com.xiberty.propongo.contrib.api;


import com.xiberty.propongo.credentials.responses.OAuthCredential;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private OAuthCredential credential;

    public AuthInterceptor(OAuthCredential credential) {
        this.credential = credential;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder()
                .header("Authorization", credential.tokenType() +" "+ credential.accessToken());
        Request request = builder.build();

        return chain.proceed(request);
    }
}
