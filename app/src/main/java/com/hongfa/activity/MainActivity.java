package com.hongfa.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongfa.adapter.ViewPageAdapter;
import com.hongfa.entity.TitleItemXYEntity;
import com.hongfa.interfaces.UnderlineAnimationInterface;
import com.hongfa.utils.DensityUtil;
import com.hongfa.view.ConnectionView;
import com.hongfa.view.GeneralView;
import com.hongfa.view.SoftwareVersionView;
import com.hongfa.view.SoundView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import activity.hongfa.com.tablayoutdemo.R;

public class MainActivity extends Activity implements UnderlineAnimationInterface {

    private static final String TAG = "MainActivity";
    private static final int PAGE_GENERAL_POSITION = 0;
    private static final int PAGE_CONNECTION_POSITION = 1;
    private static final int PAGE_SOUND_POSITION = 2;
    private static final int PAGE_SOFTWAREVERSION_POSITION = 3;
    // 窗口焦点是否已经准备好（用于下划线动画）
    private boolean mIsWindowFocusReady = false;
    /**
     * 上下文
     */
    private static Context mContext = null;
    /**
     * 记录当前页面
     */
    private int mCurrentpage = 0;
    /**
     * view集合
     */
    private ArrayList<View> viewList = null;
    /**
     * 4个tab的父布局
     */
    private LinearLayout mTabLayout;
    /**
     * tab title
     */
    private FrameLayout setting_tab_view_id;
    /**
     * viewPager 容器
     */
    private ViewPager viewPager = null;
    /**
     * 过度
     */
    private View view = null;
    // 起始控件（用于下划线过渡动画）
    private View mFromView;
    //private Window mWindow;

    // 选中项下划线
    public static ImageView mUnderlineImage;
    /**
     * tab字体
     */
    public static TextView mTextview[] = new TextView[4];

