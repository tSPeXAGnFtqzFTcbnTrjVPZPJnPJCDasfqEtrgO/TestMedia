package com.example.phamngocan.testmediaapp;

import android.app.Notification;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.phamngocan.testmediaapp.COMMON_FUNCTION.ShowLog;
import com.example.phamngocan.testmediaapp.Constant.Action;
import com.example.phamngocan.testmediaapp.Model.Song;
import com.example.phamngocan.testmediaapp.Services.ForegroundService;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static String PACKAGE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PACKAGE_NAME = getPackageName();
        prepareInstance();
        action();

    }

    private void action() {
        startActivity(new Intent(MainActivity.this,PlayerActivity.class));
        Intent intent = new Intent(this, ForegroundService.class);
        intent.setAction(Action.START_FORE.getName());
        //startService(intent);
    }

    private void prepareInstance() {
        Instance.songList = ScanFileMp3.queryFile(getApplicationContext());
    }

}
