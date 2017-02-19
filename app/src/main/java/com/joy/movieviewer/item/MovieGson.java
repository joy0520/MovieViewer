package com.joy.movieviewer.item;

import java.util.ArrayList;

/**
 * Created by joy0520 on 2017/2/15.
 */

public class MovieGson {
    public static final int HOT_MOVIE_VOTE_CRITERIA = 7;
    public int page;
    public ArrayList<Result> results = new ArrayList<>();

    public class Result {
        public String poster_path;
        public String backdrop_path;
        public String overview;
        public String title;
        public float vote_average;
        public int id;
        public float popularity;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("MovieGson [");
        builder.append("page ").append(page);
        for (Result result : results) {
            builder.append("\n{")
                    .append("poster_path : ").append(result.poster_path)
                    .append(", backdrop_path : ").append(result.backdrop_path)
                    .append(", vote_average : ").append(result.vote_average)
                    .append(", title : ").append(result.title)
                    .append(", overview : ").append(result.overview)
                    .append(", id : ").append(result.id)
                    .append(", popularity : ").append(result.popularity)
                    .append("}");
        }
        builder.append("]");
        return builder.toString();
    }
}
