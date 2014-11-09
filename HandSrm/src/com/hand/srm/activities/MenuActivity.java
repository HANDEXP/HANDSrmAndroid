package com.hand.srm.activities;

import java.util.ArrayList;
import java.util.List;



import com.hand.srm.R;
import com.hand.srm.item.TabItem;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MenuActivity  extends TabActivity{
	
	 private TabHost mTabHost;
	
	 private List<TabItem> mItems;
	 
	 private LayoutInflater mInflater;
	 
	  private List<TabSpec> mTabSpec;
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_menu);
		

		
		mInflater =  LayoutInflater.from(this);
		
		mTabHost = getTabHost();
		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
		prepare();	
	    creatTab();
		
	}
	 
	 
///////////////private//////////////
	private int getTabItemCount() {
			return mItems.size();
	}
	 
	 private void prepare()
	 {
		 
 		TabItem item1 = new TabItem(
				"首页",									// title
				R.drawable.tab_change_news,					// icon
				new Intent(this, HomeActivity.class));	// intent
		
		TabItem item2 = new TabItem(
				"订单",
				R.drawable.tab_change_navigation,
				new Intent(this, OrderTabActivity.class));
		
		TabItem item3 = new TabItem(
				"财务",
				R.drawable.tab_change_share,
				new Intent(this, OrderTabActivity.class));
		
		TabItem item4 = new TabItem(
				"发现",
				R.drawable.tab_change_setting,
				new Intent(this, OrderTabActivity.class));
		
		TabItem item5 = new TabItem(
				"设置",
				R.drawable.tab_change_setting,
				new Intent(this, OrderTabActivity.class));
		
		
		mItems = new ArrayList<TabItem>();
		mItems.add(item1);
		mItems.add(item2);
		mItems.add(item3);
		mItems.add(item4);
		mItems.add(item5);
		 
		 
	 }
	 
	 
	 private void   creatTab()
	 {
		 for(int i=0;i<getTabItemCount();i++)
		 {
			 TabSpec tabspec =  mTabHost.newTabSpec("tab" +i);
			 
//			 mTabSpec.add(tabspec); 
			 
			 //设置跳转的页面
			tabspec.setContent(mItems.get(i).getIntent());
			 
			View view=  mInflater.inflate(R.layout.tab_widget, null);
			
			TextView _textview = (TextView) view.findViewById(R.id.tab_name);
			
			_textview.setText(mItems.get(i).getTitle());
			_textview.setCompoundDrawablesWithIntrinsicBounds(0, mItems.get(i).getIcon(), 0, 0);
			
			tabspec.setIndicator(view);
			
			mTabHost.addTab(tabspec);
			
//			 tabspec.setIndicator(view)
			 
		 }
		 
	 }
	 
	
}
