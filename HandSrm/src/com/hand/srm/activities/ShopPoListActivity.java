package com.hand.srm.activities;

import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.hand.srm.R;
import com.hand.srm.adapter.ShopPoListAdapter;
import com.hand.srm.model.ShopPoListHeaderModel;
import com.hand.srm.model.ShopPoListSvcModel;
import com.littlemvc.model.LMModel;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;

public class ShopPoListActivity extends SherlockActivity implements OnClickListener,LMModelDelegate{
	private List<List<String>> group;
	private List<List<ShopPoListHeaderModel>> child;
	private ShopPoListAdapter adapter;
	private ExpandableListView shopPoListView;
	private ShopPoListSvcModel model;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ship_po_list);
		model = new ShopPoListSvcModel(this);
		
		bindAllViews();
	}

	/**
	 * 绑定View
	 * 
	 */
	private void bindAllViews(){
		model.load();
		
	}
	
	/**
	 * 初始化数据
	 * 
	 * 
	 */
	private void initializeData(){
		
	}
	
	@Override
	public void modelDidFinshLoad(LMModel model) {
		// TODO 自动生成的方法存根
		Toast.makeText(getApplicationContext(), "modelDidFinshLoad", Toast.LENGTH_SHORT).show();
		AsHttpRequestModel reponseModel = (AsHttpRequestModel) model;
		String json = new String(reponseModel.mresponseBody);
		try {
			JSONObject jsonobj = new JSONObject(json);
			String code = ((JSONObject) jsonobj.get("head")).get("code").toString();
			if(code.equals("ok")){
				
				finish();
			}else if(code.equals("failure")){
				Toast.makeText(getApplicationContext(), "服务器错误", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(), "网络繁忙请稍后再试", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} finally{
			
		};		
	}

	@Override
	public void modelDidStartLoad(LMModel model) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void modelDidFaildLoadWithError(LMModel model) {
		// TODO 自动生成的方法存根
		Toast.makeText(getApplicationContext(), "modelDidFaildLoadWithError", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		
	}

}
