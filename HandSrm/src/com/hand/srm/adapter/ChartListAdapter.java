package com.hand.srm.adapter;

import java.util.List;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hand.srm.R;
import com.hand.srm.activities.ChartListActivity.chartItem;
import com.hand.srm.application.SrmApplication;

public class ChartListAdapter extends BaseAdapter {

	public List<chartItem> items;
	
	public Context context;
	
	public ChartListAdapter(List<chartItem> items,Context context)
	{
		
		this.items= items;
		this.context = context;
	
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			
			convertView =  LayoutInflater.from(context).inflate(R.layout.layout_char_list_item, null);
		}
			
		ImageView imageView = 	(ImageView) convertView.findViewById(R.id.itemImageView);
		imageView.setImageResource(items.get(position).image);
		
		TextView  textview = (TextView) convertView.findViewById(R.id.itemTextView);
		textview.setText(items.get(position).title);
		
//		WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
//	    Display display = manager.getDefaultDisplay();
//	    int height=display.getHeight();
//
//	    
//		 AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
//                 LinearLayout.LayoutParams.MATCH_PARENT,100);
//		 convertView.setLayoutParams(lp);
		
		return convertView;
		
	}

}
