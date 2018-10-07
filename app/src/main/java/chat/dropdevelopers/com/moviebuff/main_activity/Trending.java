package chat.dropdevelopers.com.moviebuff.main_activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import chat.dropdevelopers.com.moviebuff.R;
import chat.dropdevelopers.com.moviebuff.Utils.StringData;
import chat.dropdevelopers.com.moviebuff.adapters.CustomVideoAdapter;
import chat.dropdevelopers.com.moviebuff.model.VideoModel;

public class Trending extends Fragment {

    private RecyclerView recyclerView;
    private static CustomVideoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static ArrayList<VideoModel> data;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.trending, container, false);

        recyclerView = view.findViewById(R.id.rec_view);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<VideoModel>();
        data.add(new VideoModel("Kayamkulam Kochunni ", "A romantic film directed by Roshan Andrews, starring Nivin Pauly and Priya Anand in the lead roles. ", "https://images.moviebuff.com/09d3101c-cb96-4101-8aa5-77523374daaf?w=500", "CDwvzTzSib8", 44,10,"", 5));
        data.add(new VideoModel("Mandharam", "Rajesh faces various stages of life filled with warmth and contentment from his relationships with people around him.", "https://images.moviebuff.com/d5eb1408-fded-4060-ab54-82b316032920?w=500", "TkvQPNdGAKc", 10,0,"",22));
        data.add(new VideoModel("Wonder Boys", "A thriller film directed by Sreekanth S Nair, starring Bala and Praveen in the lead roles.", "https://images.moviebuff.com/3fab936a-2d98-417a-8eb4-ee7edbbc3687?w=500", "CrPofg-yY4M", 20,10,"", 44));
        data.add(new VideoModel("Varathan", "A couple shifts from Dubai to the latter's family estate in Kerala to figure things out while troubles confront the couple.", "https://images.moviebuff.com/803330a5-755d-43df-892d-02916a80fe17?w=500", "pbg78Dbl_m0", 22,5,"",55));

        adapter = new CustomVideoAdapter(data, getContext());
        recyclerView.setAdapter(adapter);

        //filter(StringData.SEARCH);

        return view;
    }

    public static void filter(String text) {
        ArrayList<VideoModel> filteredList = new ArrayList<>();

        for (VideoModel item : data) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
    }
}
