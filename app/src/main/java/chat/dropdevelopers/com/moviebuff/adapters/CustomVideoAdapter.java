package chat.dropdevelopers.com.moviebuff.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import chat.dropdevelopers.com.moviebuff.R;
import chat.dropdevelopers.com.moviebuff.Utils.StringData;
import chat.dropdevelopers.com.moviebuff.imageLoader.ImageLoader;
import chat.dropdevelopers.com.moviebuff.main_activity.VideoDetailView;
import chat.dropdevelopers.com.moviebuff.model.VideoModel;

public class CustomVideoAdapter extends RecyclerView.Adapter<CustomVideoAdapter.MyViewHolder> {

    private ArrayList<VideoModel> dataSet;
    private Context context;
    private ImageLoader imageLoader;

    public CustomVideoAdapter(ArrayList<VideoModel> dataSet, Context context){
        this.dataSet = dataSet;
        this.context = context;
        imageLoader = new ImageLoader(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.demmi_list_item, viewGroup, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int i) {

        holder.title.setText(dataSet.get(i).getName());
        holder.desc.setText(dataSet.get(i).getDisc());
        holder.like_count.setText(String.valueOf(dataSet.get(i).getLike()));
        holder.dislike_count.setText(String.valueOf(dataSet.get(i).getDislike()));
        String link = dataSet.get(i).getImage_tem_url();
        ImageView imageView = holder.tempView;
        Glide.with(context)
                .load(link)
                .into(imageView);
        final String name = dataSet.get(i).getName();
        final String desc = dataSet.get(i).getDisc();
        final String image = dataSet.get(i).getImage_tem_url();
        final int like = dataSet.get(i).getLike();
        final int dislike = dataSet.get(i).getDislike();
        final int seen = dataSet.get(i).getViewer();
        final String video = dataSet.get(i).getVideo_url();

        holder.mianView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, VideoDetailView.class);
                JSONObject json = new JSONObject();
                try {
                    json.put(StringData.VIDIO_TITLE, name);
                    json.put(StringData.VIDEO_DESC, desc);
                    json.put(StringData.VIDEO_CODE, video);
                    json.put(StringData.IMG_URL, image);
                    json.put(StringData.LIKE , String.valueOf(like));
                    json.put(StringData.DISLIKE, String.valueOf(dislike));
                    json.put(StringData.SEEN, String.valueOf(seen));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                i.putExtra("json", String.valueOf(json));
                context.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView tempView;
        TextView title;
        TextView desc;
        TextView like_count;
        TextView dislike_count;
        ImageButton like;
        ImageButton dislike;
        ImageButton share;
        LinearLayout mianView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tempView = itemView.findViewById(R.id.im_image);
            title = itemView.findViewById(R.id.tv_name);
            desc = itemView.findViewById(R.id.tv_desc);
            like_count = itemView.findViewById(R.id.tv_like);
            dislike_count = itemView.findViewById(R.id.tv_diss);
            like = itemView.findViewById(R.id.bt_like);
            dislike = itemView.findViewById(R.id.bt_diss);
            share = itemView.findViewById(R.id.bt_share);
            mianView = itemView.findViewById(R.id.view_main);
        }
    }
}
