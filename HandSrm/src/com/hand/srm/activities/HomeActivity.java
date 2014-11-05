package com.hand.srm.activities;

import org.xml.sax.helpers.XMLFilterImpl;

import com.hand.srm.R;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class HomeActivity extends Activity implements OnClickListener{
	
	    ////�ײ��ĸ���ť
		//����
		private LinearLayout orderListll;
		

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_home);
			buildAllViews();
			


			
	
		}
		
		@Override
		protected void onResume() {
		// TODO Auto-generated method stub
			super.onResume();
		}
		
		////////////////private
		private void buildAllViews()
		{
			///////////�󶨵ײ��ĸ���ť
			orderListll = (LinearLayout) findViewById(R.id.orderListll);
			orderListll.setOnClickListener(this);

		}

		
		
		/////////////click
		@Override
		public void onClick(View v) {
			if(v.equals(orderListll)){
				
				
			}
			
		}
}
