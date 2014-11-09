package com.hand.srm.adapter;

import java.util.List;

import com.hand.srm.R;
import com.hand.srm.item.FunctionItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FunctionGridAdapter extends BaseAdapter{
	
	private List<FunctionItem>  functionList;
	
	private Context context;

	public FunctionGridAdapter(List<FunctionItem> items,Context context)
	{
		this.functionList = items;
		this.context  = context;
	}
	
	
	@Override
	public int getCount() {

		return functionList.size();
	}

	@Override
	public Object getItem(int position) {

		return functionList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			convertView = (View) LayoutInflater.from(context).inflate(
					R.layout.view_function, null);

		}
		
		ImageView img = (ImageView) convertView.findViewById(R.id.view_function_imageview);
		
		TextView tv = (TextView) convertView.findViewById(R.id.view_function_textview);
		
		img.setImageResource(functionList.get(position).icon);
		
		tv.setText(functionList.get(position).title);
		
		
		
		return convertView;
	}

}
