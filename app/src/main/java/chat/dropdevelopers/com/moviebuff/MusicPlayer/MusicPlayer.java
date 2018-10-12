package chat.dropdevelopers.com.moviebuff.MusicPlayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import chat.dropdevelopers.com.moviebuff.R;
import chat.dropdevelopers.com.moviebuff.Utils.StringData;

public class MusicPlayer extends AppCompatActivity {

    private MediaPlayer player;
    private SeekBar seekBar;
    private TextView title, startTime, endTime;
    private ImageButton play, next, revers;
    String filePath;
    private boolean playBack = false;
    private Handler mHandler;
    private double starttime = 0;
    private double finaltime = 0;
    private String song_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        title = findViewById(R.id.tv_songe_title);
        startTime = findViewById(R.id.tv_time_start);
        endTime = findViewById(R.id.tv_time_end);
        play = findViewById(R.id.bt_play);
        next = findViewById(R.id.bt_next);
        revers = findViewById(R.id.bt_back);
        mHandler = new Handler();
        seekBar = findViewById(R.id.seek);

        Intent i = getIntent();
        filePath = i.getStringExtra(StringData.FILE_URL);
        song_name = i.getStringExtra(StringData.FILE_NAME);
        player = MediaPlayer.create(this, Uri.parse(filePath));
        player.setLooping(true);
        player.start();

        finaltime = player.getDuration();
        starttime = player.getCurrentPosition();
        title.setText(song_name);
        title.setSelected(true);


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playBack){
                    player.start();
                    play.setImageResource(R.drawable.ic_play_button);
                    playBack = false;
                }else{
                    playBack = true;
                    player.pause();
                    play.setImageResource(R.drawable.ic_stop_button);
                }
            }
        });

        endTime.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) finaltime),
                TimeUnit.MILLISECONDS.toSeconds((long) finaltime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                finaltime)))
        );


        MusicPlayer.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if(player != null){
                    int mCurrentPosition = player.getCurrentPosition();
                    seekBar.setProgress(mCurrentPosition/1000);

                    startTime.setText(String.format("%d:%d",
                            TimeUnit.MILLISECONDS.toMinutes((long) mCurrentPosition),
                            TimeUnit.MILLISECONDS.toSeconds((long) mCurrentPosition) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                            toMinutes((long) mCurrentPosition)))
                    );

                }
                mHandler.postDelayed(this, 100);
            }
        });

//
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(player != null && fromUser){
                    player.seekTo(progress * 100);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (player.isPlaying()){
            player.stop();
            finish();
        }else {
            finish();
        }

    }
}
