package com.example.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.example.chenguoxing20181119.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

public class MyApp extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context= this;
        ImageLoader.getInstance().init(getConfig());
    }

    private ImageLoaderConfiguration getConfig() {
        File file = new File(Environment.getExternalStorageDirectory()+"/image");
        if (!file.exists()){
            file.mkdir();
        }
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration
                .Builder(this)
                .diskCache(new UnlimitedDiskCache(file))
                .build();
        return configuration;
    }
    public static DisplayImageOptions getOptions(){
        DisplayImageOptions options = new DisplayImageOptions
                .Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return options;

    }
    public static Context getContext() {
        return context;
    }
}
