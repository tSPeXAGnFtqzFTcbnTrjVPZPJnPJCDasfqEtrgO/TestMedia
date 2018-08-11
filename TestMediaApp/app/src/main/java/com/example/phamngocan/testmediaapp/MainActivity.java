package com.example.phamngocan.testmediaapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.example.phamngocan.testmediaapp.Constant.Action;
import com.example.phamngocan.testmediaapp.Services.ForegroundService;

public class MainActivity extends AppCompatActivity {

    public static String PACKAGE_NAME;
    private final int REQUEST_PERMISSION_READ_EXTERNAL = 123;

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
        Instance.songList.clear();
        Instance.songList.addAll(ScanFileMp3.queryFileExternal(getApplicationContext()));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            int check = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE );

            if(check == PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_READ_EXTERNAL);
            }
        }else {
            Instance.songList.addAll(ScanFileMp3.queryFileInternal(getApplicationContext()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERMISSION_READ_EXTERNAL:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Instance.songList = ScanFileMp3.queryFileInternal(getApplicationContext());
                }
                break;
            }
        }
    }
}
