package com.example.fragment;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.MyAdapter;
import com.example.app.MyApp;
import com.example.bean.NewsBean;
import com.example.chenguoxing20181123.R;
import com.example.helper.MyHelper;
import com.example.http.HttpConfig;
import com.example.utils.HttpUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {

    private  List<NewsBean.DataBean> list=new ArrayList<>();
    private int page=1;
    private int type=0;
    private PullToRefreshListView pull;
    private SQLiteDatabase database;
    private String table="news";

    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        pull = view.findViewById(R.id.pull);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getHttp();
        pull.setMode(PullToRefreshBase.Mode.BOTH);
        pull.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page=1;
                type=0;
                getHttp();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                type=1;
                getHttp();
                pull.onRefreshComplete();
            }
        });
    }

    private void getHttp() {
        MyHelper helper = new MyHelper(MyApp.getContext());
        database = helper.getReadableDatabase();
        HttpUtils httpUtils = HttpUtils.getHttpUtils();
        if (httpUtils.isNet(MyApp.getContext())){
            httpUtils.get(HttpConfig.url+page);
            httpUtils.setOnHttpLoadListener(new HttpUtils.HttpLoadListener() {
                @Override
                public void loadSuccess(String json) {
                    ContentValues values = new ContentValues();
                    values.put("json",json);
                    database.insert(table,null,values);
                    Gson gson = new Gson();
                    NewsBean newsBean = gson.fromJson(json, NewsBean.class);
                    List<NewsBean.DataBean> data = newsBean.getData();
                    switch (type){
                        case 0:
                            MyAdapter adapter = new MyAdapter(MyApp.getContext(),data);
                            pull.setAdapter(adapter);
                            break;
                            case 1:
                                list.addAll(data);
                            MyAdapter adapter1 = new MyAdapter(MyApp.getContext(),list);
                            pull.setAdapter(adapter1);
                            break;
                    }
                    pull.onRefreshComplete();
                }

                @Override
                public void loadError(String error) {

                }
            });
        }else{
            Toast.makeText(MyApp.getContext(),"网络未连接",Toast.LENGTH_SHORT).show();
            Cursor cursor = database.query(table, null, null, null, null, null, null);
        if (cursor.moveToLast()){
            String json = cursor.getString(cursor.getColumnIndex("json"));
            Gson gson = new Gson();
            NewsBean newsBean = gson.fromJson(json, NewsBean.class);
            List<NewsBean.DataBean> data = newsBean.getData();
            MyAdapter adapter = new MyAdapter(MyApp.getContext(),data);
            pull.setAdapter(adapter);
        }
        }
    }
}
