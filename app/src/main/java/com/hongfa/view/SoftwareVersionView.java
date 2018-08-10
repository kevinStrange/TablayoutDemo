package com.hongfa.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import activity.hongfa.com.tablayoutdemo.R;

public class SoftwareVersionView extends RelativeLayout {
    private Context mContext;

    public SoftwareVersionView(Context context) {
        super(context);
        initView(context);
    }

    public SoftwareVersionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SoftwareVersionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        addView(LayoutInflater.from(this.mContext).inflate(
                R.layout.activity_softwareversion_view, null));
    }
}
