package com.joy.movieviewer;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by joy0520 on 2017/2/15.
 */

public class MTDbRestClient {
    private static final String TAG = "MTDbRestClient";

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String URL_SUFFIX = "?api_key=";

    private static AsyncHttpClient sClient = new AsyncHttpClient();

    public static void get(String key, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        sClient.get(getAbsoluteUrl(key, url), params, responseHandler);
    }

    public static void getYoutubeMovieKey(String key, int movieId, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        Log.i("getYoutubeMovieKey", "getAbsoluteVideoUrl(key, movieId)="+getAbsoluteVideoUrl(key, movieId));
        sClient.get(getAbsoluteVideoUrl(key, movieId), params, responseHandler);
    }

    private static String getAbsoluteUrl(String key, String relativeUrl) {
        return BASE_URL + relativeUrl + URL_SUFFIX + key;
    }

    private static String getAbsoluteVideoUrl(String key, int movieId) {
        return String.format("%smovie/%d/videos%s%s",BASE_URL, movieId, URL_SUFFIX, key).toString();
    }
}
