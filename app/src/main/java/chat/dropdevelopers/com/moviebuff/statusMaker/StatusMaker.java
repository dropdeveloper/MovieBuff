package chat.dropdevelopers.com.moviebuff.statusMaker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import chat.dropdevelopers.com.moviebuff.MusicPlayer.MusicPlayer;
import chat.dropdevelopers.com.moviebuff.R;
import chat.dropdevelopers.com.moviebuff.Utils.SharedHelper;
import chat.dropdevelopers.com.moviebuff.Utils.StringData;
import chat.dropdevelopers.com.moviebuff.service.VideoCut;

public class StatusMaker extends AppCompatActivity {

    private TextView StartTime, EndTime;
    private SeekBar seekBar;
    private ImageButton StatusMake;
    private Button CuttFirst, CuttSecond, Proceed;
    private VideoView Video;
    String file_path = "";
    String OutFileName;
    MediaController mediaController;
    private Handler mHandler;
    private MediaPlayer player;
    ProgressDialog progressDialog;

    private String CuttStartTime = "";
    private String EndStartTime = "";
    NotificationManager mNotifyManager;
    NotificationCompat.Builder mBuilder;
    HashMap<String, String> map;
    ArrayList<Integer> times;
    SharedHelper sharedHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_maker);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        progressDialog = new ProgressDialog(StatusMaker.this);
        StartTime = findViewById(R.id.tv_start);
        EndTime = findViewById(R.id.tv_end);
        seekBar = findViewById(R.id.seek);
        StatusMake = findViewById(R.id.bt_play);
        CuttFirst = findViewById(R.id.bt_cutt_first);
        CuttSecond = findViewById(R.id.bt_cutt_second);
        Proceed = findViewById(R.id.bt_cutt);
        Video = findViewById(R.id.video);
        mHandler = new Handler();
        makeDir(this);
        mediaController= new MediaController(this);
        times = new ArrayList<Integer>();
        map = new HashMap<>();
        sharedHelper = new SharedHelper(this);


        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        final String[] cmd = {"-i",""+file_path+"/video.mp4","-ss","00:00:00","-t","00:00:05",getOutPut()+"/MOVBuff"+ts+".mp4"};

        //Creating MediaController

        final String logoPath = Environment.getExternalStorageDirectory().toString()+"/moviebuff.png";

        Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();

                if (EndStartTime.equals("") && CuttStartTime.equals("")){

                }else {
                    final String[] cmd = {"-i", "" + file_path,"-i",""+logoPath,"-filter_complex","overlay=10:main_h-overlay_h-10","-ss", CuttStartTime, "-t", EndStartTime, getOutPut() + "/MOVBuff" + ts + ".mp4"};
                    LoadVideo(cmd);
                }
            }
        });


        StatusMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (times.size() > 0) {
                    JSONObject jsonObject = new JSONObject();
                    int size;
                    for (int i = 0; i < times.size(); i++) {
                        try {
                            jsonObject.put(String.valueOf(i), String.valueOf(times.get(i)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    Log.e("CUTTING STS", "Status");
                    sharedHelper.setBoolean(StringData.EN_CUT, true);
                    sharedHelper.putString(StringData.CUT_JSON, jsonObject.toString());
                    sharedHelper.setInt(StringData.CUT_COUNT, times.size() - 1);
                    sharedHelper.setInt(StringData.CUT_POS, 1);
                    getCommand(secToTime(times.get(0)), secToTime(times.get(1)), 1);
                    Log.e("CUTTING TIME :-", secToTime(times.get(0)) + " - " + secToTime(times.get(1)));
                }else {
                    Toast.makeText(getApplicationContext(),"Video is small size",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private void getCommand(String start, String end, int videoCounr){
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        final String logoPath = Environment.getExternalStorageDirectory().toString()+"/moviebuff.png";
        final String[] cmd = {"-i", "" + file_path,"-i",""+logoPath,"-filter_complex","overlay=10:main_h-overlay_h-10","-ss", start, "-t", end ,getOutPut() + "/MOVBuff" + ts + "-("+String.valueOf(videoCounr)+").mp4"};
        Log.e("TOTAL PART", String.valueOf(videoCounr));
        StatusCutter(cmd, videoCounr);
        Log.e("CUTTING TIME OG :-",start+" - "+end);

    }

    private String secToTime(int sec) {
        int seconds = sec % 60;
        int minutes = sec / 60;
        if (minutes >= 60) {
            int hours = minutes / 60;
            minutes %= 60;
            if( hours >= 24) {
                int days = hours / 24;
                return String.format("%d days %02d:%02d:%02d", days,hours%24, minutes, seconds);
            }
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
        return String.format("00:%02d:%02d", minutes, seconds);
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

    private void addCammand(final String[] cmd){
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
                    mNotifyManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mBuilder = new NotificationCompat.Builder(StatusMaker.this);
                    mBuilder.setContentTitle("Downloading File")
                            .setContentText("Creating...")
                            .setProgress(100, 0, false)
                            .setOngoing(true)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setPriority(Notification.PRIORITY_LOW);

                    // Initialize Objects here
                    //publishProgress("5");
                    mNotifyManager.notify(55, mBuilder.build());

                    progressDialog.setTitle("Creating...");
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    Log.e("FAIL VID",message);
                    progressDialog.dismiss();
                }

                @Override
                public void onSuccess(String message) {
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    Log.e("SUCSS VID",message);
                    progressDialog.dismiss();
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
            times.clear();
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
                             StartTime.setText(getTimes(c_pos));
                             EndTime.setText(getTimes(mediaPlayer.getDuration()));

                            long time =  TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getDuration());
                            String totalDuration = getTimes(mediaPlayer.getDuration());
                            System.out.println(totalDuration);


                            long val = 000000;
                            times.add((int)val);

                            for (int i = 0; i < time; i++){
                                System.out.print(i);

                                if (val < time){

                                    val = val+TimeUnit.SECONDS.toSeconds(30);
                                    Log.e("Estimate", String.valueOf(val));
                                    times.add((int)val);
                                }

                            }

                             StatusMaker.this.runOnUiThread(new Runnable() {

                                 @Override
                                 public void run() {
                                     if(mediaPlayer != null){
                                         try {
                                             int mCurrentPosition = mediaPlayer.getCurrentPosition();
                                             seekBar.setProgress(mCurrentPosition/1000);
                                             StartTime.setText(getTimes(mediaPlayer.getCurrentPosition()));
                                             long time =  TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getDuration());
                                             String totalDuration = getTimes(mediaPlayer.getDuration());
                                             System.out.println(totalDuration);

                                         }catch (IllegalStateException e){
                                            // Log.e("ERROR",e.getMessage());
                                         }



                                         CuttFirst.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 Log.e("CUTT 1",String.valueOf(getTimes(mediaPlayer.getCurrentPosition())));
                                                 CuttStartTime = getTimes(mediaPlayer.getCurrentPosition());
                                             }
                                         });


                                         CuttSecond.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 Log.e("CUTT 2",String.valueOf(getTimes(mediaPlayer.getCurrentPosition())));
                                                 EndStartTime = getTimes(mediaPlayer.getCurrentPosition());

                                             }
                                         });

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
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
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


    private String getTimes(int mCurrentPosition){
        long hour = TimeUnit.MILLISECONDS.toHours((long) mCurrentPosition);
        long minut = TimeUnit.MILLISECONDS.toMinutes((long) mCurrentPosition);
        long sec = TimeUnit.MILLISECONDS.toSeconds((long) mCurrentPosition) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mCurrentPosition));

       return String.format("%02d:%02d:%02d", hour, minut, sec);
    }



    private void StatusCutter(final String[] cmd, final int Count){
        player.stop();
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
                    Cutting(cmd, Count);
                }

                @Override
                public void onFinish() {}
            });
        } catch (FFmpegNotSupportedException e) {
            // Handle if FFmpeg is not supported by device
        }
    }


    private void Cutting(final String[] cmd, final int count){
        final FFmpeg ffmpeg = FFmpeg.getInstance(this);
        String file_path = Environment.getExternalStorageDirectory().getPath();
        try {
            // to execute "ffmpeg -version" command you just need to pass "-version"

            ffmpeg.execute(cmd, new ExecuteBinaryResponseHandler() {

                @Override
                public void onStart() {}

                @Override
                public void onProgress(String message) {
                    Toast.makeText(getApplicationContext(),"Progress",Toast.LENGTH_SHORT).show();
                   // Log.e("CUTTING PROGRE", message);
                    int total_part  = sharedHelper.getInt(StringData.CUT_COUNT);
                    progressDialog.setTitle("Creating("+String.valueOf(count)+"/"+String.valueOf(total_part)+")");
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    Log.e("FAIL VID",message);
                    progressDialog.dismiss();
                }

                @Override
                public void onSuccess(String message) {
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    Log.e("SUCSS VID",message);
                    progressDialog.dismiss();

                    ffmpeg.killRunningProcesses();

                    boolean status = sharedHelper.getBoolean(StringData.EN_CUT);
                    if (status && ! ffmpeg.isFFmpegCommandRunning()) {

                        int cut_size = sharedHelper.getInt(StringData.CUT_COUNT);
                        String Json = sharedHelper.getString(StringData.CUT_JSON);
                        try {
                            JSONObject jsonObject = new JSONObject(Json);
                            for (int i = 0; i < cut_size; i++){
                                times.add(jsonObject.getInt(String.valueOf(i + 1)));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        int position = sharedHelper.getInt(StringData.CUT_POS);
                        if (cut_size > position+1) {
                            String s_time;
                            String e_time;

                            if (position == 1){
                                s_time = secToTime(times.get(position));
                                e_time = secToTime(times.get(position + 1));
                            }else if (position == 2){
                                s_time = secToTime(times.get(position));
                                e_time = secToTime(times.get(position - 1));
                            }else {
                                s_time = secToTime(times.get(position));
                                e_time = secToTime(times.get(position - 1));
                            }

                           // e_time = secToTime(times.get(position));
                            Log.e("CUTTING -->", s_time +"-"+ e_time);
                            Log.e("VALUE - ", String.valueOf(times.get(position))+" - "+String.valueOf(times.get(position + 1)));
                            progressDialog.dismiss();
                            getCommand(s_time, e_time, position + 1);
                            sharedHelper.setInt(StringData.CUT_POS , position + 1);
                         }else {

                            String s_time = secToTime(times.get(position));
                            String e_time= secToTime(times.get(position));
                            // e_time = secToTime(times.get(position));
                            Log.e("CUTTING -->", s_time +"-"+ e_time);
                            Log.e("VALUE - ", String.valueOf(times.get(position))+" - "+String.valueOf(times.get(position + 1)));
                            progressDialog.dismiss();
                            getCommand(s_time, e_time, position + 1);
                            sharedHelper.setInt(StringData.CUT_POS , position + 1);
                            sharedHelper.setBoolean(StringData.EN_CUT, false);
                            Log.e("CUTTING -->","FINISH");
                        }
                    }else {
                        Log.e("CUTTING -->", "No data");
                        progressDialog.dismiss();
                    }

                }

                @Override
                public void onFinish() {}
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // Handle if FFmpeg is already running
            Log.e("FAIL VID",e.getMessage());
        }
    }



}