package com.hand.srm.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.hand.srm.R;

public class SettingActivity extends SherlockActivity implements OnClickListener{
	
	private Button quitBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		bindAllViews();
	}

	
	private void bindAllViews(){
		quitBtn = (Button) findViewById(R.id.quitBtn);
		quitBtn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.quitBtn:
			AlertDialog.Builder dialog = new AlertDialog.Builder(SettingActivity.this);
			dialog.setTitle("是否清空缓存数据");
			dialog.setMessage("您的用户名和密码将被忘记");
			dialog.setCancelable(false);
			dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO 自动生成的方法存根
					finish();
				}
			});
			dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO 自动生成的方法存根
					SharedPreferences preferences = getSharedPreferences("userInfo",
							Context.MODE_APPEND);
					if(preferences.edit().clear().commit()){
						Toast.makeText(getApplicationContext(), "缓存清空成功", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(getApplicationContext(), "缓存清空失败", Toast.LENGTH_SHORT).show();
					}
					finish();
				}
			});
			dialog.show();
			
			overridePendingTransition(0, R.anim.quit_view);
			break;

		default:
			break;
		}
	}

}
