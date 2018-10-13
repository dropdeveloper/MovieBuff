package chat.dropdevelopers.com.moviebuff.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import chat.dropdevelopers.com.moviebuff.MusicPlayer.MusicPlayer;
import chat.dropdevelopers.com.moviebuff.R;
import chat.dropdevelopers.com.moviebuff.Utils.StringData;
import chat.dropdevelopers.com.moviebuff.downloadManeger.FileManager;
import chat.dropdevelopers.com.moviebuff.model.FileModel;

public class FilemangerAdapter extends RecyclerView.Adapter<FilemangerAdapter.MyViewHolder> {

    private ArrayList<FileModel> dataSet;
    private Context context;

    @Override
    public FilemangerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.demmi_file_item, viewGroup, false);

       MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FilemangerAdapter.MyViewHolder holder, final int i) {

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

        final String file = dataSet.get(i).getFilePath();
        final String fileName = dataSet.get(i).getName();

        holder.main_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String file_type = dataSet.get(i).getType();
                if (file_type.equals("VID")){
                    intent.setDataAndType(Uri.parse(dataSet.get(i).getFilePath()), "video/*");
                    context.startActivity(intent);
                }else if (file_type.equals("IMG")){
                    intent.setDataAndType(Uri.parse(dataSet.get(i).getFilePath()), "image/*");
                    context.startActivity(intent);
                } else if (dataSet.get(i).getName().endsWith(".m4a")){
                   // intent.setDataAndType(Uri.parse(dataSet.get(i).getFilePath()), "audio/*");
                    Intent i = new Intent(context, MusicPlayer.class);
                    i.putExtra(StringData.FILE_URL,file);
                    i.putExtra(StringData.FILE_NAME,fileName);
                    context.startActivity(i);
                }


            }
        });

        holder.main_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(200,VibrationEffect.DEFAULT_AMPLITUDE));
                }else{
                    //deprecated in API 26
                    vibrator.vibrate(200);
                }

                FileManager.displayPopupWindow(view.getRootView(), context);

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public FilemangerAdapter(ArrayList<FileModel> dataSet, Context context){
        this.dataSet = dataSet;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView file_name;
        TextView file_size;
        TextView date;
        ImageView templet;
        LinearLayout main_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            file_name = itemView.findViewById(R.id.file_name);
            file_size = itemView.findViewById(R.id.tv_size);
            date = itemView.findViewById(R.id.tv_date);
            templet = itemView.findViewById(R.id.view_temp);
            main_item = itemView.findViewById(R.id.main_item);
        }
    }


}
