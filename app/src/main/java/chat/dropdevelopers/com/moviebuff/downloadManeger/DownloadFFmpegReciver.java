package chat.dropdevelopers.com.moviebuff.downloadManeger;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

public class DownloadFFmpegReciver extends ResultReceiver {

    private Context context;
    private String file_name;

    public DownloadFFmpegReciver(Handler handler) {
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
            if (progress == 100) {
                // mProgressDialog.dismiss();

            }
        }
    }
}