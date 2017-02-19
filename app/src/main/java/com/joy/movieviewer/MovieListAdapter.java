package com.joy.movieviewer;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.joy.movieviewer.detail.MovieDetailActivity;
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
        @Nullable
        @BindView(R.id.title)
        TextView title;
        @Nullable
        @BindView(R.id.overview)
        TextView overview;
        @Nullable
        @BindView(R.id.ic_play)
        ImageView ic_play;

        MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

//    static class MovieItemDecor extends RecyclerView.ItemDecoration {
//        private Drawable drawable;
//        private Context context;
//
//        public MovieItemDecor(Context context) {
//            this.context = context;
//            drawable = context.getResources().getDrawable(R.drawable.list_item_odd_bg);
//        }
//
//        @Override
//        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
////            super.onDrawOver(c, parent, state);
//            final int left = parent.getPaddingLeft();
//            final int right = parent.getWidth() - parent.getPaddingRight();
//
//            final int childCount = parent.getChildCount();
//            for (int i = 0; i < childCount; i++) {
//                if (i % 2 != 0) { // odd items
//                    final View child = parent.getChildAt(i);
//                    final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
//                            .getLayoutParams();
//                    final int top = child.getTop();
//                    final int bottom = child.getBottom();
//                    drawable.setBounds(left, top, right, bottom);
//                    drawable.draw(c);
//                }
//            }
//        }
//    }

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
                    if (movie.vote_average < MovieGson.HOT_MOVIE_VOTE_CRITERIA) { // normal movie
                        Intent intent = new Intent(mContext, MovieDetailActivity.class);
                        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movie.id);
                        intent.putExtra(MovieDetailActivity.EXTRA_PREVIEW_URL, movie.backdrop_path);
                        intent.putExtra(MovieDetailActivity.EXTRA_TITLE, movie.title);
                        intent.putExtra(MovieDetailActivity.EXTRA_RATING, movie.vote_average);
                        intent.putExtra(MovieDetailActivity.EXTRA_OVERVIEW, movie.overview);
                        intent.putExtra(MovieDetailActivity.EXTRA_POPULARITY, movie.popularity);
                        mContext.startActivity(intent);
                    } else { // popular movie
                        queryMtdbYoutubeMovieKey(movie.id);
                    }
                }
            });
            // Different item bg for odd items
            if (position % 2 != 0) {
                movieHolder.continaer.setBackgroundColor(mContext.getResources().getColor(R.color.list_item_odd_bg));
            }
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

    public static String getFullImageUrl(String imageUrl) {
        return "https://image.tmdb.org/t/p/w780" + imageUrl;
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

                        Intent intent = new Intent(mContext, MoviePlayerActivity.class);
                        intent.putExtra(BUNDLE_KEY_VIDEO_URL, movieVedioGson.results.get(0).key);
                        mContext.startActivity(intent);
                    }
                });

    }
}
