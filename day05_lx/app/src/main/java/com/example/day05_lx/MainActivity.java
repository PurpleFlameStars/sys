package com.example.day05_lx;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.helper.MyHelper;
import com.example.layout.FlowLayot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edit_sou;
    private FlowLayot flow_layout;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit_sou = findViewById(R.id.edit_sou);
       Button but_sou= findViewById(R.id.but_sou);
       Button but_but= findViewById(R.id.but_but);
        flow_layout = findViewById(R.id.flow_layout);
       but_but.setOnClickListener(this);
       but_sou.setOnClickListener(this);
        list = new ArrayList<>();
        MyHelper helper = new MyHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.query("titles", null, null, null, null, null, null);
        while (cursor.moveToNext()){
            String string = cursor.getString(cursor.getColumnIndex("title"));
            list.add(string);
        }
       /* String[] strings = new String[]{
                "热门搜索","精彩电影","热门影院","流行歌曲","娱乐八卦"
        };*/
        for (int i = 0; i < list.size(); i++) {
            flow_layout.getData(list.get(i));
        }

     }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.but_sou:
                String data = edit_sou.getText().toString();
                flow_layout.getData(data);
                break;
                case R.id.but_but:
                    edit_sou.setText("");
                 flow_layout.remove();
                break;
        }
    }
}
