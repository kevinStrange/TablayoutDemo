package com.hongfa.utils;


import android.content.Context;
import android.view.View;

public class DensityUtil {

	public static int dip2px(Context context, float dpValue) { 
		final float scale = context.getResources().getDisplayMetrics().density; 
		return (int) (dpValue * scale + 0.5f);
	} 
	 
	public static int px2dip(Context context, float pxValue) { 
		final float scale = context.getResources().getDisplayMetrics().density; 
		return (int) (pxValue / scale + 0.5f);
	} 
	
	
	public static void setPadding(Context context,View view){
		
		int paddingValue = 16;
		if(null != context){
			paddingValue = px2dip(context,16);
		}
		if(null != view){
			view.setPadding(paddingValue, paddingValue, paddingValue, paddingValue);
		}
	}
	
}
