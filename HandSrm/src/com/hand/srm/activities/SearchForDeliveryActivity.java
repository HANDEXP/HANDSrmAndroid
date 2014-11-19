package com.hand.srm.activities;

import java.util.HashMap;

import com.hand.srm.R;
import com.hand.srm.model.SearchForDeliverySvcModel;
import com.littlemvc.model.LMModel;
import com.littlemvc.model.LMModelDelegate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class SearchForDeliveryActivity extends Activity implements
		LMModelDelegate, OnClickListener {

	private EditText deliveryNoEditView;
	private EditText deliveryDateFromEditView;
	private EditText deliveryDateToEditView;
	private CheckBox excludeConfirmFlagCheckBox;
	private CheckBox urgentStatusCheckBox;
	private SearchForDeliverySvcModel model;
	private Button searchButton;
	HashMap<String, String> loginParm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search_for_deliverying);
		model = new SearchForDeliverySvcModel(this);
		bindAllViews();
	}

	/**
	 * 
	 * 绑定Views
	 * 
	 */
	private void bindAllViews() {
		deliveryNoEditView = (EditText) findViewById(R.id.deliveryNoView);
		deliveryDateFromEditView = (EditText) findViewById(R.id.deliveryDateFrom);
		deliveryDateToEditView = (EditText) findViewById(R.id.deliveryDateTo);
		excludeConfirmFlagCheckBox = (CheckBox) findViewById(R.id.excludeConfirmFlag);
		urgentStatusCheckBox = (CheckBox) findViewById(R.id.urgentStatus);
		searchButton = (Button) findViewById(R.id.search4DeliveryButton);
		searchButton.setOnClickListener(this);

	}

	/**
	 * 生成登录参数
	 */
	private void generateParm() {

		loginParm.put("po_num", deliveryNoEditView.getText().toString());
		loginParm.put("exclude_confirm_flag", excludeConfirmFlagCheckBox.isChecked() ? "Y" : "N");
		loginParm.put("urgent_status", urgentStatusCheckBox.isChecked() ? "Y" : "N");
		loginParm.put("release_date_from", deliveryDateFromEditView.getText().toString());
		loginParm.put("release_date_to", deliveryDateToEditView.getText().toString());
	}

	@Override
	public void modelDidFinshLoad(LMModel model) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void modelDidStartLoad(LMModel model) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void modelDidFaildLoadWithError(LMModel model) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.search4DeliveryButton:
			generateParm();
			model.load(loginParm);
			break;

		default:
			break;
		}
	}
}
