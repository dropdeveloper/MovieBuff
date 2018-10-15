package com.example.mylibrary;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.VideoView;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class VideoPlayer extends LinearLayout {

    private ImageButton PlayButton, BackButton;
    private VideoView videoView;
    private LinearLayout loadView;
    private Handler handler;

    public VideoPlayer(Context context) {
        super(context);
        init();
    }

    public VideoPlayer(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.video_player, this);
        PlayButton = findViewById(R.id.bt_play);
        BackButton = findViewById(R.id.bt_back);
        videoView = findViewById(R.id.video_view);
        loadView = findViewById(R.id.load_view);

        PlayButton.setVisibility(GONE);

        handler = new Handler() {
            @Override
            public void publish(LogRecord record) {

            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {

            }
        };


        if (videoView.isPlaying()){
            BackButton.setVisibility(GONE);
        }else {
            BackButton.setVisibility(VISIBLE);
        }

        videoView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (BackButton.getVisibility() == GONE){
                    BackButton.setVisibility(VISIBLE);
                }else {
                    BackButton.setVisibility(GONE);
                }

                return false;
            }
        });

        videoView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PlayButton.getVisibility() == GONE){
                    PlayButton.setVisibility(VISIBLE);
                }else {
                    PlayButton.setVisibility(GONE);
                }


            }
        });



    }


    public void setVideo(String url){
        videoView.setVideoPath(url);
        videoView.start();
        loadView.setVisibility(VISIBLE);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                videoView.start();
                loadView.setVisibility(View.GONE);

            }

        });
    }


}
