package chat.dropdevelopers.com.moviebuff.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

public class VideoCut extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public VideoCut(String name) {
        super("VideoCut");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    private void getCommand(String start, String end, int videoCounr){
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        final String logoPath = Environment.getExternalStorageDirectory().toString()+"/moviebuff.png";
        final String[] cmd = {"-i", "" + file_path,"-i",""+logoPath,"-filter_complex","overlay=10:main_h-overlay_h-10","-ss", start, "-t", end, getOutPut() + "/MOVBuff" + ts + ".mp4"};
        Log.e("TOTAL PART", String.valueOf(videoCounr));
        //StatusCutter(cmd, videoCounr);
        LoadVideo(cmd);

    }
}
