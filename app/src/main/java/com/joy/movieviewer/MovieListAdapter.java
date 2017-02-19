package com.joy.movieviewer;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.joy.movieviewer.detail.MoviePlayerActivity;
import com.joy.movieviewer.item.MovieGson;
import com.joy.movieviewer.item.MovieVedioGson;
import com.loopj.android.http.TextHttpResponseHandler;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static com.joy.movieviewer.detail.MoviePlayerActivity.BUNDLE_KEY_VIDEO_URL;

/**
 * Created by joy0520 on 2017/2/16.
 */

public class MovieListAdapter extends RecyclerView.Adapter {
    public static final int ITEM_TYPE_NORMAL = 0;
    public static final int ITEM_TYPE_HOT = 1;

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.container)
        View continaer;
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.image_progress)
        ProgressBar imageProgress;
        @Nullable @BindView(R.id.title)
        TextView title;
        @Nullable @BindView(R.id.overview)
        TextView overview;
        @Nullable @BindView(R.id.ic_play)
        ImageView ic_play;

        MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class MovieItemDecor extends RecyclerView.ItemDecoration {
        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
        }
    }

    private Context mContext;
    private List<MovieGson.Result> mMovies;

    public MovieListAdapter(Context context) {
        mMovies = new ArrayList<>();
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // TODO different item layout due to a movie's rate
        View view = LayoutInflater.from(mContext).inflate(
                viewType == ITEM_TYPE_NORMAL ? R.layout.item_movie : R.layout.item_movie_hot
                , parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mMovies == null) return;
        final MovieGson.Result movie = mMovies.get(position);
        if (holder instanceof MovieViewHolder) {
            final MovieViewHolder movieHolder = (MovieViewHolder) holder;
            // Load different image by orientation.
            String imageUrl = mContext.getResources().getConfiguration().orientation
                    == Configuration.ORIENTATION_PORTRAIT ?
                    movie.poster_path : movie.backdrop_path;
            Transformation transformation = new RoundedTransformationBuilder()
                    .borderColor(Color.WHITE)
                    .borderWidthDp(3)
                    .oval(false)
                    .build();
            // Load image using Picasso library.
            Picasso.with(mContext)
                    .load(getFullImageUrl(imageUrl))
                    .fit()
                    .centerInside()
                    .transform(transformation) // rounded corner transformation
                    .placeholder(R.drawable.gohan)
                    .error(R.drawable.gohan)
                    .into(movieHolder.image, new Callback() {
                        @Override
                        public void onSuccess() {
                            // Hide progress bar once the image loaded successfully.
                            movieHolder.imageProgress.setVisibility(View.GONE);
                            if (movie.vote_average >= MovieGson.HOT_MOVIE_VOTE_CRITERIA
                                    && movieHolder.ic_play != null) {
                                movieHolder.ic_play.bringToFront();
                            }
                        }

                        @Override
                        public void onError() {
                            // do nothing
                        }
                    });

            if (movie.vote_average < MovieGson.HOT_MOVIE_VOTE_CRITERIA) {
                movieHolder.title.setText(movie.title);
                movieHolder.overview.setText(movie.overview);
            }
            // Set item click listener
            movieHolder.continaer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("movieHolder.overview", "onClick");
                    queryMtdbYoutubeMovieKey(movie.id);
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mMovies.get(position).vote_average < MovieGson.HOT_MOVIE_VOTE_CRITERIA)
            return ITEM_TYPE_NORMAL;
        else return ITEM_TYPE_HOT;
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) return 0;
        return mMovies.size();
    }

    public void setMovieGson(MovieGson gson) {
        Log.i("joy.MovieListAdapter.setMovieGson()", "gson=" + gson);
        mMovies = gson.results;
        notifyDataSetChanged();
    }

    private String getFullImageUrl(String imageUrl) {
        return "https://image.tmdb.org/t/p/original" + imageUrl;
    }

    private void queryMtdbYoutubeMovieKey(int movieId) {
        MTDbRestClient.getYoutubeMovieKey(mContext.getString(R.string.mtdb_api_key),
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

                // TODO Go to movie detail activity or movie player activity
                Intent intent = new Intent(mContext, MoviePlayerActivity.class);
                intent.putExtra(BUNDLE_KEY_VIDEO_URL, movieVedioGson.results.get(0).key);
                mContext.startActivity(intent);
            }
        });

    }
}
