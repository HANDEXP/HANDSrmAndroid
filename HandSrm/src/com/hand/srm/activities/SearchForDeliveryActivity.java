package com.hand.srm.activities;

import java.util.HashMap;

import com.hand.srm.R;
import com.hand.srm.dialogs.DatePickerWrapDialog;
import com.hand.srm.model.SearchForDeliverySvcModel;
import com.littlemvc.model.LMModel;
import com.littlemvc.model.LMModelDelegate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchForDeliveryActivity extends Activity implements
		OnClickListener {

	private EditText deliveryNoEditView;
	private TextView deliveryDateFromTextView;
	private TextView deliveryDateToTextView;
	private DatePickerWrapDialog dateFromDateDialog;
	private DatePickerWrapDialog dateToDateDialog;
	private CheckBox excludeConfirmFlagCheckBox;
	private CheckBox urgentStatusCheckBox;
	private Button searchButton;
	HashMap<String, String> searchParm;
	public static int RETURN_PARAMETER = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search_for_deliverying);
		searchParm = new HashMap<String, String>();
		
		bindAllViews();
	}

	/**
	 * 
	 * 绑定Views
	 * 
	 */
	private void bindAllViews() {
		deliveryNoEditView = (EditText) findViewById(R.id.deliveryNoView);
		deliveryDateFromTextView = (TextView) findViewById(R.id.deliveryDateFrom);
		deliveryDateFromTextView.setOnClickListener(this);
		dateFromDateDialog = new DatePickerWrapDialog(this, deliveryDateFromTextView);
		deliveryDateToTextView = (TextView) findViewById(R.id.deliveryDateTo);
		deliveryDateToTextView.setOnClickListener(this);
		dateToDateDialog = new DatePickerWrapDialog(this, deliveryDateToTextView);
		excludeConfirmFlagCheckBox = (CheckBox) findViewById(R.id.excludeConfirmFlag);
		urgentStatusCheckBox = (CheckBox) findViewById(R.id.urgentStatus);
		searchButton = (Button) findViewById(R.id.search4DeliveryButton);
		searchButton.setOnClickListener(this);

	}

	/**
	 * 生成登录参数
	 */
	private void generateParm() {

		searchParm.put("po_num", deliveryNoEditView.getText().toString());
		searchParm.put("exclude_confirm_flag", excludeConfirmFlagCheckBox.isChecked() ? "Y" : "N");
		searchParm.put("urgent_status", urgentStatusCheckBox.isChecked() ? "Y" : "N");
		searchParm.put("release_date_from", deliveryDateFromTextView.getText().toString());
		searchParm.put("release_date_to", deliveryDateToTextView.getText().toString());
	}


	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.search4DeliveryButton:
			generateParm();
//			model.load(searchParm);
			Intent data = new Intent();
			data.putExtra("searchParm", searchParm);
			setResult(RETURN_PARAMETER, data);
			finish();

			break;
		case R.id.deliveryDateFrom:
			dateFromDateDialog.showDateDialog();
			break;
		case R.id.deliveryDateTo:
			dateToDateDialog.showDateDialog();
			break;
		default:
			break;
		}
	}
}
