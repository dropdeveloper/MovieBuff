package chat.dropdevelopers.com.moviebuff.main_activity;

import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;
import chat.dropdevelopers.com.moviebuff.R;
import chat.dropdevelopers.com.moviebuff.Utils.StringData;

public class VideoDetailView extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

private YouTubePlayer YPlayer;
//AIzaSyCZ1fUOO-Z0lbLmmiUd9iLHmEqUTgw69mo
    //AIzaSyAWbKv4i-3yRu17Ci57T4wZEIZCIXKP1I0
private static final String YoutubeDeveloperKey = "AIzaSyCZ1fUOO-Z0lbLmmiUd9iLHmEqUTgw69mo";
private static final int RECOVERY_DIALOG_REQUEST = 1;

private String title, desc, img_url, video_code;
private int like, dislike, seen;

private TextView Title, Disc, LikeView, DislikeView, SeenView;
private ImageButton LikeButton, DislikeButton, ShareButton, DownloadButton;
private LinearLayout mainLayout;
    private static String youtubeLink;
    private LinearLayout ProgressView, DownloadView;
    private ProgressBar loading, proDown;
    private File dir;
@Override
protected void onCreate(Bundle savedInstanceState) {
                  super.onCreate(savedInstanceState);
                  setContentView(R.layout.activity_video_detail_view);
                    Title = findViewById(R.id.tv_name);
                    Disc = findViewById(R.id.tv_desc);
                    LikeView = findViewById(R.id.tv_like);
                    DislikeView = findViewById(R.id.tv_dislike);
                    SeenView = findViewById(R.id.tv_viewers);
                    DownloadButton = findViewById(R.id.bt_download);
                    mainLayout = findViewById(R.id.main_layout);
                    ProgressView = findViewById(R.id.loading_view);
                    loading = findViewById(R.id.progress_bar);
                    proDown = findViewById(R.id.down_pro);
                    DownloadView = findViewById(R.id.down_pro_view);

                  YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube);
                  youTubeView.initialize(YoutubeDeveloperKey, this);

                  Intent i = getIntent();
    try {
        JSONObject json = new JSONObject(i.getStringExtra("json"));

        title = json.getString(StringData.VIDIO_TITLE);
        desc = json.getString(StringData.VIDEO_DESC);
        img_url = json.getString(StringData.IMG_URL);
        video_code = json.getString(StringData.VIDEO_CODE);
        like = json.getInt(StringData.LIKE);
        dislike =  json.getInt(StringData.DISLIKE);
        seen = json.getInt(StringData.SEEN);

        Title.setText(title);
        Disc.setText(desc);
        LikeView.setText(String.valueOf(like));
        DislikeView.setText(String.valueOf(dislike));
        SeenView.setText(String.valueOf(seen));

    } catch (JSONException e) {
        e.printStackTrace();
    }

    DownloadButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //  downloadVideo("https://www.youtube.com/watch?v="+video_code);
            Log.e("CLICK","DOWNLOAD");
            String link = "https://www.youtube.com/watch?v="+video_code;
            if (link != null && (link.contains("://youtu.be/") || link.contains("youtube.com/watch?v="))) {
                youtubeLink = link;
                // We have a valid link
                getYoutubeDownloadUrl(youtubeLink);
                //vibrator
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(200,VibrationEffect.DEFAULT_AMPLITUDE));
                }else{
                    //deprecated in API 26
                    vibrator.vibrate(200);
                }
                loading.setVisibility(View.VISIBLE);
                ProgressView.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(VideoDetailView.this, "error_no_yt_link", Toast.LENGTH_LONG).show();
//vibrator
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(800,VibrationEffect.DEFAULT_AMPLITUDE));
                }else{
                    //deprecated in API 26
                    vibrator.vibrate(800);
                }
            }
        }

    });

    deleteCache(VideoDetailView.this);

}


@Override
public void onInitializationFailure(YouTubePlayer.Provider provider,
               YouTubeInitializationResult errorReason) {
               if (errorReason.isUserRecoverableError()) {
               errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
               } else {
               String errorMessage = String.format(
               "There was an error initializing the YouTubePlayer",
               errorReason.toString());
               Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
               }
}


@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                  if (requestCode == RECOVERY_DIALOG_REQUEST) {
                  // Retry initialization if user performed a recovery action
                  getYouTubePlayerProvider().initialize(YoutubeDeveloperKey, this);
                  }
}

protected YouTubePlayer.Provider getYouTubePlayerProvider() {
                  return (YouTubePlayerView) findViewById(R.id.youtube);
}

