package com.joy.movieviewer;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by joy05 on 2017/2/15.
 */

public class MTDbRestClient {
    private static final String TAG = "MTDbRestClient";

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String URL_SUFFIX;

    static {
        URL_SUFFIX = "?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    }

    private static AsyncHttpClient sClient = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        sClient.get(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl + URL_SUFFIX;
    }
}
