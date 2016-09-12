package com.honaf.testmsglistener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.honaf.testmsglistener.R;

public class MainActivity extends Activity implements OnClickListener {
	private Button btn_ok;
	private EditText et_url;
	private EditText et_keyword;
	private SharedPreferences sp;
	public static Activity inStance=null;
	public static  Activity getInstance(){
		return inStance;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inStance=this;
		initView();
		initListener();
		initData();
		Log.e("honaf", "mainactivity开启！");
	
//		Intent intent = new Intent(MainActivity.this, MyService.class);
//		startService(intent);
		
//		Intent i = new Intent();  
//        ComponentName comp = new ComponentName("com.android.settings",  
//                "com.android.settings.BackgroundApplicationsManager");  
//        i.setComponent(comp);  
//        startActivity(i); 
	}

	private void initView() {
		sp = this.getSharedPreferences("info", Context.MODE_PRIVATE);
		btn_ok = (Button) this.findViewById(R.id.btn_ok);
		et_url = (EditText) this.findViewById(R.id.et_url);
		et_keyword=(EditText)this.findViewById(R.id.et_keyword);

	}

	public void initListener() {
		btn_ok.setOnClickListener(this);
	}

	public void initData() {
		et_url.setText(Util.urlForSp(this, sp));
		et_keyword.setText(Util.keywordForSp(this, sp));

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.btn_ok:
			String url=et_url.getText().toString().trim();
			String keyword=et_keyword.getText().toString().trim();
			if(url!=null&&!"".equals(url)){
				Editor edit = sp.edit();
				edit.putString("url", url);
				edit.putString("keyword", keyword);
				edit.commit();
				Toast.makeText(this, "绑定成功！", Toast.LENGTH_SHORT).show();
				Intent serviceIntent = new Intent(this, MyService.class);
				this.startService(serviceIntent);
//				ContentObserver co = new SmsReceiver(new Handler(), this
//		                ); 
//		        this.getContentResolver().registerContentObserver( 
//		                Uri.parse("content://sms/"), true, co); 
				this.finish(); 
			}else{
				Toast.makeText(this, "请输入url路径", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}
}
