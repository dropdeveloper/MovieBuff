package chat.dropdevelopers.com.moviebuff.main_activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.util.ArrayList;

import chat.dropdevelopers.com.moviebuff.R;
import chat.dropdevelopers.com.moviebuff.Utils.CenterZoomLayoutManager;
import chat.dropdevelopers.com.moviebuff.Utils.CustomLayoutManager;
import chat.dropdevelopers.com.moviebuff.adapters.StatusAdapter;
import chat.dropdevelopers.com.moviebuff.adapters.StatusCategoryAdapter;
import chat.dropdevelopers.com.moviebuff.adapters.VideoDetailAdapter;
import chat.dropdevelopers.com.moviebuff.model.StatusCateModel;
import chat.dropdevelopers.com.moviebuff.model.StatusModel;

public class VideoListActivity extends AppCompatActivity {

    private ArrayList<StatusModel> data;
    private static VideoDetailAdapter adapter;
   // private RecyclerView.LayoutManager layoutManager;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;

    String TAG = "REC POS";
    private Handler handler;
    String thump = Environment.getExternalStorageDirectory()+"/MovieBuff/.temp/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle("");
        recyclerView = findViewById(R.id.rec_view);
        layoutManager = new CustomLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        SnapHelper mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(recyclerView);
        handler = new Handler();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e(TAG, "STATE"+String.valueOf(newState));
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e(TAG, String.valueOf(dx + " - "+dy));
            }
        });

        data = new ArrayList<StatusModel>();
        data.add(new StatusModel("", "Abu", "","http://dropdevelopers.vcandu.com/a.mp4",10, "22",5, 5,thump+"image.png"));
        data.add(new StatusModel("", "Rim", "","http://dropdevelopers.vcandu.com/1537855313.mp4",20, "33",5, 5, thump+"image.png"));
        data.add(new StatusModel("", "Vishnu", "","http://dropdevelopers.vcandu.com/MOVBuff153.mp4",30, "11",5, 5, thump+"image.png"));
        data.add(new StatusModel("", "Jio", "","http://dropdevelopers.vcandu.com/1537855313.mp4",10, "2",5, 5,thump+"image.png"));
        data.add(new StatusModel("4", "Muhammed", "","http://dropdevelopers.vcandu.com/MOVBuff153.mp4",20, "44",5, 5,thump+"image.png"));
        data.add(new StatusModel("", "Biju", "","http://dropdevelopers.vcandu.com/MOVBuff153.mp4",30, "76",5, 5,thump+"image.png"));
        data.add(new StatusModel("", "Riju", "","http://dropdevelopers.vcandu.com/1537855313.mp4",10, "54",5, 5,thump+"image.png"));
        data.add(new StatusModel("", "Manu", "","http://dropdevelopers.vcandu.com/MOVBuff153.mp4",10, "43",5, 5,thump+"image.png"));


        adapter = new VideoDetailAdapter(recyclerView , layoutManager ,data, VideoListActivity.this);
        recyclerView.setAdapter(adapter);


      //  load("http://dropdevelopers.vcandu.com/MOVBuff153.mp4","image");



    }


    private void load(final String url, final String name){
        FFmpeg ffmpeg = FFmpeg.getInstance(getBaseContext());
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {

                @Override
                public void onStart() {
                    Toast.makeText(getApplicationContext(),"Start",Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure() {
                    Toast.makeText(getBaseContext(),"Fail",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess() {
                    addCammand(url, name);
                }

                @Override
                public void onFinish() {}
            });
        } catch (FFmpegNotSupportedException e) {
            // Handle if FFmpeg is not supported by device
        }
    }

    private void addCammand(String url, String name){
        FFmpeg ffmpeg = FFmpeg.getInstance(getBaseContext());
        String out_path = Environment.getExternalStorageDirectory()+"/MovieBuff/.temp/";
        try {

           // ffmpeg -i inputfile.mp4 -r 1 -an -t 12 -s 512x288 -vsync 1 -threads 4 image-%d.jpeg
            String[] cmd = {"-i", url,"-r","1", "-an","-t","1","-s","512x288","-vsync","1","-threads","4", out_path+name+".png"};

            ffmpeg.execute(cmd, new ExecuteBinaryResponseHandler() {

                @Override
                public void onStart() {}

                @Override
                public void onProgress(String message) {
                   // Toast.makeText(getApplicationContext(),"Progress",Toast.LENGTH_SHORT).show();
                    Log.e("PROGRESS", "WORKING.......");
                }

                @Override
                public void onFailure(String message) {
                    //Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    Log.e("FAIL VID",message);
                }

                @Override
                public void onSuccess(String message) {
                   // Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
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
}
