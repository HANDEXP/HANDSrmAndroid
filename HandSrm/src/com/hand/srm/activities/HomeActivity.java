package com.hand.srm.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hand.srm.R;
import com.hand.srm.adapter.FunctionGridAdapter;
import com.hand.srm.item.FunctionItem;
import com.hand.srm.model.HomeModel;
import com.littlemvc.model.LMModel;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.AdapterView.OnItemClickListener;

public class HomeActivity extends Activity implements OnClickListener,LMModelDelegate {

	private LinearLayout orderListll;

	private GridView mGridView;

	private List<FunctionItem> functionList;
	
	private LinearLayout  firstLL;
	
	private LinearLayout  secondLL;
	
	private TextView   toShipTextView;
	
	private TextView   toRecvTextView;
	
	private HomeModel model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		prepare();
		buildAllViews();

		model = new HomeModel(this);
		model.load();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

////////////////////private////////////////////////////////////////////////////
	private void prepare() {
		
		
		FunctionItem item1 = new FunctionItem("预制采购单", R.drawable.ic_category_0, null);

		FunctionItem item2 = new FunctionItem("供应商名录", R.drawable.ic_category_1, null);

		FunctionItem item3 = new FunctionItem("报表仪表盘", R.drawable.ic_category_2, new Intent(this,ChartListActivity.class));

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
		
		toShipTextView = (TextView) findViewById(R.id.toShipTextView);
		
		toRecvTextView  = (TextView) findViewById(R.id.toRecvTextView);
		
		
		mGridView = (GridView) findViewById(R.id.mGridView);
		mGridView.setAdapter(new FunctionGridAdapter(functionList, this));
		
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println(functionList.get(position).title);
				Intent intent = functionList.get(position).intent;
				if(intent != null){
					
					startActivity(intent);
					
				}
				
				
			}
			
			
		});
		
		firstLL = (LinearLayout) findViewById(R.id.firstLL);
		secondLL = (LinearLayout) findViewById(R.id.secondLL);
		secondLL.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent =  new Intent(HomeActivity.this,EnableToReceiveActivity.class);
				intent.putExtra("to_receive_flag", true);
				startActivity(intent);
			}
		});
		
		
		firstLL.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent =  new Intent(HomeActivity.this,ShopPoListActivity.class);
				intent.putExtra("to_ship_flag", true);
				startActivity(intent);
				
			}
		});
		
	}
	
	private void setHomeAmount(JSONArray amounts)
	{
		
		for(int i = 0;i< amounts.length();i++)
		{
			try {
				
				String enableAmount = amounts.getJSONObject(i).getString("enable_amount");
				String enableSegment = amounts.getJSONObject(i).getString("enable_segment");
				if(enableSegment.equalsIgnoreCase("ENABLE_TO_RECEIVE")){
					
					toRecvTextView.setText(enableAmount);
					
				}else if(enableSegment.equalsIgnoreCase("ENABLE_TO_SHIP")){
					
					toShipTextView.setText(enableAmount);
				}
				
			} catch (JSONException e) {
				Toast.makeText(getApplicationContext(), "服务器返回数值接口错误",
						Toast.LENGTH_SHORT).show();
				e.printStackTrace();
				return;
			
			}
			
		}
		
	}

/////////////click///////////////////////
	@Override
	public void onClick(View v) {
		if (v.equals(orderListll)) {

		}

	}
///////////////////////model  delegate/////////////////////////
	@Override
	public void modelDidFinshLoad(LMModel model) {
		
		AsHttpRequestModel reponseModel = (AsHttpRequestModel) model;
		
		try {
			String json = new String(reponseModel.mresponseBody);
			JSONObject jsonObj = new JSONObject(json);

			String code = ((JSONObject) jsonObj.get("head")).get("code")
					.toString();
			if(code.equalsIgnoreCase("ok")){
				
				setHomeAmount(jsonObj.getJSONArray("body"));
			}else {
				
				
				Toast.makeText(getApplicationContext(), "获取首页数值失败错误信息为" +  jsonObj.getJSONObject("head").getString("message"),
						Toast.LENGTH_SHORT).show();
			}
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		}

	}

	@Override
	public void modelDidStartLoad(LMModel model) {
		
	}

	@Override
	public void modelDidFaildLoadWithError(LMModel model) {
		Toast.makeText(getApplicationContext(), "网络请求失败",
				Toast.LENGTH_SHORT).show();
	}
}
