package com.example.phamngocan.testmediaapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.phamngocan.testmediaapp.adapter.ViewPagerAdapter;
import com.example.phamngocan.testmediaapp.constant.Action;
import com.example.phamngocan.testmediaapp.constant.ActionBroadCast;
import com.example.phamngocan.testmediaapp.fragment.AlbumFragment;
import com.example.phamngocan.testmediaapp.fragment.ListMusicFragment;
import com.example.phamngocan.testmediaapp.fragment.PlayListFragment;
import com.example.phamngocan.testmediaapp.function.ShowLog;
import com.example.phamngocan.testmediaapp.services.ForegroundService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListMusicActivity extends AppCompatActivity {

    @BindView(R.id.tab_view)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager pager;
    @BindView(R.id.bottom_play_layout)
    ConstraintLayout bottomPlayLayout;
    @BindView(R.id.txtv_title)
    TextView txtvTitle;
    @BindView(R.id.btn_play)
    ImageButton btnPlay;
    @BindView(R.id.btn_next)
    ImageButton btnNext;

    ArrayList<Fragment> fragments = new ArrayList<>();

    ViewPagerAdapter pagerAdapter;

    Intent playIntent, pauseIntent, nextIntent, startFore,playerIntent;
    IntentFilter intentFilter;
    long id = -1,prevId = -1;
    int pos = 0;
    String nameSong;
    boolean isVisitedBottomPlay = false;
    boolean isRegister = false;
    boolean isPlaying = false,isStop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_music);
        ButterKnife.bind(this);

        init();
        setUpIntent();
        setClick();
    }

    @Override
    protected void onStart() {
        register();
        super.onStart();
    }

    @Override
    protected void onStop() {
        unRegister();
        super.onStop();
    }

    private void init() {
        fragments.add(new ListMusicFragment());
        fragments.add(new AlbumFragment());
        fragments.add(new PlayListFragment());
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    }

    private void setUpIntent(){

        playerIntent = new Intent(this,PlayerActivity.class);
        playerIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        startFore = new Intent(this, ForegroundService.class);
        startFore.setAction(Action.START_FORE.getName());

        playIntent = new Intent(this, ForegroundService.class);
        playIntent.setAction(Action.PLAY.getName());

        pauseIntent = new Intent(this, ForegroundService.class);
        pauseIntent.setAction(Action.PAUSE.getName());

        nextIntent = new Intent(this, ForegroundService.class);
        nextIntent.setAction(Action.NEXT.getName());


        intentFilter = new IntentFilter();
        intentFilter.addAction(ActionBroadCast.CURSEEK.getName());
        intentFilter.addAction(ActionBroadCast.PAUSE.getName());
        intentFilter.addAction(ActionBroadCast.PLAY.getName());
        intentFilter.addAction(ActionBroadCast.STOP.getName());
    }

    private void setClick(){
        txtvTitle.setOnClickListener(v->{
            startActivity(playerIntent);
        });
        btnPlay.setOnClickListener(v -> {
            ShowLog.logInfo("inner click", "play");
            if (isPlaying) {
                startService(pauseIntent);
            } else {
                if (isStop) {
                    startFore.putExtra(ForegroundService.POS_KEY, pos);
                    startService(startFore);
                } else {
                    startService(playIntent);
                }
            }
            isPlaying = !isPlaying;
        });

        btnNext.setOnClickListener(v -> {
            startService(nextIntent);
        });

    }
    private void update(){
        ShowLog.logInfo("ListMusic ac","update" );
        if(!isVisitedBottomPlay){
            isVisitedBottomPlay = true;
            bottomPlayLayout.setVisibility(View.VISIBLE);
        }
        txtvTitle.setText(nameSong);
    }

    private void register(){
        if(!isRegister){
            isRegister = true;
            registerReceiver(broadcastReceiver,intentFilter );
        }
    }
    private void unRegister(){
        if(isRegister){
            isRegister = false;
            unregisterReceiver(broadcastReceiver);
        }
    }
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == null) {
                return;
            }

            String action = intent.getAction();

            if (action.equals(ActionBroadCast.CURSEEK.getName())) {

                prevId = id;
                id =intent.getLongExtra(ForegroundService.SONG_ID,id );
                nameSong = intent.getStringExtra(ForegroundService.NAME_SONG);
                isPlaying = intent.getBooleanExtra(ForegroundService.IS_PLAYING_KEY, isPlaying);
                pos = intent.getIntExtra(ForegroundService.SONG_ID, pos);

                if(prevId != id){
                    update();
                }

            } else if (action.equals(ActionBroadCast.PLAY.getName())) {
                btnPlay.setImageResource(R.drawable.ic_pause);
                isPlaying = true;
            } else if (action.equals(ActionBroadCast.PAUSE.getName())) {
                btnPlay.setImageResource(R.drawable.ic_play);
                isPlaying = false;
            } else if (action.equals(ActionBroadCast.STOP.getName())) {
                btnPlay.setImageResource(R.drawable.ic_play);

                isStop = true;
                isPlaying = false;
            }
        }
    };


}


