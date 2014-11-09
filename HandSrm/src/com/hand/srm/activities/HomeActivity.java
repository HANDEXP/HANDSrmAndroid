package com.hand.srm.activities;

import java.util.ArrayList;
import java.util.List;

import com.hand.srm.R;
import com.hand.srm.adapter.FunctionGridAdapter;
import com.hand.srm.item.FunctionItem;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import android.widget.AdapterView.OnItemClickListener;

public class HomeActivity extends Activity implements OnClickListener {

	private LinearLayout orderListll;

	private GridView mGridView;

	private List<FunctionItem> functionList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		prepare();
		buildAllViews();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

////////////////////private//////////////////
	private void prepare() {
		FunctionItem item1 = new FunctionItem("预制采购单", R.drawable.ic_category_0, null);

		FunctionItem item2 = new FunctionItem("供应商名录", R.drawable.ic_category_1, null);

		FunctionItem item3 = new FunctionItem("报表仪表盘", R.drawable.ic_category_2, null);

		FunctionItem item4 = new FunctionItem("寄售领用记录", R.drawable.ic_category_3, null);

		FunctionItem item5 = new FunctionItem("寄售寄存记录", R.drawable.ic_category_4, null);

		functionList = new ArrayList<FunctionItem>();
		functionList.add(item1);
		functionList.add(item2);
		functionList.add(item3);
		functionList.add(item4);
		functionList.add(item5);

	}

	private void buildAllViews() {
		mGridView = (GridView) findViewById(R.id.mGridView);
		mGridView.setAdapter(new FunctionGridAdapter(functionList, this));
		
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println(functionList.get(position).title);
				
				
			}
			
			
		});
	}

/////////////click///////////////////////
	@Override
	public void onClick(View v) {
		if (v.equals(orderListll)) {

		}

	}
}