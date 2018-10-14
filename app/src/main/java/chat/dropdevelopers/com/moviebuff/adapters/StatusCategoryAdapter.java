package chat.dropdevelopers.com.moviebuff.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import chat.dropdevelopers.com.moviebuff.R;
import chat.dropdevelopers.com.moviebuff.model.StatusCateModel;

public class StatusCategoryAdapter extends RecyclerView.Adapter<StatusCategoryAdapter.MyViewHolder> {

    private ArrayList<StatusCateModel> dataSet;
    private Context context;
    private int[] select;

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.status_cate_item, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        holder.title.setText(dataSet.get(i).getCategory());
        holder.main.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
               // Intent i = new Intent(context, )

                    holder.main.setBackgroundResource(R.drawable.category_item_back_status);
                    holder.title.setTextColor(Color.parseColor("#FFFFFF"));

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public StatusCategoryAdapter(ArrayList<StatusCateModel> dataSet){
        this.dataSet = dataSet;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        LinearLayout main;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            main = itemView.findViewById(R.id.main_view);
        }
    }
}
