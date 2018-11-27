package com.example.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.MyApp;
import com.example.bean.NewsBean;
import com.example.chenguoxing20181119.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<NewsBean.DataBean> list;

    public MyAdapter(Context context, List<NewsBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView =View.inflate(context, R.layout.itme_layout,null);
            holder = new ViewHolder();
            holder.imageView=convertView.findViewById(R.id.image);
            holder.text_title=convertView.findViewById(R.id.text_title);
            holder.text_summary=convertView.findViewById(R.id.text_con);
            convertView.setTag(holder);
        }else {
        holder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(list.get(position).getPic_url(),holder.imageView,MyApp.getOptions());
        holder.text_summary.setText(list.get(position).getNews_summary());
        holder.text_title.setText(list.get(position).getNews_title());
        return convertView;
    }
    public class ViewHolder{
        ImageView imageView;
        TextView text_title,text_summary;
    }
}
