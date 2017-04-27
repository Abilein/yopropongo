package com.xiberty.warpp.contrib.api;

import com.google.gson.Gson;

public class FormattedResp {

    private String code;
    private String message;

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}