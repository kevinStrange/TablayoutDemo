package com.hongfa.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import activity.hongfa.com.tablayoutdemo.R;

public class ConnectionView extends RelativeLayout {

    private Context mContext;

    public ConnectionView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }
    public ConnectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public ConnectionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
    }

    private void initView() {
        addView(LayoutInflater.from(this.mContext).inflate(
                R.layout.activity_connection_view, null));


    }
}
