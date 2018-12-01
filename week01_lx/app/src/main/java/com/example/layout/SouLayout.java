package com.example.layout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.example.week01_lx.R;

public class SouLayout extends LinearLayout {
    public SouLayout(Context context) {
        super(context);
        initView();
    }

    public SouLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SouLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private void initView(){
        View view = View.inflate(getContext(), R.layout.sou_layout,this);
    }
}
