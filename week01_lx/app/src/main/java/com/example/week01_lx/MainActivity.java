package com.example.week01_lx;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.helper.MyHelper;
import com.example.layout.FlowLayout;
import com.example.layout.SouLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edit_sou;
    private FlowLayout flow_1;
    private SQLiteDatabase database;
    private String table="titles";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SouLayout souLayout= findViewById(R.id.sou_layout);
        ImageView image_close= findViewById(R.id.image_close);
        MyHelper helper = new MyHelper(this);
        database = helper.getReadableDatabase();
        final ImageView image_sou= souLayout.findViewById(R.id.image_sou);
        edit_sou = souLayout.findViewById(R.id.edit_sou);
        image_sou.setOnClickListener(this);
        image_close.setOnClickListener(this);
        flow_1 = findViewById(R.id.flow_1);
        FlowLayout flow_2= findViewById(R.id.flow_2);
        for (int i = 0; i < 11; i++) {
            flow_2.getData("搜索"+i);
        }
        Cursor cursor = database.query(table, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex("title"));
            flow_1.getData(title);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_close:
                    flow_1.removeAllViews();
                    database.delete(table,null,null);
                break;
                case R.id.image_sou:
                    String data = edit_sou.getText().toString();
                   // Toast.makeText(MainActivity.this,"data",Toast.LENGTH_SHORT).show();
                    ContentValues values = new ContentValues();
                    values.put("title",data);
                    flow_1.getData(data);
                    database.insert(table,null,values);

                    break;
        }
    }
}
