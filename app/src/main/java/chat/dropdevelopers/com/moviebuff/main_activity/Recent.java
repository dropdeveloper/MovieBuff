package chat.dropdevelopers.com.moviebuff.main_activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import chat.dropdevelopers.com.moviebuff.R;
import chat.dropdevelopers.com.moviebuff.adapters.CustomVideoAdapter;
import chat.dropdevelopers.com.moviebuff.adapters.StatusAdapter;
import chat.dropdevelopers.com.moviebuff.adapters.StatusCategoryAdapter;
import chat.dropdevelopers.com.moviebuff.model.StatusCateModel;
import chat.dropdevelopers.com.moviebuff.model.StatusModel;
import chat.dropdevelopers.com.moviebuff.model.VideoModel;

public class Recent extends Fragment {

    private RecyclerView recyclerView, categoryRecycler;
    private static StatusAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private StatusCategoryAdapter cateAdapter;
    private static ArrayList<StatusModel> data;
    private ArrayList<StatusCateModel> category;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.recent, container, false);

        recyclerView = view.findViewById(R.id.rec_view);
        categoryRecycler = view.findViewById(R.id.rec_view_cate);
        layoutManager = new LinearLayoutManager(view.getContext());
        categoryRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryRecycler.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        category = new ArrayList<StatusCateModel>();

        data = new ArrayList<StatusModel>();
        data.add(new StatusModel("", "Abu", "","http://dropdevelopers.vcandu.com/MOVBuff153.mp4",10, "",5, 5,""));
        data.add(new StatusModel("", "Rim", "","http://dropdevelopers.vcandu.com/1537855313.mp4",20, "",5, 5,""));
        data.add(new StatusModel("", "Vishnu", "","http://dropdevelopers.vcandu.com/MOVBuff153.mp4",30, "",5, 5,""));
        data.add(new StatusModel("", "Jio", "","http://dropdevelopers.vcandu.com/1537855313.mp4",10, "",5, 5,""));
        data.add(new StatusModel("", "Muhammed", "","http://dropdevelopers.vcandu.com/MOVBuff153.mp4",20, "",5, 5,""));
        data.add(new StatusModel("", "Biju", "","http://dropdevelopers.vcandu.com/MOVBuff153.mp4",30, "",5, 5,""));
        data.add(new StatusModel("", "Riju", "","http://dropdevelopers.vcandu.com/1537855313.mp4",10, "",5, 5,""));
        data.add(new StatusModel("", "Manu", "","http://dropdevelopers.vcandu.com/MOVBuff153.mp4",10, "",5, 5,""));


        adapter = new StatusAdapter(data, view.getContext());
        recyclerView.setAdapter(adapter);

        category.add(new StatusCateModel("Funny","33"));
        category.add(new StatusCateModel("Songs","66"));
        category.add(new StatusCateModel("Cinema","99"));
        category.add(new StatusCateModel("Love","9"));
        category.add(new StatusCateModel("Famly","7"));
        category.add(new StatusCateModel("Football","7"));
        category.add(new StatusCateModel("Comedy","7"));
        category.add(new StatusCateModel("Filim Stars","7"));
        category.add(new StatusCateModel("Cricket","7"));

        cateAdapter = new StatusCategoryAdapter(category);
        categoryRecycler.setAdapter(cateAdapter);


        return view;
    }


}
