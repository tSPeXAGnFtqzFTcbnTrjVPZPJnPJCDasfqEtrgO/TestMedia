package com.example.phamngocan.testmediaapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter{
    ArrayList<Fragment> fragments;

    public ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);

//        switch (position){
//            case 0:{
//                return new Fragment1();
//
//            }
//            case 1:{
//                return new Fragment2();
//
//            }
//            case 2:{
//                return new Fragment3();
//
//            }
//            case 3:{
//                return new Fragment4();
//
//            }
//            default:{
//                return null;
//            }
//
//        }
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
