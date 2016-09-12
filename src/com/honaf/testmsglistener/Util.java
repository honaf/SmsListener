package com.honaf.testmsglistener;

import java.util.Calendar;

import android.R.bool;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class Util {
	private static SharedPreferences sp;
	private static String url = "";
	private static String keyword="";

	public static boolean isHaveUrl(Context context) {
		sp = context.getSharedPreferences("info", Context.MODE_PRIVATE);
		url = sp.getString("url", "");
		if (url != null && !"".equals(url)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isHaveUrlForSp(Context context, SharedPreferences sp) {
		url = sp.getString("url", "");
		if (url != null && !"".equals(url)) {
			return true;
		} else {
			return false;
		}
	}

	public static String url(Context context) {
		sp = context.getSharedPreferences("info", Context.MODE_PRIVATE);
		url = sp.getString("url", "");
		if (url != null && !"".equals(url)) {
			return url;
		} else {
			return "";
		}
	}

	public static String urlForSp(Context context, SharedPreferences sp) {
		url = sp.getString("url", "");
		if (url != null && !"".equals(url)) {
			return url;
		} else {
			return "";
		}
	}
	
	public static String keyword(Context context) {
		sp = context.getSharedPreferences("info", Context.MODE_PRIVATE);
		keyword = sp.getString("keyword", "");
		if (keyword != null && !"".equals(keyword)) {
			return keyword;
		} else {
			return "";
		}
	}

	public static String keywordForSp(Context context, SharedPreferences sp) {
		url = sp.getString("keyword", "");
		if (keyword != null && !"".equals(keyword)) {
			return keyword;
		} else {
			return "";
		}
	}


	public static boolean isNeedSend() {
		Calendar cal = Calendar.getInstance();// 当前日期
		int hour = cal.get(Calendar.HOUR_OF_DAY);// 获取小时
		int minute = cal.get(Calendar.MINUTE);// 获取分钟
		int minuteOfDay = hour * 60 + minute;// 从0:00分开是到目前为止的分钟数
		final int start = 8 * 60 + 20;// 起始时间 17:20的分钟数
		final int end = 18 * 60;// 结束时间 19:00的分钟数
		if (minuteOfDay >= start && minuteOfDay <= end) {
			return true;
		} else {
			return false;
		}
	}
	public static boolean isContainKeyword(Context context,String msg_content){
		if(TextUtils.isEmpty(msg_content)){
			return false;
		}
		try {
			String keyword=keyword(context);
			if(!TextUtils.isEmpty(keyword)){
				String str[]=keyword.split("|");
				if(str!=null&&str.length!=0){
					for (int i = 0; i < str.length; i++) {
						if(msg_content.contains(str[i]))
							return true;
					}
					
				}
				
			}
			
		} catch (Exception e) {
		}
		return false;
		
	}
}
