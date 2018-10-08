package chat.dropdevelopers.com.moviebuff.downloadManeger;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import chat.dropdevelopers.com.moviebuff.R;


public class DownloadReceiver extends ResultReceiver {

    private Context context;
    private String file_name;
    NotificationManager mNotifyManager;
    NotificationCompat.Builder mBuilder;

    public DownloadReceiver(Handler handler, Context context, String file_name) {
        super(handler);
        this.context = context;
        this.file_name = file_name;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        if (resultCode == DownloadService.UPDATE_PROGRESS) {
            int progress = resultData.getInt("progress");
           // mProgressDialog.setProgress(progress);
            makeDir(context);
            mNotifyManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setContentTitle("Downloading File")
                    .setContentText(file_name)
                    .setProgress(100, progress, false)
                    .setOngoing(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setPriority(Notification.PRIORITY_LOW);

            // Initialize Objects here
            //publishProgress("5");
            mNotifyManager.notify(55, mBuilder.build());

            if (progress == 100) {
               // mProgressDialog.dismiss();
                Log.e("DOWNLOAD", "SUCCSSE DOWNLOAD");
                load();
            }
        }
    }

    private void load(){
        FFmpeg ffmpeg = FFmpeg.getInstance(context);
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {

                @Override
                public void onStart() {
                    Toast.makeText(context,"Start",Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure() {
                    Toast.makeText(context,"Fail",Toast.LENGTH_SHORT).show();
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
        FFmpeg ffmpeg = FFmpeg.getInstance(context);
        final String file_path = Environment.getExternalStorageDirectory()+"/MovieBuff/Videos/";
        String logoPath = Environment.getExternalStorageDirectory().toString()+"/moviebuff.png";
        try {
            // to execute "ffmpeg -version" command you just need to pass "-version"

            //Orginal command
            //String[] cmd = {"-i",""+file_path+"/video.mp4","-i",""+file_path+"/logo1.png","-filter_complex","overlay=10:main_h-overlay_h-10",file_path+"/out.mp4"};

            String[] cmd = {"-i",""+file_path+file_name,"-i",""+logoPath,"-filter_complex","overlay=10:main_h-overlay_h-10",tempStore()+"v:"+file_name};

            ffmpeg.execute(cmd, new ExecuteBinaryResponseHandler() {

                @Override
                public void onStart() {}

                @Override
                public void onProgress(String message) {
                    Toast.makeText(context,"Progress",Toast.LENGTH_SHORT).show();
                    Log.e("PROGRESS", "WORKING.......");
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                    Log.e("FAIL VID",message);
                }

                @Override
                public void onSuccess(String message) {
                    Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                    Log.e("SUCSS VID",message);
                    File fdelete = new File(Environment.getExternalStorageDirectory().getParent()+"/MovieBuff/Videos/"+file_name);
                    if (fdelete.exists()) {
                        if (fdelete.delete()) {
                            Log.e("file Deleted :" , file_name);
                            moveFile(tempStore(), "v:"+file_name, getOutFilePath());
                        } else {
                            Log.e("file not Deleted :" , file_path);
                        }
                    }

                }

                @Override
                public void onFinish() {}
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // Handle if FFmpeg is already running
            Log.e("FAIL VID",e.getMessage());
        }
    }

    private void moveFile(String inputPath, String inputFile, String outputPath) {

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File (outputPath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath + inputFile);
            String[] separated = inputFile.split(":");
            out = new FileOutputStream(outputPath + separated[1]);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file
            out.flush();
            out.close();
            out = null;

            // delete the original file
            new File(inputPath + inputFile).delete();


        }

        catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }


   private String getFilePath(){

        String file = Environment.getExternalStorageDirectory().toString()+"/MovieBuff/.temp/";
        return file;
   }

    private String getOutFilePath(){

        String file = Environment.getExternalStorageDirectory().toString()+"/MovieBuff/Videos/";
        return file;
    }

    private String tempStore(){
        String file = Environment.getExternalStorageDirectory().toString()+"/MovieBuff/.temp/";
        return file;
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


}