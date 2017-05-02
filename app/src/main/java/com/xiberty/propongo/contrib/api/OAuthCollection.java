package com.xiberty.propongo.contrib.api;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class OAuthCollection {

    private String update_date;

    private List<String> credentials = new ArrayList<String>();

    public List<String> credentials() {
        return credentials;
    }
    public void credentials(List<String> credentials) {
        this.credentials = credentials;
    }

    public String updateDate() {return update_date;}
    public void updateDate(String update_date) {this.update_date = update_date;}

    public String toString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public boolean isEmpty(){
        return (credentials.size()==0);
    }
}
