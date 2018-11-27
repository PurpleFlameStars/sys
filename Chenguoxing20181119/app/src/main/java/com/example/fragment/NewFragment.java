package com.example.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.adapter.MyAdapter;
import com.example.adapter.MyAdapter1;
import com.example.app.MyApp;
import com.example.bean.NewsBean;
import com.example.chenguoxing20181119.R;
import com.example.helper.NyHelper;
import com.example.http.HttpConfig;
import com.example.utils.HttpUtils;
import com.example.view.MyListView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.List;

public class NewFragment extends BaseFragment {

    private String url;
    private HttpUtils httpUtils;

    private  int page =0;
    private  int type =0;
    private List<NewsBean.DataBean> list2 = new ArrayList<>();
    private PullToRefreshScrollView pull;
    private MyListView listView;
    private Banner viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        url = bundle.getString("url");
        Log.d(TAG, url);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_layout, container, false);
        listView = view.findViewById(R.id.list_item);
        pull = view.findViewById(R.id.pull);
        viewPager = view.findViewById(R.id.view_pager_3);
        pull.setMode(PullToRefreshBase.Mode.BOTH);
        pull.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page=0;
                type=0;
                getSeek();
                pull.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page++;
                type=1;
                getSeek();
                pull.onRefreshComplete();
            }
        });

        return view;

    }

    @Override
    protected void getSeek() {
        final NyHelper helper = new NyHelper(MyApp.getContext());
        httpUtils = HttpUtils.getHttpUtils();
        if (TextUtils.isEmpty(url)){
            url=HttpConfig.urls[0];
        }
        Log.d(TAG, "getSeek: "+url);
        httpUtils.get(url+page);
        httpUtils.setOnHttpLoadListener(new HttpUtils.HttpLoadListener() {
            @Override
            public void loadSuccess(String json) {
                Gson gson = new Gson();
                NewsBean newsBean = gson.fromJson(json, NewsBean.class);
                List<NewsBean.DataBean> list = newsBean.getData();
                Log.d(TAG, "loadSuccess: "+list.toString());
                SQLiteDatabase database = helper.getReadableDatabase();
                for (int i=0;i<list.size();i++){
                    String news_title = list.get(i).getNews_title();
                    String news_summary = list.get(i).getNews_summary();
                    ContentValues values = new ContentValues();
                    values.put("title",news_title);
                    values.put("content",news_summary);
                    database.insert("news",null,values);
                }
                switch (type){
                    case 0:{
                        MyAdapter adapter = new MyAdapter(MyApp.getContext(),list);
                        listView.setAdapter(adapter);
                    }
                    break;
                    case 1:{
                        list2.addAll(list);
                        MyAdapter adapter = new MyAdapter(MyApp.getContext(),list2);
                        listView.setAdapter(adapter);
                    }
                    break;
                }
                List<String> urls = new ArrayList<>();
                for (int i=0;i<list.size();i++){
                    urls.add(list.get(i).getPic_url());
                }
                viewPager.setImageLoader(new BeanImageLoader());
                viewPager.setImages(urls).start();

            }

            @Override
            public void loadError(String error) {

            }
        });
    }
    public static NewFragment newFragment(String url){
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        NewFragment newFragment = null;
        if (newFragment==null){
            newFragment = new NewFragment();
        }
        newFragment.setArguments(bundle);
        return newFragment;
    }

    private class BeanImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage((String) path,imageView);
        }
    }
}
