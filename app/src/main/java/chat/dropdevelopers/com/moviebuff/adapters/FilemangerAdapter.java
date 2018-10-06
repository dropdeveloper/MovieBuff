package chat.dropdevelopers.com.moviebuff.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import chat.dropdevelopers.com.moviebuff.R;
import chat.dropdevelopers.com.moviebuff.model.FileModel;

public class FilemangerAdapter extends RecyclerView.Adapter<FilemangerAdapter.MyViewHolder> {

    private ArrayList<FileModel> dataSet;

    @Override
    public FilemangerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.demmi_file_item, viewGroup, false);

       MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FilemangerAdapter.MyViewHolder holder, int i) {

        holder.file_name.setText(dataSet.get(i).getName());
        holder.file_size.setText(dataSet.get(i).getSize());
        holder.date.setText(dataSet.get(i).getDate());
        if (dataSet.get(i).getType().equals("IMG")){
            holder.templet.setImageResource(R.drawable.ic_image_icon);
        }else if (dataSet.get(i).getType().equals("VID")){
            holder.templet.setImageResource(R.drawable.ic_video_icon);
        }else if (dataSet.get(i).getType().equals("MSC")){
            holder.templet.setImageResource(R.drawable.ic_music_icon);
        }else {
            holder.templet.setImageResource(R.drawable.ic_file_icon);
        }

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public FilemangerAdapter(ArrayList<FileModel> dataSet){
        this.dataSet = dataSet;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView file_name;
        TextView file_size;
        TextView date;
        ImageView templet;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            file_name = itemView.findViewById(R.id.file_name);
            file_size = itemView.findViewById(R.id.tv_size);
            date = itemView.findViewById(R.id.tv_date);
            templet = itemView.findViewById(R.id.view_temp);
        }
    }
}
