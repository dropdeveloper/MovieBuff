package chat.dropdevelopers.com.moviebuff.main_activity;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;

import java.util.ArrayList;

import chat.dropdevelopers.com.moviebuff.R;
import chat.dropdevelopers.com.moviebuff.Utils.CenterZoomLayoutManager;
import chat.dropdevelopers.com.moviebuff.adapters.StatusAdapter;
import chat.dropdevelopers.com.moviebuff.adapters.StatusCategoryAdapter;
import chat.dropdevelopers.com.moviebuff.adapters.VideoDetailAdapter;
import chat.dropdevelopers.com.moviebuff.model.StatusCateModel;
import chat.dropdevelopers.com.moviebuff.model.StatusModel;

public class VideoListActivity extends AppCompatActivity {

    private ArrayList<StatusModel> data;
    private static VideoDetailAdapter adapter;
   // private RecyclerView.LayoutManager layoutManager;
    private CenterZoomLayoutManager layoutManager;
    private RecyclerView recyclerView;

    String TAG = "REC POS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        setTitle("");
        recyclerView = findViewById(R.id.rec_view);
        layoutManager = new CenterZoomLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

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
        data.add(new StatusModel("", "Abu", "","http://dropdevelopers.vcandu.com/MOVBuff153.mp4",10, "",5, 5));
        data.add(new StatusModel("", "Rim", "","http://dropdevelopers.vcandu.com/1537855313.mp4",20, "",5, 5));
        data.add(new StatusModel("", "Vishnu", "","http://dropdevelopers.vcandu.com/MOVBuff153.mp4",30, "",5, 5));
        data.add(new StatusModel("", "Jio", "","http://dropdevelopers.vcandu.com/1537855313.mp4",10, "",5, 5));
        data.add(new StatusModel("", "Muhammed", "","http://dropdevelopers.vcandu.com/MOVBuff153.mp4",20, "",5, 5));
        data.add(new StatusModel("", "Biju", "","http://dropdevelopers.vcandu.com/MOVBuff153.mp4",30, "",5, 5));
        data.add(new StatusModel("", "Riju", "","http://dropdevelopers.vcandu.com/1537855313.mp4",10, "",5, 5));
        data.add(new StatusModel("", "Manu", "","http://dropdevelopers.vcandu.com/MOVBuff153.mp4",10, "",5, 5));


        adapter = new VideoDetailAdapter(data, VideoListActivity.this);
        recyclerView.setAdapter(adapter);

    }
}
