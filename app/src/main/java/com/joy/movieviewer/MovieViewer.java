package com.joy.movieviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;

import com.google.gson.Gson;
import com.joy.movieviewer.detail.MoviePlayerActivity;
import com.joy.movieviewer.item.MovieGson;
import com.joy.movieviewer.item.MovieVedioGson;
import com.loopj.android.http.TextHttpResponseHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static com.joy.movieviewer.detail.MoviePlayerActivity.BUNDLE_KEY_VIDEO_URL;

public class MovieViewer extends Activity {

    @BindView(R.id.list)
    RecyclerView mList;

    private String mMtdbApiKey;

    //    private RecyclerView mList;
    private MovieGson mMovieGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateResources();
        queryMtdb("movie/now_playing");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void updateResources() {
        mMtdbApiKey = getString(R.string.mtdb_api_key);

        ButterKnife.bind(this);
        // Set up the list view
        mList.setAdapter(new MovieListAdapter(this));
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mList.setLayoutManager(manager);
//        mList.addItemDecoration(new MovieListAdapter.MovieItemDecor(this));
    }

    private void queryMtdb(String query) {
        MTDbRestClient.get(mMtdbApiKey, query/*"movie/now_playing"*/, null, new TextHttpResponseHandler() {
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
            }
        });
    }
}
