package com.example.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adapter.MyFragmentPagerAdapter1;
import com.example.chenguoxing20181119.R;
import com.example.http.HttpConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {


    private ViewPager viewPager;
    private TabLayout tabLayout;

    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        viewPager = view.findViewById(R.id.view_pager_2);
        tabLayout = view.findViewById(R.id.tab);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tabLayout.setupWithViewPager(viewPager);
        List<NewFragment> list = new ArrayList<>();
        for (int i=0;i<HttpConfig.urls.length;i++){
            NewFragment newFragment = NewFragment.newFragment(HttpConfig.urls[i]);
            list.add(newFragment);
        }
        MyFragmentPagerAdapter1 adapter = new MyFragmentPagerAdapter1(getActivity().getSupportFragmentManager(),list);
        viewPager.setAdapter(adapter);
    }
}
