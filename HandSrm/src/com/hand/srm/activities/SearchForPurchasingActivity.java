package com.hand.srm.activities;

import java.util.HashMap;

import com.hand.srm.R;
import com.hand.srm.dialogs.DatePickerWrapDialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class SearchForPurchasingActivity extends Activity implements
		OnClickListener {

	private EditText purchaseNoEditView;
	private TextView purchaseDateFromTextView;
	private TextView purchaseDateToTextView;
	private DatePickerWrapDialog dateFromDateDialog;
	private DatePickerWrapDialog dateToDateDialog;
	private ImageButton backImgBtn;
	private Button searchButton;
	HashMap<String, String> searchParm;
	public static int RETURN_PARAMETER = 1;
	private CheckBox excludeConfirmFlagCheckBox;
	private CheckBox urgentStatusCheckBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search_for_purchasing);
		searchParm = new HashMap<String, String>();
		
		bindAllViews();
	}

	/**
	 * 
	 * 绑定Views
	 * 
	 */
	private void bindAllViews() {
		purchaseNoEditView = (EditText) findViewById(R.id.purchaseNoView);
		purchaseDateFromTextView = (TextView) findViewById(R.id.purchaseDateFrom);
		purchaseDateFromTextView.setOnClickListener(this);
		dateFromDateDialog = new DatePickerWrapDialog(this, purchaseDateFromTextView);
		purchaseDateToTextView = (TextView) findViewById(R.id.purchaseDateTo);
		purchaseDateToTextView.setOnClickListener(this);
		dateToDateDialog = new DatePickerWrapDialog(this, purchaseDateToTextView);
		searchButton = (Button) findViewById(R.id.search4PurchasingButton);
		searchButton.setOnClickListener(this);
		backImgBtn = (ImageButton) findViewById(R.id.backImgBtn);
		backImgBtn.setOnClickListener(this);
		excludeConfirmFlagCheckBox = (CheckBox) findViewById(R.id.excludeConfirmFlag);
		urgentStatusCheckBox = (CheckBox) findViewById(R.id.urgentStatus);

	}

	/**
	 * 生成登录参数
	 */
	private void generateParm() {

//		searchParm.put("asn_num", purchaseNoEditView.getText().toString());
//		searchParm.put("ship_date_from", purchaseDateFromTextView.getText().toString());
//		searchParm.put("ship_date_to", purchaseDateToTextView.getText().toString());
///////////////////////////////////////////////////////////////////////////////////////////
		searchParm.put("po_num", purchaseNoEditView.getText().toString());
		searchParm.put("release_date_from", purchaseDateFromTextView.getText().toString());
		searchParm.put("release_date_to", purchaseDateToTextView.getText().toString());
		
//		searchParm.put("po_num", deliveryNoEditView.getText().toString());
		searchParm.put("exclude_confirm_flag", excludeConfirmFlagCheckBox.isChecked() ? "Y" : "N");
		searchParm.put("urgent_status", urgentStatusCheckBox.isChecked() ? "Y" : "N");
//		searchParm.put("release_date_from", deliveryDateFromTextView.getText().toString());
//		searchParm.put("release_date_to", deliveryDateToTextView.getText().toString());
	}


	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.search4PurchasingButton:
			generateParm();
			Intent data = new Intent();
			data.putExtra("searchParm", searchParm);
			setResult(RETURN_PARAMETER, data);
			finish();
			break;
		case R.id.purchaseDateFrom:
			dateFromDateDialog.showDateDialog();
			break;
		case R.id.purchaseDateTo:
			dateToDateDialog.showDateDialog();
			break;
		case R.id.backImgBtn:
			finish();
			overridePendingTransition(R.anim.move_left_in_activity,
					R.anim.move_right_out_activity);			
		default:
			break;
		}
	}
}
