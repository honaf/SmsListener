package com.honaf.testmsglistener;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.util.Log;

public class SmsContent {
	private Activity activity;// 这里有个activity对象，不知道为啥以前好像不要，现在就要了。自己试试吧。
	private Uri uri;
	List<SmsInfo> infos;

	public SmsContent(Activity activity, Uri uri) {
		infos = new ArrayList<SmsInfo>();
		this.activity = activity;
		this.uri = uri;
	}

	/**
	 * Role:获取短信的各种信息 <BR>
	 * Date:2012-3-19 <BR>
	 * 
	 * @author CODYY)peijiangping
	 */
	public List<SmsInfo> getSmsInfo() {
		try {
			String[] projection = new String[] { "_id", "address", "person",
					"body", "date", "type" };
			// Cursor cusor = activity.managedQuery(uri, projection, null,
			// null,"date desc");
			ContentResolver cr = activity.getContentResolver();
			Cursor cusor = cr.query(uri, projection, null, null, "date desc");
			// int nameColumn = cusor.getColumnIndex("person");
			int phoneNumberColumn = cusor.getColumnIndex("address");
			int smsbodyColumn = cusor.getColumnIndex("body");
			int dateColumn = cusor.getColumnIndex("date");
			int typeColumn = cusor.getColumnIndex("type");
			if (cusor != null) {
				while (cusor.moveToNext()) {
					SmsInfo smsinfo = new SmsInfo();
					// smsinfo.setName(cusor.getString(nameColumn));
					smsinfo.setDate(cusor.getString(dateColumn));
					smsinfo.setPhoneNumber(cusor.getString(phoneNumberColumn));
					smsinfo.setSmsbody(cusor.getString(smsbodyColumn));
					smsinfo.setType(cusor.getString(typeColumn));
					infos.add(smsinfo);
				}
				cusor.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.e("honaf_infos.size()", infos.size() + "");
		return infos;
	}
}