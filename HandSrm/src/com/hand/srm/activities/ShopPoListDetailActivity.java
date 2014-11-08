package com.hand.srm.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.hand.srm.R;
import com.hand.srm.adapter.ShopPoListDeatilAdapter;
import com.hand.srm.model.ShopPoListDetailModel;
import com.hand.srm.model.ShopPoListDetailSvcModel;
import com.hand.srm.model.ShopPoListSvcModel;
import com.littlemvc.model.LMModel;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;

public class ShopPoListDetailActivity extends SherlockActivity implements OnClickListener, LMModelDelegate{
	
	private TextView consignerTextView;
	private TextView consignerOrganizationTextView;
	private TextView orderTypeTextView;
	private ListView listDetail;
	private ShopPoListDeatilAdapter adapter;
	private ShopPoListDetailSvcModel model;
	private HashMap<String, String> parm;
	private List<ShopPoListDetailModel> data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_po_detail);
		model = new ShopPoListDetailSvcModel(this);
		parm = new HashMap<String, String>();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		bindAllViews();
		Intent intent = getIntent();
		String purHeaderId = intent.getStringExtra("purHeaderId");
		parm.put("pur_header_id", purHeaderId);
//		Toast.makeText(getApplicationContext(), purHeaderId, Toast.LENGTH_SHORT).show();
		model.load(parm);
		
	}

	private void bindAllViews(){
		consignerTextView = (TextView) findViewById(R.id.consignerTextView);
		consignerOrganizationTextView = (TextView) findViewById(R.id.consignerOrganizationTextView);
		orderTypeTextView = (TextView) findViewById(R.id.orderTypeTextView);
		listDetail = (ListView) findViewById(R.id.listDetail);
		
	}
	
	/**
	 * 
	 * 设置头信息
	 * @throws JSONException 
	 * 
	 */
	private Integer setHeader(JSONObject jsonObj) throws JSONException{
		String consignerString = jsonObj.get("ship_to_location_name").toString();
		String consignerOrganizationString = jsonObj.get("inv_organization_name").toString();
		String orderTypeString = jsonObj.get("type_lookup_desc").toString();
		consignerTextView.setText(consignerString);
		consignerOrganizationTextView.setText(consignerOrganizationString);
		orderTypeTextView.setText(orderTypeString);
		return Integer.valueOf(jsonObj.get("line_count").toString());
		
	}
	
	private void setDetail(JSONArray jsonArr) throws JSONException{
		data = new ArrayList<ShopPoListDetailModel>();
		int length = jsonArr.length();
		for(int i = 0; i< length; i+=1){
			JSONObject item = new JSONObject();
			item = (JSONObject) jsonArr.get(i);
			data.add(new ShopPoListDetailModel(item.getString("item_code"), item.getString("item_description"), item.getString("unit_price"), item.getString("quantity"), item.getString("unit_meas_lookup_code"),item.getString("line_amount")));
		}
		adapter = new ShopPoListDeatilAdapter(data, getApplicationContext());
		listDetail.setAdapter(adapter);
	}
	
	@Override
	public void modelDidFinshLoad(LMModel model) {
		// TODO 自动生成的方法存根
		AsHttpRequestModel reponseModel = (AsHttpRequestModel) model;
		String json = new String(reponseModel.mresponseBody);
		Toast.makeText(getApplicationContext(), "modelDidFinshLoad",
				Toast.LENGTH_SHORT).show();
		try {
			JSONObject jsonObj = new JSONObject(json);
			String code = ((JSONObject)jsonObj.get("head")).get("code").toString();
			if(code.equals("ok")) {
				JSONObject headerObj = (JSONObject) ((JSONObject) jsonObj.get("body")).get("header");
				if(!setHeader(headerObj).equals(0)){
					JSONArray linesArr = (JSONArray) ((JSONObject) jsonObj.get("body")).get("lines");
					setDetail(linesArr);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void modelDidStartLoad(LMModel model) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void modelDidFaildLoadWithError(LMModel model) {
		// TODO 自动生成的方法存根
		Toast.makeText(getApplicationContext(), "请检查网络设置",
				Toast.LENGTH_SHORT).show();		
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		
	}
}
