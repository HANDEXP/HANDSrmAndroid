package com.hand.srm.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
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
import com.mas.customview.ProgressDialog;

public class EnableToReceiveDetailActivity extends SherlockActivity implements OnClickListener, LMModelDelegate{
	
	private TextView locationNameTextView;
	private TextView consignerOrganizationTextView;
	private ListView listDetail;
	private EnableToReceiveDeatilAdapter adapter;
	private EnableToReceiveDetailSvcModel model;
	private HashMap<String, String> parm;
	private List<EnableToReceiveDetailModel> data;
	private ImageButton backTextView;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_receive_detail);
		model = new EnableToReceiveDetailSvcModel(this);
		parm = new HashMap<String, String>();
		dialog = new ProgressDialog(this, "数据加载中，请稍后");
		dialog.show();
	}
	@Override  
    public void onBackPressed() {  
        // do something what you want  
//        super.onBackPressed();  
		finishWithAnime();
		
    }  	
	@Override
	protected void onResume(){
		super.onResume();
		bindAllViews();
		Intent intent = getIntent();
		String asnHeaderId = intent.getStringExtra("purHeaderId");
		parm.put("asn_header_id", asnHeaderId);
//		Toast.makeText(getApplicationContext(), purHeaderId, Toast.LENGTH_SHORT).show();
		model.load(parm);
		
	}

	private void bindAllViews(){
		locationNameTextView = (TextView) findViewById(R.id.locationNameTextView);
		consignerOrganizationTextView = (TextView) findViewById(R.id.consignerOrganizationTextView);
		listDetail = (ListView) findViewById(R.id.listDetail);
		backTextView = (ImageButton) findViewById(R.id.backTextView);
		backTextView.setOnClickListener(this);
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
		} finally {
			dialog.dismiss();
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
		switch (v.getId()) {
		case R.id.backTextView:
			finishWithAnime();			
			break;

		default:
			break;
		}
	}
	private void finishWithAnime(){
		finish();
		overridePendingTransition(R.anim.alpha_in, R.anim.move_right_out_activity);
	}
}
