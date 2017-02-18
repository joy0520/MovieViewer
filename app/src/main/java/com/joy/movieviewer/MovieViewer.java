package com.joy.movieviewer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.joy.movieviewer.item.MovieGson;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MovieViewer extends Activity {

    private String mMtdbApiKey;

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

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void updateResources() {
        mMtdbApiKey = getString(R.string.mtdb_api_key);

        mList = (RecyclerView) findViewById(R.id.list);
        // Set an adapter with empty data
        mList.setAdapter(new MovieListAdapter(this));
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mList.setLayoutManager(manager);
    }

    private void queryMtdb(String query) {
        //TODO
        MTDbRestClient.get(mMtdbApiKey, query/*"movie/now_playing"*/, null, new TextHttpResponseHandler() {
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
                // Give list updated data.
                ((MovieListAdapter) mList.getAdapter()).setMovieGson(mMovieGson);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("joy.onFailure()", "damn");
            }
        });
    }


}
