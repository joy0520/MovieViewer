package com.joy.movieviewer.detail;

import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.joy.movieviewer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by joy0520 on 2017/2/19.
 */

public class MoviePlayerActivity extends YouTubeBaseActivity {
    public static final String BUNDLE_KEY_VIDEO_URL = "movie_player_activity_bundle_key_video_url";

    private String mVideoKey = null;
    @BindView(R.id.player)
    YouTubePlayerView mPlayer;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_movie_player);
        ButterKnife.bind(this);

        mVideoKey = getIntent().getExtras().getString(BUNDLE_KEY_VIDEO_URL);

        if (mVideoKey != null && !mVideoKey.isEmpty()) {
            mPlayer.initialize(getResources().getString(R.string.youtube_api_key),
                    new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                            if (mVideoKey != null || !mVideoKey.isEmpty()) {
                                youTubePlayer.setFullscreen(true);
                                youTubePlayer.cueVideo(mVideoKey);
                            }
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                            Log.i("MoviePlayerActivity.onInitializationFailure()",
                                    "youTubeInitializationResult="+youTubeInitializationResult);
                        }
                    });
        }
    }
}
