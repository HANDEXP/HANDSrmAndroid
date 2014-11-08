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
import com.hand.srm.adapter.EnableToReceiveDeatilAdapter;
import com.hand.srm.model.EnableToReceiveDetailModel;
import com.hand.srm.model.EnableToReceiveDetailSvcModel;
import com.littlemvc.model.LMModel;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;

public class EnableToReceiveDetailActivity extends SherlockActivity implements OnClickListener, LMModelDelegate{
	
	private TextView locationNameTextView;
	private TextView consignerOrganizationTextView;
	private ListView listDetail;
	private EnableToReceiveDeatilAdapter adapter;
	private EnableToReceiveDetailSvcModel model;
	private HashMap<String, String> parm;
	private List<EnableToReceiveDetailModel> data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_po_detail);
		model = new EnableToReceiveDetailSvcModel(this);
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
		locationNameTextView = (TextView) findViewById(R.id.locationNameTextView);
		consignerOrganizationTextView = (TextView) findViewById(R.id.consignerOrganizationTextView);
		listDetail = (ListView) findViewById(R.id.listDetail);
		
	}
	
	/**
	 * 
	 * 设置头信息
	 * @throws JSONException 
	 * 
	 */
	private Boolean setHeader(JSONObject jsonObj) throws JSONException{
		String locationNameString = jsonObj.get("location_name").toString();
		String consignerOrganizationString = jsonObj.get("ship_to_organization_name").toString();
		locationNameTextView.setText(locationNameString);
		consignerOrganizationTextView.setText(consignerOrganizationString);
		return true;
		
	}
	
	private void setDetail(JSONArray jsonArr) throws JSONException{
		data = new ArrayList<EnableToReceiveDetailModel>();
		int length = jsonArr.length();
		for(int i = 0; i< length; i+=1){
			JSONObject item = new JSONObject();
			item = (JSONObject) jsonArr.get(i);
			data.add(new EnableToReceiveDetailModel(item.getString("item_code"), item.getString("item_name"), item.getString("po_num"), item.getString("ship_quantity"), item.getString("receive_quantity"), item.getString("on_the_way_quantity"), item.getString("uom_name"), item.getString("line_status")));
		}
//		adapter = new ShopPoListDeatilAdapter(data, getApplicationContext());
		adapter = new EnableToReceiveDeatilAdapter(data, getApplicationContext());
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
				if(setHeader(headerObj)){
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