@Override
public void onInitializationSuccess(YouTubePlayer.Provider provider,
               YouTubePlayer player, boolean wasRestored) {
               YPlayer = player;
               /*
                * Now that this variable YPlayer is global you can access it
                * throughout the activity, and perform all the player actions like
                * play, pause and seeking to a position by code.
                */
               if (!wasRestored) {
               YPlayer.cueVideo(video_code);
               }
   }




    private void getYoutubeDownloadUrl(String youtubeLink) {
        new YouTubeExtractor(this) {

            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                //mainProgressBar.setVisibility(View.GONE);

                if (ytFiles == null) {
                    // Something went wrong we got no urls. Always check this.
                    finish();
                    return;
                }
                // Iterate over itags
                for (int i = 0, itag; i < ytFiles.size(); i++) {
                    itag = ytFiles.keyAt(i);
                    // ytFile represents one file with its url and meta data
                    YtFile ytFile = ytFiles.get(itag);

                    // Just add videos in a decent format => height -1 = audio
                    if (ytFile.getFormat().getHeight() == -1 || ytFile.getFormat().getHeight() >= 360) {
                        addButtonToMainLayout(vMeta.getTitle(), ytFile);
                    }
                }
            }
        }.extract(youtubeLink, true, false);
    }

    private void addButtonToMainLayout(final String videoTitle, final YtFile ytfile) {
        // Display some buttons and let the user choose the format
        String btnText = (ytfile.getFormat().getHeight() == -1) ? "Audio " +
                                                                          ytfile.getFormat().getAudioBitrate() + " kbit/s" :
                                 ytfile.getFormat().getHeight() + "p";
        btnText += (ytfile.getFormat().isDashContainer()) ? " dash" : "";
        final Button btn = new Button(this);
        btn.setText(btnText);
        btn.setPadding(0,5,0,5);
        btn.setBackgroundResource(R.drawable.video_cate_button);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String filename;
                if (videoTitle.length() > 55) {
                    filename = videoTitle.substring(0, 55) + "." + ytfile.getFormat().getExt();
                } else {
                    filename = videoTitle + "." + ytfile.getFormat().getExt();
                }
                filename = filename.replaceAll("[\\\\><\"|*?%:#/]", "");
                downloadFromUrl(ytfile.getUrl(), videoTitle, filename);
                //finish();
            }
        });
        mainLayout.addView(btn);
        loading.setVisibility(View.GONE);
        ProgressView.setVisibility(View.GONE);
    }

    private boolean downloadFromUrl(String youtubeDlUrl, String downloadTitle, String fileName) {

        makeDir(VideoDetailView.this);

        Uri uri = Uri.parse(youtubeDlUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(downloadTitle);

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        dir = new File(android.os.Environment.getExternalStorageDirectory(), "/MovieBuff/Videos");
        request.setDestinationInExternalPublicDir(String.valueOf(dir), fileName);
       // request.setDestinationInExternalFilesDir(VideoDetailView.this, String.valueOf(dir),fileName);

        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);


        boolean flag = true;
        boolean downloading =true;
        try{
           // DownloadManager mManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            //Request mRqRequest = new Request(Uri.parse("http://"+model.getDownloadURL()));
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            long idDownLoad=manager.enqueue(request);
            DownloadManager.Query query = null;
            query = new DownloadManager.Query();
            Cursor c = null;
            if(query!=null) {
                query.setFilterByStatus(DownloadManager.STATUS_FAILED|DownloadManager.STATUS_PAUSED|DownloadManager.STATUS_SUCCESSFUL|DownloadManager.STATUS_RUNNING|DownloadManager.STATUS_PENDING);
            } else {
                return flag;
            }

            while (downloading) {
                c = manager.query(query);
                if(c.moveToFirst()) {
                    Log.i ("FLAG","Downloading");
                   // Notify();
                    //DownloadView.setVisibility(View.VISIBLE);

                    int status =c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    if (status==DownloadManager.STATUS_SUCCESSFUL) {
                        Log.i ("FLAG","done");
                       // DownloadView.setVisibility(View.GONE);
                        downloading = false;
                        flag=true;
                        break;
                    }
                    if (status==DownloadManager.STATUS_FAILED) {
                        Log.i ("FLAG","Fail");
                       // DownloadView.setVisibility(View.GONE);
                        downloading = false;
                        flag=false;
                        break;
                    }
                }
            }

            return flag;
        }catch (Exception e) {
            flag = false;
            return flag;
        }
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public void makeDir(Context context) {
        // Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            dir = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "MovieBuff/Videos");
        else
            dir = context.getCacheDir();
        if (!dir.exists())
            dir.mkdirs();

    }


}