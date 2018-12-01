package com.example.layout;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.day05_lx.R;
import com.example.helper.MyHelper;

public class FlowLayot extends FrameLayout {
    MyHelper helper = new MyHelper(getContext());
    private String table="titles";
    private SQLiteDatabase database;

    public FlowLayot(@NonNull Context context) {
        super(context);
    }

    public FlowLayot(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayot(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void getData(String data){
        TextView textView = (TextView) View.inflate(getContext(), R.layout.text_layout, null);
        textView.setText(data);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);
        addView(textView);
        database = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",data);
        database.insert(table,null,values);

    }
    public void remove(){
        removeAllViews();
        database.delete(table,null,null);
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width=getWidth();
        int row=0;
        int disWidth = 20;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            int viewWidth = view.getWidth();
            int viewHeight= view.getHeight();
            if (disWidth+viewWidth>width){
                row++;
                disWidth = 20;
            }
            view.layout(disWidth,viewHeight*row,disWidth+viewWidth,viewHeight*(row+1));
            disWidth+=viewWidth;
        }
    }
}
