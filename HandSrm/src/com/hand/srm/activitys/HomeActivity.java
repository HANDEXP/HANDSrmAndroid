package com.hand.srm.activitys;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.xml.sax.helpers.XMLFilterImpl;

import com.hand.srm.R;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class HomeActivity extends Activity implements OnClickListener{
	
	    ////底部四个按钮
		//订单
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
			///////////绑定底部四个按钮
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
