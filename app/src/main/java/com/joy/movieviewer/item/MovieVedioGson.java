package com.joy.movieviewer.item;

import java.util.ArrayList;

/**
 * Created by joy0520 on 2017/2/19.
 */

public class MovieVedioGson {
    public int id;
    public ArrayList<Result> results = new ArrayList<>();

    public class Result {
        public String id;
        public String key;
        public String name;
        public int size;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("MovieVedioGson [");
        builder.append("id : ").append(id);
        for (Result result : results) {
            builder.append("\n{")
                    .append("id : ").append(result.id)
                    .append(", key : ").append(result.key)
                    .append(", name : ").append(result.name)
                    .append(", size : ").append(result.size)
                    .append("}");
        }
        builder.append("]");
        return builder.toString();
    }
}
