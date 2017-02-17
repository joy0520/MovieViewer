package com.joy.movieviewer;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joy.movieviewer.item.MovieGson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy0520 on 2017/2/16.
 */

public class MovieListAdapter extends RecyclerView.Adapter {

    private static class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title, overview;

        MovieViewHolder(View itemView) {
            super(itemView);
            if (itemView instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) itemView;
                image = (ImageView) viewGroup.findViewById(R.id.image);
                title = (TextView) viewGroup.findViewById(R.id.title);
                overview = (TextView) viewGroup.findViewById(R.id.overview);
            }
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
        // TODO different item layout due to orientation
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mMovies == null) return;
        MovieGson.Result movie = mMovies.get(position);
        if (holder instanceof MovieViewHolder) {
            MovieViewHolder movieHolder = (MovieViewHolder) holder;
            // Load different image by orientation.
            String imageUrl = mContext.getResources().getConfiguration().orientation
                    == Configuration.ORIENTATION_PORTRAIT ?
                    movie.poster_path : movie.backdrop_path;
            // Load image using Picasso library.
            Picasso.with(mContext)
                    .load(getFullImageUrl(imageUrl))
                    .fit()
                    .centerInside()
                    .placeholder(R.drawable.gohan)
                    .error(R.drawable.gohan)
                    .into(movieHolder.image);

            movieHolder.title.setText(movie.title);
            movieHolder.overview.setText(movie.overview);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
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
}
