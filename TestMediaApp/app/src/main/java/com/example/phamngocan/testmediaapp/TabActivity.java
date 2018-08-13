package com.example.phamngocan.testmediaapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.phamngocan.testmediaapp.adapter.ViewPagerAdapter;
import com.example.phamngocan.testmediaapp.fragment.Fragment1;
import com.example.phamngocan.testmediaapp.fragment.Fragment2;
import com.example.phamngocan.testmediaapp.fragment.Fragment3;
import com.example.phamngocan.testmediaapp.fragment.Fragment4;
import com.example.phamngocan.testmediaapp.indicator.MyIndicatorView;
import com.example.phamngocan.testmediaapp.indicator.PageException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabActivity extends AppCompatActivity {


    @BindView(R.id.indicator)
    MyIndicatorView indicatorView;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    ArrayList<Fragment> fragments = new ArrayList<>();
    ViewPagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        fragments.add(new Fragment4());

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(pagerAdapter);
        try {
            indicatorView.setViewPager(viewPager);
        } catch (PageException e) {
            Log.e("AAA",e.getMessage() );
            e.printStackTrace();
        }
        //    tabLayout.setupWithViewPager(viewPager);
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



    }
}
