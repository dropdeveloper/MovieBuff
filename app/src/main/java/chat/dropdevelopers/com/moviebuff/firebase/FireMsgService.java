package chat.dropdevelopers.com.moviebuff.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import chat.dropdevelopers.com.moviebuff.MainActivity;
import chat.dropdevelopers.com.moviebuff.R;
import chat.dropdevelopers.com.moviebuff.main_activity.Main_Screen;

public class FireMsgService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d("---->", "Message received ["+remoteMessage+"]");


        Log.d("---->", "From: " + remoteMessage.getFrom());
        Log.d("---->", "Notification Message Body: " + remoteMessage.getNotification().getBody());

        // Create Notification
        Intent intent = new Intent(this, Main_Screen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1410,
                intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                                                                 .setSmallIcon(R.drawable.icon)
                                                                 .setContentTitle("Message")
                                                                 .setContentText(remoteMessage.getNotification().getBody())
                                                                 .setAutoCancel(true)
                                                                 .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1410, notificationBuilder.build());
    }
}