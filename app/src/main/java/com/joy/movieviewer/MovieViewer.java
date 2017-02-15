package com.joy.movieviewer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.joy.movieviewer.item.MovieGson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MovieViewer extends Activity {
    private static String MTDB_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MTDB_KEY = getString(R.string.mtdb_api_key);

//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get("https://api.themoviedb.org/3/movie/76341?api_key=" + MTDB_KEY, new AsyncHttpResponseHandler() {
//
//            @Override
//            public void onStart() {
//                // called before request is started
//                Log.i("joy", "onStart");
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
//                // called when response HTTP status is "200 OK"
//                Log.i("joy.onSuccess()", "statusCOde=" + statusCode
//                        + "\nheaders=" + headers
//                        + "\nresponse=" + response);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//                Log.i("joy", "onFailure");
//            }
//
//            @Override
//            public void onRetry(int retryNo) {
//                // called when request is retried
//                Log.i("joy", "onRetry");
//            }
//        });
        queryMtdb();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void queryMtdb() {
        MTDbRestClient.get("movie/now_playing", null, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBytes) {
                super.onSuccess(statusCode, headers, responseBytes);
                Log.i("joy.onSuccess()", "another");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson = new Gson();
                Log.i("joy.onSuccess()", "responseString=" + responseString);
                MovieGson result = gson.fromJson(responseString, MovieGson.class);
                Log.i("joy.onSuccess()", "result=" + result);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("joy.onFailure()", "damn");
            }
        });
    }

}
