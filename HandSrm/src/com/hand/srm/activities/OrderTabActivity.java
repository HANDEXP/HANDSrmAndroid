package com.hand.srm.activities;

import com.hand.srm.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class OrderTabActivity  extends Activity implements OnClickListener
{
	private LinearLayout orderListLL;
	private LinearLayout shipListSearchLL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ordertab);
		
		
		bindAllViews();
	}

	private void bindAllViews(){
		orderListLL = (LinearLayout) findViewById(R.id.orderListLL);
		orderListLL.setOnClickListener(this);
		shipListSearchLL = (LinearLayout) findViewById(R.id.shipListSearchLL);
		shipListSearchLL.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.orderListLL:
			Intent intent4shoplist = new Intent(getApplicationContext(),ShopPoListActivity.class);
			startActivity(intent4shoplist);
			break;
		case R.id.shipListSearchLL:
			Intent intent4enablelist = new Intent(getApplicationContext(),EnableToReceiveActivity.class);
			startActivity(intent4enablelist);
			break;
		default:
			break;
		}
	}
	
}
