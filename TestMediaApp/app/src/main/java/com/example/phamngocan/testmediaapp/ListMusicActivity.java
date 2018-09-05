package com.example.phamngocan.testmediaapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.phamngocan.testmediaapp.adapter.ViewPagerAdapter;
import com.example.phamngocan.testmediaapp.fragment.AlbumFragment;
import com.example.phamngocan.testmediaapp.fragment.ListMusicFragment;
import com.example.phamngocan.testmediaapp.fragment.PlayListFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListMusicActivity extends AppCompatActivity {

    @BindView(R.id.tab_view)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager pager;

    ArrayList<Fragment> fragments = new ArrayList<>();

    ViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_music);
        ButterKnife.bind(this);

        init();
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


}


