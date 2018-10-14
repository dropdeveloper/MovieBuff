package chat.dropdevelopers.com.moviebuff.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;
import java.util.ArrayList;
import chat.dropdevelopers.com.moviebuff.R;
import chat.dropdevelopers.com.moviebuff.main_activity.VideoListActivity;
import chat.dropdevelopers.com.moviebuff.model.StatusModel;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.MyViewHolder> {

    private ArrayList<StatusModel> dataSet;
    private Context context;

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.demmi_status_v_item, viewGroup, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        myViewHolder.name.setText(dataSet.get(i).getName());
        myViewHolder.seen.setText(String.valueOf(dataSet.get(i).getSeen()));
        String link="http://www.vcandu.com//videoUploads//Uploads//%E0%B4%AE%E0%B4%B4%E0%B4%AF%E0%B5%87%20%E0%B4%A4%E0%B5%82%E0%B4%AE%E0%B4%B4%E0%B4%AF%E0%B5%87%20Malayalam.mp4";

        myViewHolder.videoView.setVideoPath(link);
        myViewHolder.videoView.start();
        myViewHolder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                myViewHolder.videoView.start();
                myViewHolder.videoView.pause();
            }
        });

        myViewHolder.videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, VideoListActivity.class);
                context.startActivity(i);
            }
        });

//        myViewHolder.play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (myViewHolder.videoView.isPlaying()){
//                    myViewHolder.videoView.pause();
//                }else {
//                    myViewHolder.videoView.start();
//                    myViewHolder.play.setVisibility(View.GONE);
//                }
//
//            }
//        });
//
//        myViewHolder.videoView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (myViewHolder.play.getVisibility() == View.GONE){
//                    myViewHolder.play.setVisibility(View.VISIBLE);
//                }else {
//                    myViewHolder.play.setVisibility(View.GONE);
//                }
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public StatusAdapter(ArrayList<StatusModel> dataSet, Context context){
        this.dataSet = dataSet;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView seen;
        VideoView videoView;
        ImageButton play;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name);
            seen = itemView.findViewById(R.id.tv_seen);
            videoView = itemView.findViewById(R.id.view_video);
            play = itemView.findViewById(R.id.bt_play);
        }
    }
}
