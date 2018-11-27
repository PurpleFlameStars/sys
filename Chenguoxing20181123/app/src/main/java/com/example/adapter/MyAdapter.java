package com.example.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.MyApp;
import com.example.bean.NewsBean;
import com.example.chenguoxing20181123.R;
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
        switch (getItemViewType(position)){
            case 0:
                if (convertView==null){
                    convertView = View.inflate(context, R.layout.itme_layout_1,null);
                    holder = new ViewHolder();
                    holder.image_1=convertView.findViewById(R.id.image_1);
                    holder.textView = convertView.findViewById(R.id.text_1);
                    convertView.setTag(holder);
                }else{
                    holder = (ViewHolder) convertView.getTag();
                }
                ImageLoader.getInstance().displayImage(list.get(position).getThumbnail_pic_s(),holder.image_1,MyApp.getOptions());
                holder.textView.setText(list.get(position).getTitle());
                break;
                case 1:
                if (convertView==null){
                    convertView = View.inflate(context, R.layout.itme_layout_2,null);
                    holder = new ViewHolder();
                    holder.image_1=convertView.findViewById(R.id.image_2_1);
                    holder.image_2=convertView.findViewById(R.id.image_2_2);
                    holder.image_3=convertView.findViewById(R.id.image_2_3);
                    holder.textView = convertView.findViewById(R.id.text_2);
                    convertView.setTag(holder);
                }else{
                    holder = (ViewHolder) convertView.getTag();
                }
                ImageLoader.getInstance().displayImage(list.get(position).getThumbnail_pic_s(),holder.image_1,MyApp.getOptions());
                ImageLoader.getInstance().displayImage(list.get(position).getThumbnail_pic_s02(),holder.image_2,MyApp.getOptions());
                ImageLoader.getInstance().displayImage(list.get(position).getThumbnail_pic_s03(),holder.image_3,MyApp.getOptions());
                holder.textView.setText(list.get(position).getTitle());
                break;
        }
        return convertView;
    }
    public class ViewHolder{
        ImageView image_1,image_2,image_3;
        TextView textView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        int type=0;
        if (TextUtils.isEmpty(list.get(position).getThumbnail_pic_s02())&&TextUtils.isEmpty(list.get(position).getThumbnail_pic_s03())){
            type = 0;
        }
        if (!(TextUtils.isEmpty(list.get(position).getThumbnail_pic_s())&&TextUtils.isEmpty(list.get(position).getThumbnail_pic_s02())&&TextUtils.isEmpty(list.get(position).getThumbnail_pic_s03()))){
            type = 1;
        }
        return type;
    }
}
