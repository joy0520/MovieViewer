package com.joy.movieviewer;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.joy.movieviewer.item.MovieGson;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MovieViewer extends Activity {
    private static String MTDB_KEY;

    private RecyclerView mList;
    private MovieGson mMovieGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateResources();
        queryMtdb("movie/now_playing");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void updateResources() {
        MTDB_KEY = getString(R.string.mtdb_api_key);

        mList = (RecyclerView) findViewById(R.id.list);
        // Set an adapter with empty data
        mList.setAdapter(new MovieListAdapter(this));
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mList.setLayoutManager(manager);
    }

    private void queryMtdb(String query) {
        //TODO
        MTDbRestClient.get(MTDB_KEY, query/*"movie/now_playing"*/, null, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBytes) {
                super.onSuccess(statusCode, headers, responseBytes);
                Log.i("joy.onSuccess()", "another");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson = new Gson();
                Log.i("joy.onSuccess()", "responseString=" + responseString);
                mMovieGson = gson.fromJson(responseString, MovieGson.class);
                Log.i("joy.onSuccess()", "mMovieGson=" + mMovieGson);
//                mList.setAdapter(new MovieListAdapter(MovieViewer.this, mMovieGson));
                ((MovieListAdapter)mList.getAdapter()).setMovieGson(mMovieGson);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("joy.onFailure()", "damn");
            }
        });
    }


}
