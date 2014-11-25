package com.hand.srm.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
			finish();
			overridePendingTransition(0, R.anim.quit_view);
			break;

		default:
			break;
		}
	}

}
