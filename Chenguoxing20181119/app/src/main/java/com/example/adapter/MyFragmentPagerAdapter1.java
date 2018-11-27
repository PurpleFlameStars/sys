package com.example.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.fragment.NewFragment;
import com.example.http.HttpConfig;

import java.util.List;

public class MyFragmentPagerAdapter1 extends FragmentPagerAdapter {
    private List<NewFragment> list;

    public MyFragmentPagerAdapter1(FragmentManager fm, List<NewFragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return HttpConfig.titles[position];
    }
}