    private boolean isFoucs = false;
    private ViewPageAdapter mViewPageAdapter;
    /**
     * ViewPager内容
     */
    public GeneralView mGeneralView;
    private ConnectionView mConnectionView = null;
    public SoundView mSoundView = null;
    private SoftwareVersionView mSoftwareVersionView = null;
    private Map<Integer, TitleItemXYEntity> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        initView(mContext);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(TAG, "onWindowFocusChanged: hasFocus = " + hasFocus +
                ", mIsWindowFocusReady = " + mIsWindowFocusReady);
        if (hasFocus && !mIsWindowFocusReady) {
            mIsWindowFocusReady = true;
            initUnderline(mUnderlineImage, 0);
        }
    }

    /**
     * @param context
     * @function 初始化View
     * @author 黄宏发
     */
    private void initView(Context context) {
        mTabLayout = (LinearLayout) findViewById(R.id.activity_main_tab_relative);
        setting_tab_view_id = (FrameLayout) findViewById(R.id.setting_tab_view_id);
        /**
         * tab栏
         */
        this.mTextview[0] = (TextView) findViewById(R.id.activity_main_tab_textgeneral);
        this.mTextview[1] = (TextView) findViewById(R.id.activity_main_tab_textconnection);
        this.mTextview[2] = (TextView) findViewById(R.id.activity_main_tab_textsounds);
        this.mTextview[3] = (TextView) findViewById(R.id.activity_main_tab_textsoftwareversion);
        mUnderlineImage = (ImageView) findViewById(R.id.underline_image);
        this.viewPager = (ViewPager) findViewById(R.id.activity_main_view_pager);
        for (int i = 0; i < mTextview.length; i++) {
            mTextview[i].setFocusable(true);
            mTextview[i].setFocusableInTouchMode(true);
            mTextview[i].setOnClickListener(mTabClickListener);
            mTextview[i].setOnFocusChangeListener(mOnFocusChangeListener);
        }

        /**
         * @author huang hf ViewPager栏
         */
        mGeneralView = new GeneralView(context);
        mConnectionView = new ConnectionView(context);
        mSoundView = new SoundView(context);
        mSoftwareVersionView = new SoftwareVersionView(context);
        viewList = new ArrayList<View>();
        viewList.add(mGeneralView);
        viewList.add(mConnectionView);
        viewList.add(mSoundView);
        viewList.add(mSoftwareVersionView);
        this.viewPager.setOffscreenPageLimit(viewList.size());
        this.mViewPageAdapter = new ViewPageAdapter(viewList);
        Log.v(TAG, "viewList :" + viewList);
        Log.v(TAG, "this.mViewPageAdapter :" + this.mViewPageAdapter);
        this.viewPager.setAdapter(mViewPageAdapter);
        this.viewPager.addOnPageChangeListener(new ViewPageOnPageChangeListener());
    }

    private final View.OnClickListener mTabClickListener = new View.OnClickListener() {
        public void onClick(View view) {
//            final int oldSelected = viewPager.getCurrentItem();
//            final int newSelected = mTextview[i].getIndex();
            tabSwitch(0, view);
//            if (oldSelected == newSelected && mTabReselectedListener != null) {
//                mTabReselectedListener.onTabReselected(newSelected);
//            }
        }
    };

    /**
     * TAb 栏由右向左滑动
     */
    private void mainTranslateAnimation() {
        AnimationSet animationset = new AnimationSet(true);
        TranslateAnimation translate = new TranslateAnimation(1850, 0, 0, 0);
        translate.setDuration(500);
        animationset.addAnimation(translate);
        animationset.setFillAfter(true);
        animationset.setFillBefore(true);// 移动的时候不一样
        setting_tab_view_id.startAnimation(animationset);
        animationset.setAnimationListener(new Animation.AnimationListener() {


            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }


            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            public void onAnimationEnd(Animation arg0) {
                showTabView();
            }
        });


    }


    /**
     * 显示界面
     */
    private void showTabView() {
        this.viewPager.setVisibility(this.viewPager.VISIBLE);

    }

    /**
     * @author 黄宏发
     */
    public class ViewPageOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {

        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onPageSelected(int position) {
            tabSwitch(position, null);
        }
    }


    /**
     * tab获取焦点事件和失去焦点事件
     */
    public View.OnFocusChangeListener mOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            tabSwitch(mCurrentpage, view);
        }
    };

    /**
     * @param id
     * @param view
     * @function
     * @author 黄宏发
     */
    @SuppressLint("ResourceAsColor")
    private void tabSwitch(final int id, View view) {
        for (int i = 0; i < mTextview.length; i++) {
            mMainButtonBg(i);
        }
        if (view != null) {
            switch (view.getId()) {
                case R.id.activity_main_tab_textgeneral:
                    setCurrentItem(PAGE_GENERAL_POSITION);
                    mMainButtonFocusBg(0);
                    break;
                case R.id.activity_main_tab_textconnection:
                    setCurrentItem(PAGE_CONNECTION_POSITION);
                    mMainButtonFocusBg(1);
                    break;
                case R.id.activity_main_tab_textsounds:
                    setCurrentItem(PAGE_SOUND_POSITION);
                    mMainButtonFocusBg(2);
                    break;
                case R.id.activity_main_tab_textsoftwareversion:
                    setCurrentItem(PAGE_SOFTWAREVERSION_POSITION);
                    mMainButtonFocusBg(3);
                    break;
            }
        } else {
            switch (id) {
                case 0:
                    setCurrentItem(PAGE_GENERAL_POSITION);
                    mMainButtonFocusBg(0);
                    break;
                case 1:
                    setCurrentItem(PAGE_CONNECTION_POSITION);
                    mMainButtonFocusBg(1);
                    break;
                case 2:
                    setCurrentItem(PAGE_SOUND_POSITION);
                    mMainButtonFocusBg(2);
                    break;
                case 3:
                    setCurrentItem(PAGE_SOFTWAREVERSION_POSITION);
                    mMainButtonFocusBg(3);
                    break;
            }
        }

    }

    /**
     * @param item
     * @function 设置当前页面
     * @author 黄宏发
     */
    private void setCurrentItem(int item) {
        if (viewPager.getCurrentItem() != item) {
            viewPager.setCurrentItem(item);
        }
    }

    /**
     * 有焦点时的背景颜色与字体颜色（选中时）
     *
     * @param i
     */
    private void mMainButtonFocusBg(int i) {
        mTextview[i].requestFocus();
        initUnderline(mUnderlineImage, i);
        mTextview[i].setTextColor(mContext.getResources().getColor(R.color.b1));
    }

    /**
     * title 栏的字体颜色(失去选中时)
     *
     * @param i
     */
    private void mMainButtonBg(int i) {
        mTextview[i].setTextColor(mContext.getResources().getColor(
                R.color.k1));

    }

    @Override
    public void initUnderline(View underlineView, int position) {
        View v = mTabLayout.getFocusedChild();
        if (!mIsWindowFocusReady || v == null) {
            return;
        }
        // 下划线x坐标（返回图标宽度 + 控件相对于父布局的左间距）
        int x = DensityUtil.dip2px(mContext, 0) + v.getLeft();
        // 下划线宽度
        int width = v.getWidth();
        // 目标项索引值
        int targetItemIndex = mTabLayout.indexOfChild(v);
        Log.d(TAG, "initUnderline: targetItemIndex = " + targetItemIndex +
                ", x = " + x + ", width = " + width);
        if (map.isEmpty() || !map.containsKey(position)) {
            if (position == 0) {
                TitleItemXYEntity entity = new TitleItemXYEntity();
                entity.setX(x);
                entity.setY(width);
                map.put(position, entity);
            }
            if (position == 1) {
                TitleItemXYEntity entity = new TitleItemXYEntity();
                entity.setX(x);
                entity.setY(width);
                map.put(position, entity);
            }
            if (position == 2) {
                TitleItemXYEntity entity = new TitleItemXYEntity();
                entity.setX(x);
                entity.setY(width);
                map.put(position, entity);
            }
            if (position == 3) {
                TitleItemXYEntity entity = new TitleItemXYEntity();
                entity.setX(x);
                entity.setY(width);
                map.put(position, entity);
            }
        }
        for (Map.Entry<Integer, TitleItemXYEntity> entry : map.entrySet()) {
            if (position == entry.getKey()) {
                x = entry.getValue().getX();
                width = entry.getValue().getY();
                break;
            }
        }
        // 设置下划线的x坐标和宽度
        android.widget.FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) underlineView.getLayoutParams();
        params.leftMargin = x;
        params.width = width;
        // 设置新参数前，必须先将动画清除掉，否则坐标和宽度可能会错乱
        underlineView.clearAnimation();
