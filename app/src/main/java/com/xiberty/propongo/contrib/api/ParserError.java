package com.xiberty.propongo.contrib.api;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;


public class ParserError {

    public static FormattedResp parse(Response<?> response) {
        Converter<ResponseBody, FormattedResp> converter =
                WS.retrofit.responseBodyConverter(FormattedResp.class, new Annotation[0]);

        FormattedResp error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new FormattedResp();
        }

        return error;
    }
}