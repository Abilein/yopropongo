package com.xiberty.warpp.contrib.api;


import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Excepts {

    public static boolean isConnectionFailure(Throwable t) {
        return (t instanceof ConnectException || t instanceof SocketTimeoutException || t instanceof SocketException);
    }
}
