package com.example.layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.week01_lx.R;


public class FlowLayout extends FrameLayout {
    public FlowLayout(@NonNull Context context) {
        super(context);
    }

    public FlowLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void getData(String data){
        TextView textView = (TextView) View.inflate(getContext(), R.layout.text_layout,null);
        textView.setText(data);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);
        addView(textView);

    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width = getWidth();
        int height = getHeight();
        int row = 0;
        int num = 0;
        int disWidth = 20;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            int viewWidth = view.getWidth();
            int viewHeight = view.getHeight();
            if (disWidth+viewWidth>width){
                row++;
                disWidth=20;
            }

            view.layout(disWidth,row*viewHeight,disWidth+viewWidth,viewHeight*(row+1));
            disWidth+=viewWidth;
            if (viewHeight*(row+1)>height){
                removeView(getChildAt(num++));

            }
        }
    }
}
