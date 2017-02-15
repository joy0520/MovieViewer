package com.joy.movieviewer.item;

import java.util.ArrayList;

/**
 * Created by joy05 on 2017/2/15.
 */

public class MovieGson {
    public int page;
    public ArrayList<Result> results = new ArrayList<>();

    public class Result {
        public String poster_path;
        public String overview;
        public String title;
        public float vote_average;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("MovieGson [");
        builder.append("page ").append(page);
        for (Result result : results) {
            builder.append("\n")
                    .append("poster_path : ").append(result.poster_path)
                    .append(", vote_average : ").append(result.vote_average)
                    .append(", title : ").append(result.title)
                    .append(", overview : ").append(result.overview)
                    .append("}");
        }
        builder.append("]");
        return builder.toString();
    }
}
