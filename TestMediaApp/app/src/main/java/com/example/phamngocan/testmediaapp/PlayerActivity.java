package com.example.phamngocan.testmediaapp;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import com.example.phamngocan.testmediaapp.Model.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerActivity extends AppCompatActivity {

    @BindView(R.id.recycle_song)
    RecyclerView recyclerView;
    AdapterSong adapterSong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);

        init();
    }
    private void  init(){
        adapterSong = new AdapterSong(Instance.songList,getApplicationContext());
        LinearLayoutManager layoutManager =  new LinearLayoutManager(getApplicationContext());
        recyclerView.setAdapter(adapterSong);
        recyclerView.setLayoutManager(layoutManager);


    }
}
