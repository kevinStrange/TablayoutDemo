package com.hongfa.interfaces;

import android.view.View;

/**
 * 下划线过渡动画接口
 */
public interface UnderlineAnimationInterface {
    /**
     * 初始化下划线的坐标和宽度等参数
     * @param underlineView 下划线控件
     */
    public void initUnderline(View underlineView, int position);

    /**
     * 将下划线从起始控件的下方移动到目标控件的下方，移动过程同时将下划线的宽度缩放成目标控件的宽度
     * @param underlineView 下划线控件
     * @param fromView 起始控件
     * @param toView 目标控件
     * @param position //暂时用来标记每个title的item，以便下划线过渡
     */
    public void scaleAndMoveAnimation(View underlineView, View fromView, View toView,int position);

    /**
     * 将下划线的参数还原到初始状态
     */
    public void resetUnderline(View underlineView);
}
