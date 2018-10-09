package chat.dropdevelopers.com.moviebuff.statusMaker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.io.File;
import java.util.concurrent.TimeUnit;

import chat.dropdevelopers.com.moviebuff.MusicPlayer.MusicPlayer;
import chat.dropdevelopers.com.moviebuff.R;

public class StatusMaker extends AppCompatActivity {

    private TextView StartTime, EndTime;
    private SeekBar seekBar;
    private ImageButton PlayButton;
    private Button CuttFirst, CuttSecond, Proceed;
    private VideoView Video;
    String file_path = "";
    String OutFileName;
    MediaController mediaController;
    private Handler mHandler;
    private MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_maker);

        StartTime = findViewById(R.id.tv_start);
        EndTime = findViewById(R.id.tv_end);
        seekBar = findViewById(R.id.seek);
        PlayButton = findViewById(R.id.bt_play);
        CuttFirst = findViewById(R.id.bt_cutt_first);
        CuttSecond = findViewById(R.id.bt_cutt_second);
        Proceed = findViewById(R.id.bt_cutt);
        Video = findViewById(R.id.video);
        mHandler = new Handler();
        makeDir(this);
        mediaController= new MediaController(this);

        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        final String[] cmd = {"-i",""+file_path+"/video.mp4","-ss","00:00:00","-t","00:00:05",getOutPut()+"/MOVBuff"+ts+".mp4"};

        //Creating MediaController


        Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadVideo(cmd);
            }
        });


//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if(player != null && fromUser){
//                    Video.seekTo(progress * 100);
//                }
//            }
//        });
    }



    private void LoadVideo(final String[] cmd){
        FFmpeg ffmpeg = FFmpeg.getInstance(this);
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {

                @Override
                public void onStart() {
                    Toast.makeText(getApplicationContext(),"Start",Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure() {
                    Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess() {
                    addCammand(cmd);
                }

                @Override
                public void onFinish() {}
            });
        } catch (FFmpegNotSupportedException e) {
            // Handle if FFmpeg is not supported by device
        }
    }

    private void addCammand(String[] cmd){
        FFmpeg ffmpeg = FFmpeg.getInstance(this);
        String file_path = Environment.getExternalStorageDirectory().getPath();
        try {
            // to execute "ffmpeg -version" command you just need to pass "-version"

            ffmpeg.execute(cmd, new ExecuteBinaryResponseHandler() {

                @Override
                public void onStart() {}

                @Override
                public void onProgress(String message) {
                    Toast.makeText(getApplicationContext(),"Progress",Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    Log.e("FAIL VID",message);
                }

                @Override
                public void onSuccess(String message) {
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    Log.e("SUCSS VID",message);
                }

                @Override
                public void onFinish() {}
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // Handle if FFmpeg is already running
            Log.e("FAIL VID",e.getMessage());
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select_video, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.bt_add) {
            if (Video.isPlaying()){
                Video.stopPlayback();
            }
            Intent intent = new Intent();
            intent.setType("video/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select Video"),23);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 23) {
                Uri selectedImageUri = data.getData();

                // OI FILE Manager
               String filemanagerstring = selectedImageUri.getPath();

                // MEDIA GALLERY
               String selectedImagePath = getPath(selectedImageUri);
                if (selectedImagePath != null) {
                   file_path = selectedImagePath;
                    Video.setVideoURI(Uri.parse(file_path));
                    Video.start();
                    Video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(final MediaPlayer mediaPlayer) {
                            mediaPlayer.setLooping(true);
                            final int c_pos = mediaPlayer.getCurrentPosition();
                             seekBar.setMax(mediaPlayer.getDuration() /1000);
                             seekBar.setProgress(c_pos / 1000);
                             player = mediaPlayer;
                             StartTime.setText(millisecondsToString(c_pos));
                             EndTime.setText(millisecondsToString(mediaPlayer.getDuration()));

                             StatusMaker.this.runOnUiThread(new Runnable() {

                                 @Override
                                 public void run() {
                                     if(mediaPlayer != null){
                                         int mCurrentPosition = mediaPlayer.getCurrentPosition();
                                         seekBar.setProgress(mCurrentPosition/1000);
                                         StartTime.setText(millisecondsToString(mediaPlayer.getCurrentPosition()));

                                         seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                                             @Override
                                             public void onStopTrackingTouch(SeekBar seekBar) {

                                             }

                                             @Override
                                             public void onStartTrackingTouch(SeekBar seekBar) {

                                             }

                                             @Override
                                             public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                                 if(mediaPlayer != null && fromUser){
                                                     mediaPlayer.seekTo(progress * 1000);
                                                     Log.e("POS",String.valueOf(progress*1000));
                                                 }
                                             }
                                         });
                                     }
                                     mHandler.postDelayed(this, 1000);
                                 }
                             });
                        }
                    });
                }
            }
        }
    }

    // UPDATED!
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                                       .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }


    public void makeDir(Context context) {
        // Find the dir to save cached images
        File dir;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            dir = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "MovieBuff/Status");
        else
            dir = context.getCacheDir();
        if (!dir.exists())
            dir.mkdirs();

    }

    private String getOutPut(){
        String path = Environment.getExternalStorageDirectory().getPath()+"/MovieBuff/Status";
        return path;
    }

    private String millisecondsToString(int milliseconds)  {
        long minutes = TimeUnit.MILLISECONDS.toMinutes((long) milliseconds);
        long seconds =  TimeUnit.MILLISECONDS.toSeconds((long) milliseconds) ;
        long hours = TimeUnit.HOURS.toHours((long) milliseconds);
        return minutes+":"+ seconds;
    }


}