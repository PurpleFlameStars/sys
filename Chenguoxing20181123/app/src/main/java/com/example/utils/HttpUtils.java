package com.example.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtils {
    private static final String TAG = "HttpUtils-------------";
    private static HttpUtils httpUtils = new HttpUtils();
    private HttpLoadListener httpLoadListener;

    private HttpUtils(){

    }

    public static HttpUtils getHttpUtils() {
        synchronized (httpUtils){
            if (httpUtils==null){
                httpUtils = new HttpUtils();
            }
            return httpUtils;
        }
    }
    public void get(String url){
        new MyTask().execute(url);
    }
    class MyTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setConnectTimeout(5000);
                if (con.getResponseCode()==HttpURLConnection.HTTP_OK){
                    InputStream inputStream = con.getInputStream();
                    String string = getString(inputStream);
                    return string;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (TextUtils.isEmpty(s)){
                httpLoadListener.loadError("未加载数据");
            }else{
                Log.i(TAG, s);
                httpLoadListener.loadSuccess(s);
            }
        }
    }

    private String getString(InputStream inputStream) throws IOException {
        InputStreamReader reader = new InputStreamReader(inputStream);
        char[] chars = new char[1024];
        StringBuffer buffer = new StringBuffer();
        int v=0;
        while ((v=reader.read(chars))!=-1){
            String s = new String(chars,0,v);
            buffer.append(s);
        }
        return  buffer.toString();
    }
    public interface HttpLoadListener{
        void loadSuccess(String json);
        void loadError(String error);
    }

    public  void setOnHttpLoadListener(HttpLoadListener httpLoadListener) {
        this.httpLoadListener = httpLoadListener;
    }
    public boolean isNet(Context context){
       ConnectivityManager con= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = con.getActiveNetworkInfo();
        if (info!=null&&info.isConnected()){
            return true;
            
        }else{
            return false;
        }
    }
}
