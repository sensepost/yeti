package com.sensepost.yeti.spider;

import java.net.URL;

public interface UrlProcessor {

    void addHostFromUrl(String url, String baseUrl);

    void handleUrlError(URL url, String baseUrl);

    void addEmailAddress(String emailAddress);

    void onDebug(String message);

    void onError(String errorMessage);
}
