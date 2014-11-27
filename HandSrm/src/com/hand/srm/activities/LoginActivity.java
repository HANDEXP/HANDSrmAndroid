	package com.hand.srm.activities;

import java.util.HashMap;

import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.hand.srm.R;
import com.hand.srm.model.LoginModel;
import com.littlemvc.model.LMModel;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends SherlockActivity implements OnClickListener,LMModelDelegate {

	private Button loginBtn;
	private TextView usernameTextView;
	private TextView passwordTextView;
	
	LoginModel model;
	
	HashMap<String, String> loginParm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.activity_login);
		model = new LoginModel(this);
		loginParm = new HashMap<String, String>();
		buildAllViews();
	}

	private void buildAllViews(){
		//登录按钮
		loginBtn = (Button) findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(this);
		//用户名栏
		usernameTextView = (TextView) findViewById(R.id.username);
		usernameTextView.setOnClickListener(this);
		//密码栏
		passwordTextView = (TextView) findViewById(R.id.password);
		passwordTextView.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.loginBtn:
			if(usernameTextView.getText().toString().equals("")){
				Toast.makeText(getApplicationContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
				return;
			};
			if(passwordTextView.getText().toString().equals("")){
				Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
				return;
			}
			generateParm();
			model.load(loginParm);
			break;

		default:
			break;
		}
	}

	/**
	 * 生成登录参数 	
	 */
		private void generateParm(){
			
			loginParm.put("user_name", usernameTextView.getText().toString());
			loginParm.put("user_password", passwordTextView.getText().toString());
//			loginParm.put("device_id",  ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId());	
		}	
	
	@Override
	public void modelDidFinshLoad(LMModel model) {
		// TODO 自动生成的方法存根
		AsHttpRequestModel reponseModel = (AsHttpRequestModel) model;
		String json = new String(reponseModel.mresponseBody);
		try {
			JSONObject jsonobj = new JSONObject(json);
			String code = ((JSONObject) jsonobj.get("head")).get("code").toString();
			if(code.equals("ok")){
				saveUserData();

				Intent intent = new Intent(getApplicationContext(),MenuActivity.class);

				startActivity(intent);
				finish();
			}else if(code.equals("failure")){
				Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
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
		Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
	}
	
	private void saveUserData() {
		SharedPreferences preferences=getSharedPreferences("userInfo",Context.MODE_APPEND);
//		Toast.makeText(getApplicationContext(), "userName:"+loginParm.get("user_name"),
//				Toast.LENGTH_SHORT).show();
//		Toast.makeText(getApplicationContext(), "userPassword:"+loginParm.get("user_password"),
//				Toast.LENGTH_SHORT).show();
		String userName = loginParm.get("user_name");
		String userPassword = loginParm.get("user_password");
		preferences.edit().putString("userName",userName).commit();
		preferences.edit().putString("userPassword",userPassword).commit();
	}	
	
}
