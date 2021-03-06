package com.hongfa.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ViewPageAdapter extends PagerAdapter {

    private ArrayList<View> mlist = new ArrayList<View>();
    private int mChildCount = 0;

    public ViewPageAdapter(ArrayList<View> list) {
        this.mlist.clear();
        this.mlist.addAll(list);
        Log.d("ViewPageAdapter", "this.mlist" + this.mlist.size());
    }


//    @Override
//    public void notifyDataSetChanged() {
//        mChildCount = getCount();
//        super.notifyDataSetChanged();
//    }
//
//    @Override
//    public int getItemPosition(Object object) {
//        if (mChildCount > 0) {
//            mChildCount--;
//            return POSITION_NONE;
//        }
//        return super.getItemPosition(object);
//    }
// 获取要滑动的控件的数量，在这里我们以滑动的广告栏为例，那么这里就应该是展示的广告图片的ImageView数量
    @Override
    public int getCount() {
        return mlist.size();
    }

    // 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    // PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView(mlist.get(position));
    }

    // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        view.addView(mlist.get(position));
        return mlist.get(position);
    }
}
