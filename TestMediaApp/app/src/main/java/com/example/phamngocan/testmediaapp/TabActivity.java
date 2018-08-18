package com.example.phamngocan.testmediaapp;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.phamngocan.testmediaapp.adapter.ViewPagerAdapter;
import com.example.phamngocan.testmediaapp.constant.ActionBroadCast;
import com.example.phamngocan.testmediaapp.fragment.Fragment1;
import com.example.phamngocan.testmediaapp.fragment.Fragment2;
import com.example.phamngocan.testmediaapp.fragment.Fragment3;
import com.example.phamngocan.testmediaapp.fragment.Fragment4;
import com.example.phamngocan.testmediaapp.function.ShowLog;
import com.example.phamngocan.testmediaapp.indicator.MyIndicatorView;
import com.example.phamngocan.testmediaapp.indicator.PageException;
import com.example.phamngocan.testmediaapp.services.ForegroundService;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.reactivestreams.Subscription;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabActivity extends AppCompatActivity {

    ForegroundService foregroundService;
    @BindView(R.id.indicator)
    MyIndicatorView indicatorView;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.seek_bar)
    DiscreteSeekBar seekBar;
    @BindView(R.id.txtv_duration)
    TextView txtvDuration;
    @BindView(R.id.txtv_duration_total)
    TextView txtvDurationTotal;


    ArrayList<Fragment> fragments = new ArrayList<>();
    ViewPagerAdapter pagerAdapter;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");

    String nameSong = "";
    int curTime = 0;
    int totalTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        ButterKnife.bind(this);

        init();
        setUpBroadCast();

    }

    private void init() {

        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        fragments.add(new Fragment4());

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        try {
            indicatorView.setViewPager(viewPager);
        } catch (PageException e) {
            Log.e("AAA", e.getMessage());
            e.printStackTrace();
        }
        //    tabLayout.setupWithViewPager(viewPager);
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }
    private void setUpBroadCast(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ActionBroadCast.CURSEEK.getName());
        registerReceiver(broadcastReceiver,intentFilter );
    }


    private void update(){
        seekBar.setMax(totalTime);
        seekBar.setProgress(curTime);
        txtvDuration.setText(simpleDateFormat.format(curTime));
        txtvDurationTotal.setText(simpleDateFormat.format(totalTime ));
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction()!=null && intent.getAction().equals(ActionBroadCast.CURSEEK.getName())){
                totalTime = intent.getIntExtra(ForegroundService.totalTimeKey,totalTime );
                curTime = intent.getIntExtra(ForegroundService.curTimeKey,curTime );
                nameSong  = intent.getStringExtra(ForegroundService.nameSong);
                ShowLog.logInfo("Broadcast", nameSong);
                update();
            }
        }
    };
}