//        underlineView.setLayoutParams(params);
//        underlineView.setBackgroundColor(getResources().getColor(R.color.b1));
    }

    @Override
    public void scaleAndMoveAnimation(View underlineView, View fromView, View toView, int position) {
        // 起始x坐标
        int fromX = (int) (DensityUtil.dip2px(mContext, 0) + fromView.getLeft());
        // 目标x坐标
        int toX = (int) (DensityUtil.dip2px(mContext, 0) + toView.getLeft());
        // 起始控件字符串宽度
        float fromWidth = fromView.getWidth();
        // 目标控件字符串宽度
        float toWidth = toView.getWidth();
        Log.d(TAG, "scaleAndMoveAnimation: fromX = " + fromX + ", toX = " + toX);
        Log.d(TAG, "scaleAndMoveAnimation: fromWidth = " + fromWidth + ", toWidth = " + toWidth);
        Log.d(TAG, "scaleAndMoveAnimation: underlineView.getWidth = " + underlineView.getWidth());

        // 位移动画
        Animation transAnim = new TranslateAnimation(fromX - underlineView.getLeft(), toX - underlineView.getLeft(), 0, 0);
        // 缩放动画
        Animation scaleAnim = new ScaleAnimation(fromWidth / underlineView.getWidth(), toWidth / underlineView.getWidth(), 1, 1);
        // 动画集合，用于组合动画
        AnimationSet animSet = new AnimationSet(false);
        animSet.setDuration(100);
        // 动画完成后，保留所在位置和宽度
        animSet.setFillAfter(true);
        // 备注：此处必须先添加缩放动画后添加移动动画，否则会导致移动距离错误
        animSet.addAnimation(scaleAnim);
        animSet.addAnimation(transAnim);
        underlineView.startAnimation(animSet);
    }

    @Override
    public void resetUnderline(View underlineView) {
        Log.d(TAG, "resetUnderline!");
        // 清除起始控件
        mFromView = null;
        // 将下划线的颜色设置为透明
        underlineView.setBackgroundColor(getResources().getColor(
                R.color.transparent));
        // 设置下划线的x坐标和宽度
        android.widget.FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) underlineView.getLayoutParams();
        params.leftMargin = 0;
        params.width = 0;
        // 设置新参数前，必须先将动画清除掉，否则坐标和宽度可能会错乱
        underlineView.clearAnimation();
        underlineView.setLayoutParams(params);
    }
}
