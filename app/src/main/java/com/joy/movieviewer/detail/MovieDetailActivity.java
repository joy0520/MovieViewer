package com.joy.movieviewer.detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.joy.movieviewer.MTDbRestClient;
import com.joy.movieviewer.MovieListAdapter;
import com.joy.movieviewer.R;
import com.joy.movieviewer.item.MovieVedioGson;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static com.joy.movieviewer.detail.MoviePlayerActivity.BUNDLE_KEY_VIDEO_URL;

/**
 * Created by joy0520 on 2017/2/19.
 */

public class MovieDetailActivity extends YouTubeBaseActivity {
    public static final String EXTRA_MOVIE_ID = "detail_activity_extra_movie_id";
    public static final String EXTRA_PREVIEW_URL = "detail_activity_extra_preview_image";
    public static final String EXTRA_TITLE = "detail_activity_extra_title";
    public static final String EXTRA_RATING = "detail_activity_extra_rating";
    public static final String EXTRA_OVERVIEW = "detail_activity_extra_overview";
    public static final String EXTRA_POPULARITY = "detail_activity_extra_popularity";

    @BindView(R.id.backdrop)
    ImageView mBackdrop;
//    @BindView(R.id.ic_play)
//    ImageView mPlayIcon;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.rating_bar)
    RatingBar mRatingBar;
    @BindView(R.id.rating)
    TextView mRating;
    @BindView(R.id.overview)
    TextView mOverview;
    @BindView(R.id.popularity)
    TextView mPopularity;

    private int mMovieId;
    private String mVideoKey;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        mMovieId = getIntent().getExtras().getInt(EXTRA_MOVIE_ID);
        String imageUrl = MovieListAdapter.getFullImageUrl(getIntent().getExtras().getString(EXTRA_PREVIEW_URL));
        Log.i("detail.onCreate()", ""+imageUrl);

        Picasso.with(this)
                .load(imageUrl)
                .fit()
                .centerInside()
                .into(mBackdrop, new Callback() {
                    @Override
                    public void onSuccess() {
//                        mPlayIcon.bringToFront();
                        Log.i("detail.oncreate.onSuccess()", "++");
                    }

                    @Override
                    public void onError() {
                        Log.i("detail.oncreate.onError()", "++");
                    }
                });
        mBackdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryMtdbYoutubeMovieKey(mMovieId);
            }
        });
        mTitle.setText(getIntent().getExtras().getString(EXTRA_TITLE));
//        mRatingBar.setNumStars(getIntent().getExtras().getInt(EXTRA_RATING));
        mRating.setText(String.format("%f", getIntent().getExtras().getFloat(EXTRA_RATING)));
        mOverview.setText(getIntent().getExtras().getString(EXTRA_OVERVIEW));
        mPopularity.setText(Float.toString(getIntent().getExtras().getFloat(EXTRA_POPULARITY)));
    }


    private void queryMtdbYoutubeMovieKey(int movieId) {
        MTDbRestClient.getYoutubeMovieKey(getString(R.string.mtdb_api_key),
                movieId, null, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.i("joy.onFailure()", "responseString=" + responseString);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Gson gson = new Gson();
                        Log.i("joy.onSuccess()", "responseString=" + responseString);
                        MovieVedioGson movieVedioGson = gson.fromJson(responseString, MovieVedioGson.class);
                        Log.i("joy.onSuccess()", "movieVedioGson=" + movieVedioGson);

                        Intent intent = new Intent(MovieDetailActivity.this, MoviePlayerActivity.class);
                        intent.putExtra(BUNDLE_KEY_VIDEO_URL, movieVedioGson.results.get(0).key);
                        startActivity(intent);
                    }
                });

    }
}
