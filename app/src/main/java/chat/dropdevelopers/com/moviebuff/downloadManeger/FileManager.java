package chat.dropdevelopers.com.moviebuff.downloadManeger;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import chat.dropdevelopers.com.moviebuff.R;
import chat.dropdevelopers.com.moviebuff.model.FileModel;

public class FileManager extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<FileModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manager);

//        recyclerView = findViewById(R.id.rec_view);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        data = new ArrayList<FileModel>();
        getFiles();
    }


    private void getFiles(){

        File file = new File(Environment.getExternalStorageDirectory(),"/movieBuff/Videos");
        File[] files;
        files = file.listFiles();
        if (files != null) {
            for (File fil : files) {
                Log.e("FILE ---", fil.getName());
            }
        }
    }

    private ArrayList<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files;
        files = parentDir.listFiles();
        if (files != null) {
            for (File file : files) {

                if (file.getName().endsWith(".jpg") ||
                            file.getName().endsWith(".gif") ||
                            file.getName().endsWith(".mp4")) {
                    if (!inFiles.contains(file))
                        inFiles.add(file);
                }
            }
        }
        return inFiles;
    }
}
