package chat.dropdevelopers.com.moviebuff.downloadManeger;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import chat.dropdevelopers.com.moviebuff.R;
import chat.dropdevelopers.com.moviebuff.adapters.FilemangerAdapter;
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

        recyclerView = findViewById(R.id.rec_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<FileModel>();



        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 22);
        } else {
            getFiles();
        }

      // getFiles();

    }


    private void getFiles(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String path = Environment.getExternalStorageDirectory().toString()+"/storage/emulated/0/MovieBuff/Videos";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
        for (int i = 0; i < files.length; i++)
        {
            Log.d("Files", "FileName:" + files[i].getName());
            File f = new File(files[i].getPath());
            int file_size = Integer.parseInt(String.valueOf(f.length()/1024));
            Date lastModDate = new Date(f.lastModified());
            String file_name = files[i].getName();
            String file_type;
            if (file_name.endsWith(".mp4")){
                file_type = "VID";
            }else if (file_name.endsWith(".jpg")){
                file_type = "IMG";
            }else {
                file_type = "MSC";
            }
            data.add(new FileModel(file_name, getFileSize(file_size), sdf.format(lastModDate), file_type, files[i].getPath()));

        }

        adapter = new FilemangerAdapter(data, this);
        recyclerView.setAdapter(adapter);
    }


    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";

        final String[] units = new String[] { "K", "MB", "GB", "TB", "TB" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 22:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getFiles();
                }
                break;

            default:
                break;
        }
    }
}
