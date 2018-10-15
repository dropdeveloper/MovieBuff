package chat.dropdevelopers.com.moviebuff.adapters;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.mylibrary.Loading;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import chat.dropdevelopers.com.moviebuff.R;
import chat.dropdevelopers.com.moviebuff.Utils.CustomLayoutManager;
import chat.dropdevelopers.com.moviebuff.main_activity.VideoListActivity;
import chat.dropdevelopers.com.moviebuff.model.StatusModel;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

import static cn.jzvd.Jzvd.SCREEN_WINDOW_NORMAL;

    public class VideoDetailAdapter extends RecyclerView.Adapter<VideoDetailAdapter.MyViewHolder> {

    private ArrayList<StatusModel> dataSet;
    private Context context;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.demmi_full_video, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        myViewHolder.seen.setText(String.valueOf(dataSet.get(i).getSeen()));
        myViewHolder.unLike.setText(String.valueOf(dataSet.get(i).getUnLike()));
        myViewHolder.like.setText(String.valueOf(dataSet.get(i).getLike()));
        myViewHolder.name.setText(String.valueOf(dataSet.get(i).getName()));


        dataSet.get(i).setPlayer(ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(context), new DefaultTrackSelector(), new DefaultLoadControl()));

        myViewHolder.mExoPlayer.setPlayer(dataSet.get(i).getPlayer());

         dataSet.get(i).getPlayer().setPlayWhenReady(dataSet.get(i).getPlayWhenReady());
         dataSet.get(i).getPlayer().seekTo(dataSet.get(i).getCurrentWindow(), dataSet.get(i).getPlaybackPosition());

        Uri uri = Uri.parse(dataSet.get(i).getVideo_url());
        MediaSource mediaSource = buildMediaSource(uri);
        dataSet.get(i).getPlayer().prepare(mediaSource, true, false);
        dataSet.get(i).getPlayer().setPlayWhenReady(true);
        myViewHolder.mExoPlayer.setUseController(false);


        int x =layoutManager.findFirstVisibleItemPosition();
//        dataSet.get(oldPos).getPlayer().stop();




    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public void onViewRecycled(@NonNull MyViewHolder holder) {
        int position = holder.getAdapterPosition();
        if (dataSet.get(position) != null) {
            dataSet.get(position).getPlayer().release();
        }
        super.onViewRecycled(holder);
    }

    public VideoDetailAdapter(RecyclerView recyclerView, LinearLayoutManager layoutManager , ArrayList<StatusModel> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
        this.layoutManager = layoutManager;
        this.recyclerView = recyclerView;
    }



    private MediaSource buildMediaSource(Uri uri) {

        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).createMediaSource(uri);
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView seen;
        TextView like;
        TextView unLike;
        JzvdStd video;
        LinearLayout loading;
        public PlayerView mExoPlayer;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name);
            seen = itemView.findViewById(R.id.tv_viewers);
            like = itemView.findViewById(R.id.tv_like);
            unLike = itemView.findViewById(R.id.tv_dislike);
            video = itemView.findViewById(R.id.view_video);
            loading = itemView.findViewById(R.id.view_loading);
            mExoPlayer = itemView.findViewById(R.id.player_view);
        }



    }





}
