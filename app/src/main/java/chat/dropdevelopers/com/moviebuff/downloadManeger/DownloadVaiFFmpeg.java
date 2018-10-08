package chat.dropdevelopers.com.moviebuff.downloadManeger;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadVaiFFmpeg extends IntentService {
    public static final int UPDATE_PROGRESS = 8344;
    String urlToDownload, fileName;
    public DownloadVaiFFmpeg() {
        super("DownloadService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        makeDir(getBaseContext());
        urlToDownload = intent.getStringExtra("url");
        fileName = intent.getStringExtra("name");
        ResultReceiver receiver = (ResultReceiver) intent.getParcelableExtra("receiver");
//        try {
//            URL url = new URL(urlToDownload);
//            URLConnection connection = url.openConnection();
//            connection.connect();
//            // this will be useful so that you can show a typical 0-100% progress bar
//            int fileLength = connection.getContentLength();
//
//            // download the file
//            InputStream input = new BufferedInputStream(connection.getInputStream());
//            String path = Environment.getExternalStorageDirectory().getPath()+"/MovieBuff/.temp/";
//            OutputStream output = new FileOutputStream(path+fileName);
//
//            byte data[] = new byte[1024];
//            long total = 0;
//            int count;
//            while ((count = input.read(data)) != -1) {
//                total += count;
//                // publishing the progress....
//                Bundle resultData = new Bundle();
//                resultData.putInt("progress" ,(int) (total * 100 / fileLength));
//                receiver.send(UPDATE_PROGRESS, resultData);
//                output.write(data, 0, count);
//            }
//
//            output.flush();
//            output.close();
//            input.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        load();

        Bundle resultData = new Bundle();
        resultData.putInt("progress" ,100);
        receiver.send(UPDATE_PROGRESS, resultData);
    }



    private void load(){
        FFmpeg ffmpeg = FFmpeg.getInstance(getBaseContext());
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {

                @Override
                public void onStart() {
                    Toast.makeText(getApplicationContext(),"Start",Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure() {
                    Toast.makeText(getBaseContext(),"Fail",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess() {
                    addCammand();
                }

                @Override
                public void onFinish() {}
            });
        } catch (FFmpegNotSupportedException e) {
            // Handle if FFmpeg is not supported by device
        }
    }

    private void addCammand(){
        FFmpeg ffmpeg = FFmpeg.getInstance(getBaseContext());
        String file_path = Environment.getExternalStorageDirectory()+"/MovieBuff/.temp/";
        String logoPath = Environment.getExternalStorageDirectory().toString()+"/moviebuff.png";
        try {
            // to execute "ffmpeg -version" command you just need to pass "-version"

            //Orginal command
            //String[] cmd = {"-i",""+file_path+"/video.mp4","-i",""+file_path+"/logo1.png","-filter_complex","overlay=10:main_h-overlay_h-10",file_path+"/out.mp4"};

            String[] cmd = {"-i",""+urlToDownload,"-i",""+logoPath,"-filter_complex","overlay=10:main_h-overlay_h-10",getOutFilePath()};

            ffmpeg.execute(cmd, new ExecuteBinaryResponseHandler() {

                @Override
                public void onStart() {}

                @Override
                public void onProgress(String message) {
                    Toast.makeText(getBaseContext(),"Progress",Toast.LENGTH_SHORT).show();
                    Log.e("PROGRESS", "WORKING.......");
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT).show();
                    Log.e("FAIL VID",message);
                }

                @Override
                public void onSuccess(String message) {
                    Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT).show();
                    Log.e("SUCSS VID",message);
                }

                @Override
                public void onFinish() {}
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // Handle if FFmpeg is already running
            Log.e("FAIL VID",e.getMessage());
        }
    }


    public void makeDir(Context context) {
        // Find the dir to save cached images
        File dir;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            dir = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "MovieBuff/.temp");
        else
            dir = context.getCacheDir();
        if (!dir.exists())
            dir.mkdirs();

    }

    private String getOutFilePath(){

        String file = Environment.getExternalStorageDirectory().toString()+"/MovieBuff/Videos/";
        return file;
    }

}