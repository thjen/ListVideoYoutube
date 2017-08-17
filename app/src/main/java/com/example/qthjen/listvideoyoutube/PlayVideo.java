package com.example.qthjen.listvideoyoutube;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayVideo extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    YouTubePlayerView youTubePlayerView;
    int REQUEST_VIDEO = 1;
    String idVideo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.ytPlayer);

        Intent intent = getIntent();
        idVideo = intent.getStringExtra("idVideoYt");

        youTubePlayerView.initialize(MainActivity.API_KEY, PlayVideo.this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        youTubePlayer.loadVideo(idVideo);
        youTubePlayer.setFullscreen(true);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        if ( youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, REQUEST_VIDEO);
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ( requestCode == REQUEST_VIDEO ) {
            youTubePlayerView.initialize(MainActivity.API_KEY, PlayVideo.this);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
