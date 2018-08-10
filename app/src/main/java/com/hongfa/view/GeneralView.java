package com.hongfa.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import activity.hongfa.com.tablayoutdemo.R;

public class GeneralView extends RelativeLayout {
    private Context mContext;
    /** 记录当前页面 */
    private int mCurrentpage = 0;

    public GeneralView(Context context) {
        super(context);
        initView(context);
    }
    public GeneralView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public GeneralView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        addView(LayoutInflater.from(this.mContext).inflate(R.layout.activity_general_view, null));
    }

}
