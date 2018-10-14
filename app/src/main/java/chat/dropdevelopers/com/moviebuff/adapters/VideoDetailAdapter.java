package chat.dropdevelopers.com.moviebuff.adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.mylibrary.Loading;

import java.util.ArrayList;

import chat.dropdevelopers.com.moviebuff.R;
import chat.dropdevelopers.com.moviebuff.model.StatusModel;

public class VideoDetailAdapter extends RecyclerView.Adapter<VideoDetailAdapter.MyViewHolder>{

    private ArrayList<StatusModel> dataSet;
    private Context context;

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

        String link="http://www.vcandu.com//videoUploads//Uploads//%E0%B4%AE%E0%B4%B4%E0%B4%AF%E0%B5%87%20%E0%B4%A4%E0%B5%82%E0%B4%AE%E0%B4%B4%E0%B4%AF%E0%B5%87%20Malayalam.mp4";

        //loading.setLoading(false);



        myViewHolder.video.setVideoPath(link);
        myViewHolder.video.start();
        myViewHolder.video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                myViewHolder.video.start();
                myViewHolder.loading.setVisibility(View.GONE);

            }

        });

        final MediaPlayer.OnInfoListener onInfoToPlayStateListener = new MediaPlayer.OnInfoListener() {

            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START: {
                       // mProgressBar.setVisibility(View.GONE);
                        myViewHolder.loading.setVisibility(View.GONE);
                        return true;
                    }
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START: {
                        //mProgressBar.setVisibility(View.VISIBLE);
                        myViewHolder.loading.setVisibility(View.VISIBLE);
                        return true;
                    }
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END: {
                        //mProgressBar.setVisibility(View.VISIBLE);
                        myViewHolder.loading.setVisibility(View.GONE);
                        return true;
                    }
                }
                return false;
            }

        };

        myViewHolder.video.setOnInfoListener(onInfoToPlayStateListener);

        myViewHolder.video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (myViewHolder.video.isPlaying()){
                    myViewHolder.video.pause();
                    myViewHolder.video.setOnInfoListener(onInfoToPlayStateListener);
                }else {
                    myViewHolder.video.start();
                    myViewHolder.video.setOnInfoListener(onInfoToPlayStateListener);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public VideoDetailAdapter(ArrayList<StatusModel> dataSet , Context context){
        this.dataSet = dataSet;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView seen;
        TextView like;
        TextView unLike;
        VideoView video;
        LinearLayout loading;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name);
            seen = itemView.findViewById(R.id.tv_viewers);
            like = itemView.findViewById(R.id.tv_like);
            unLike = itemView.findViewById(R.id.tv_dislike);
            video = itemView.findViewById(R.id.view_video);
            loading = itemView.findViewById(R.id.view_loading);

        }
    }
}
