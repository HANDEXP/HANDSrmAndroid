package com.hand.srm.activities;

import java.util.ArrayList;
import java.util.List;

import com.hand.srm.R;
import com.hand.srm.adapter.ChartListAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class ChartListActivity extends Activity{
	
	private ListView listview;
	
	private List<chartItem> itemList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_chart_list);
		
		buildAllViews();
		
	}
	
	
///////////////private ////////////
	
	private void buildAllViews()
	{
		//设置标题
		TextView title = (TextView) findViewById(R.id.titleTextView);
		title.setText("报表仪表盘");
		
		ImageButton  btn = (ImageButton) findViewById(R.id.return_btn);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		
		listview = (ListView) findViewById(R.id.chartListView);
		
		itemList = new ArrayList<chartItem>();
		itemList.add(new chartItem(new Intent(this,BarChartActivity.class),"供应商交易额榜单",R.drawable.char1));
		itemList.add(new chartItem(new Intent(this,LineChartActivity.class), "供应商交易额榜单报表", R.drawable.char2));
		itemList.add(new chartItem(new Intent(this,PieChartActivity.class), "采购品供应商占比分析报表", R.drawable.char3));
		
		listview.setAdapter(new ChartListAdapter(itemList,this));
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = itemList.get(position).intent;
				if(intent !=null){
					
					startActivity(intent);
				}
				
			}
		});
		
		
	}
	
	public class chartItem 
	{
		public Intent intent;
		
		public String  title;
		
		public int  image;
		
		public chartItem(Intent intent,String  title,int  image){
			this.intent = intent;
			this.title = title;	
			this.image = image;
			
		}
		
		
	}
	
}
