package com.honaf.testmsglistener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author honaf
 *
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
	static final String action_boot = "android.intent.action.BOOT_COMPLETED";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(action_boot)) {
			if (!Util.isHaveUrl(context)) {
				Log.e("BootBroadcastReceiver", "开机启动成功，url路径未绑定过");
				Intent ootStartIntent = new Intent(context, MainActivity.class);
				ootStartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(ootStartIntent);
			} else {
				Log.e("BootBroadcastReceiver", "开机启动成功，url路径已绑定过");
				Intent serviceIntent = new Intent(context, MyService.class);
				context.startService(serviceIntent);
			}

		}

	}

}
