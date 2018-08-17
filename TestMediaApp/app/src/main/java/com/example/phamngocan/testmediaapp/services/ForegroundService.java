package com.example.phamngocan.testmediaapp.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.phamngocan.testmediaapp.function.ShowLog;
import com.example.phamngocan.testmediaapp.constant.Action;
import com.example.phamngocan.testmediaapp.Instance;
import com.example.phamngocan.testmediaapp.MainActivity;
import com.example.phamngocan.testmediaapp.R;

public class ForegroundService extends Service {

    public final static String posKey = "abc";

    private final int REQUEST_CODE = 123;
    private final String CHANNEL_ID = "111";
    private final int FORE_ID = 321;
    private static int pos;
    MediaPlayer mediaPlayer;


    RemoteViews remoteViews = new RemoteViews(MainActivity.PACKAGE_NAME, R.layout.content_notification);

    Notification notifiCustom;
    Intent prevIntent, nextIntent, noificationMainIntent, playIntent, pauseIntent, stopIntent;
    PendingIntent mainPending, playPending, pausePending, stopPending, prevPending, nextPending;
    NotificationManager mNotificationManager;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("AAA", "create");
        setupIntent();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d("AAA", "start");

        if (intent.getAction() == null) {
            Log.d("AAA", "Intent null");
            return START_NOT_STICKY;
        }

        String action = intent.getAction();
        pos = intent.getIntExtra(posKey, pos);
        ShowLog.logInfo("fore pos:  " ,pos);

        ShowLog.logInfo("action", action);

        if (action.compareTo(Action.START_FORE.getName()) == 0) {


            play(pos);
            startForeground(FORE_ID, notifiCustom);


        } else if (action.equals(Action.PLAY.getName())) {
            Log.d("AAA", "play");

            remoteViews.setImageViewResource(R.id.notifi_play, R.drawable.ic_pause);
            remoteViews.setOnClickPendingIntent(R.id.notifi_play, pausePending);

            mNotificationManager.notify(FORE_ID, notifiCustom);
            resume();

        } else if (action.equals(Action.PAUSE.getName())) {
            Log.d("AAA", "pause");
            remoteViews.setImageViewResource(R.id.notifi_play, R.drawable.ic_play);

            remoteViews.setOnClickPendingIntent(R.id.notifi_play, playPending);

            mNotificationManager.notify(FORE_ID, notifiCustom);
            pause();

        } else if (action.equals(Action.STOP.getName())) {
            Log.d("AAA", "stop");

        } else if (action.equals(Action.NEXT.getName())) {
            Log.d("AAA", "NEXT");
            pos = (pos + 1) % Instance.songList.size();
            play(pos);

        } else if (action.equals(Action.PREVIOUS.getName())) {
            Log.d("AAA", "PREV");
            if (pos == 0) {
                pos = Instance.songList.size();
            }
            pos--;
            play(pos);
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("AAA", "Des");
        super.onDestroy();
    }

    private void play(int pos) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        ShowLog.logInfo("path",Instance.songList.get(pos).getPath());
        int t = pos;
        do {
            ShowLog.logInfo("fore",Instance.songList.get(pos).getNameVi());
            mediaPlayer = MediaPlayer.create(this, Uri.parse(Instance.songList.get(pos).getPath()));
            pos= (pos+1)%Instance.songList.size();
            ShowLog.logInfo("for mp",mediaPlayer);
        }while (mediaPlayer==null && t!=pos);
        if(mediaPlayer!=null){
            pos=t;
            Log.d("a","playing");
            remoteViews.setTextViewText(R.id.notifi_title,Instance.songList.get(pos).getNameVi());
            //remoteViews.setString(R.id.notifi_title,"",Instance.songList.get(pos).getNameVi());

            mNotificationManager.notify(FORE_ID,notifiCustom);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    ForegroundService.pos = (ForegroundService.pos + 1) % Instance.songList.size();

                    play(ForegroundService.pos);

                }
            });
        }
    }
    private void resume(){
        if(mediaPlayer!=null){
            mediaPlayer.start();
        }
    }
    private void  pause(){
        if(mediaPlayer!=null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    private void setupIntent() {

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        noificationMainIntent = new Intent(this, MainActivity.class);
        noificationMainIntent.setAction(Action.MAIN.getName());
        noificationMainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        mainPending = PendingIntent.getActivity(this, REQUEST_CODE, noificationMainIntent, 0);

        playIntent = new Intent(this, ForegroundService.class);
        playIntent.setAction(Action.PLAY.getName());
        playPending = PendingIntent.getService(this, REQUEST_CODE, playIntent, 0);


        pauseIntent = new Intent(this, ForegroundService.class);
        pauseIntent.setAction(Action.PAUSE.getName());
        pausePending = PendingIntent.getService(this, REQUEST_CODE, pauseIntent, 0);

        stopIntent = new Intent(this, ForegroundService.class);
        stopIntent.setAction(Action.STOP.getName());
        stopPending = PendingIntent.getService(this, REQUEST_CODE, stopIntent, 0);

        prevIntent = new Intent(this, ForegroundService.class);
        prevIntent.setAction(Action.PREVIOUS.getName());
        prevPending = PendingIntent.getService(this, REQUEST_CODE, prevIntent, 0);

        nextIntent = new Intent(this, ForegroundService.class);
        nextIntent.setAction(Action.NEXT.getName());
        nextPending = PendingIntent.getService(this, REQUEST_CODE, nextIntent, 0);

        NotificationChannel channel;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
             channel = new NotificationChannel(CHANNEL_ID,"name",NotificationManager.IMPORTANCE_HIGH);

            mNotificationManager.createNotificationChannel(channel);
        }

        notifiCustom = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setOngoing(true)
                .setContentIntent(mainPending)
                .build();

        notifiCustom.bigContentView = remoteViews;

        remoteViews.setOnClickPendingIntent(R.id.notifi_next, nextPending);
        remoteViews.setOnClickPendingIntent(R.id.notifi_prev, prevPending);
        remoteViews.setOnClickPendingIntent(R.id.notifi_play, pausePending);


    }
}
