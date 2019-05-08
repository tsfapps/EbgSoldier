package com.earnbygame.ebgsoldier.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.earnbygame.ebgsoldier.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView mYouTubeView;
    private final String mKey = "AIzaSyBwXlnVmQ0lUvHjlFJ63hw5otPZYNKtTls";// TODO need to change
    private static final int RECOVERY_REQUEST = 1;
    private String videoUrl = "https://www.youtube.com/watch?v=giJZAYYrxlQ";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        mYouTubeView = findViewById(R.id.youtube_view);
        mYouTubeView.initialize(mKey, this);//TODO
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.cueVideo(getVideoId(videoUrl));
            youTubePlayer.play();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format("Youtube error", youTubeInitializationResult.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(mKey, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return mYouTubeView;
    }

    public String getVideoId(String url) {
        String videoId = url;
        if (url.contains("v=")) {
            String[] vUrl = url.split("v=");
            videoId = vUrl[1];
        }
        return videoId;
    }
}
