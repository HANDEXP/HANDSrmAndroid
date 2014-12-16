package com.hand.srm.activities;

import java.util.HashMap;

import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.hand.srm.R;
import com.hand.srm.layout.MyLayout;
import com.hand.srm.layout.MyLayout.OnSoftKeyboardListener;
import com.hand.srm.model.LoginModel;
import com.littlemvc.model.LMModel;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;
import com.mas.customview.ProgressDialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class LoginActivity extends SherlockActivity implements OnClickListener,
		LMModelDelegate {

	private Button loginBtn;
	private TextView usernameTextView;
	private TextView passwordTextView;
	private ProgressDialog dialog;
	private MyLayout myLayout;
	private LinearLayout iconLL;
	LoginModel model;

	HashMap<String, String> loginParm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		model = new LoginModel(this);
		loginParm = new HashMap<String, String>();
		buildAllViews();
	}

	private void buildAllViews() {
		// 登录按钮
		loginBtn = (Button) findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(this);
		// 用户名栏
		usernameTextView = (TextView) findViewById(R.id.username);
		usernameTextView.setOnClickListener(this);
		// 密码栏
		passwordTextView = (TextView) findViewById(R.id.password);
		passwordTextView.setOnClickListener(this);
		passwordTextView.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO 自动生成的方法存根
				if(actionId == EditorInfo.IME_ACTION_DONE){
					InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
					loginRequest();
					return true;
				}
				return false;
			}
		});
//		iconLL = (LinearLayout) findViewById(R.id.Iconll);
		myLayout = (MyLayout) findViewById(R.id.mylayout);
		myLayout.setOnSoftKeyboardListener(new OnSoftKeyboardListener() {
			
			@Override
			public void onShown() {
				// TODO 自动生成的方法存根
//				if(loginBtn.getHeight() < 60){
//					iconLL.setPadding(0, 0, 0, 20);
//				}
//				Toast.makeText(getApplicationContext(), "Show"+String.valueOf(loginBtn.getHeight()), Toast.LENGTH_SHORT).show();
//				iconLL.setBackgroundResource(R.drawable.picl);
				
			}
			
			@Override
			public void onHidden() {
				// TODO 自动生成的方法存根
				
//				Toast.makeText(getApplicationContext(), "Hidden"+String.valueOf(loginBtn.getHeight()), Toast.LENGTH_SHORT).show();
//				iconLL.setBackgroundResource(R.drawable.pic);
			}
		});
		setDefaultInfo();
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.loginBtn:
			loginRequest();
			break;

		default:
			break;
		}
	}

	/**
	 * 生成登录参数
	 */
	private void generateParm() {

		loginParm.put("user_name", usernameTextView.getText().toString());
		loginParm.put("user_password", passwordTextView.getText().toString());
		// loginParm.put("device_id", ((TelephonyManager)
		// getSystemService(TELEPHONY_SERVICE)).getDeviceId());
	}

	private void loginRequest(){
		if (usernameTextView.getText().toString().equals("")) {
			Toast.makeText(getApplicationContext(), "请输入用户名",
					Toast.LENGTH_SHORT).show();
			return;
		}
		;
		if (passwordTextView.getText().toString().equals("")) {
			Toast.makeText(getApplicationContext(), "请输入密码",
					Toast.LENGTH_SHORT).show();
			return;
		}
		generateParm();
		dialog = new ProgressDialog(this, "登录中，请稍后");
		dialog.show();
		model.load(loginParm);		
	}
	@Override
	public void modelDidFinshLoad(LMModel model) {
		// TODO 自动生成的方法存根
		
		AsHttpRequestModel reponseModel = (AsHttpRequestModel) model;
		String json = new String(reponseModel.mresponseBody);
		try {
			JSONObject jsonobj = new JSONObject(json);
			String code = ((JSONObject) jsonobj.get("head")).get("code")
					.toString();
			if (code.equals("ok")) {
				String description = ((JSONObject) jsonobj.get("body")).get("description")
						.toString();
				saveUserData(description);
				
				Intent intent = new Intent(getApplicationContext(),
						MenuActivity.class);

				startActivity(intent);
				finish();
			} else if (code.equals("failure")) {
				String msg = ((JSONObject) jsonobj.get("body")).get("error_message").toString();
				Toast.makeText(getApplicationContext(), msg,
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(), "服务器返回数据解析失败",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} finally {
			dialog.dismiss();
		}
		;
	}

	@Override
	public void modelDidStartLoad(LMModel model) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void modelDidFaildLoadWithError(LMModel model) {
		// TODO 自动生成的方法存根
		dialog.dismiss();
		Toast.makeText(getApplicationContext(), "无法连接到指定服务器", Toast.LENGTH_LONG)
				.show();
	}

	private void saveUserData(String description) {
		SharedPreferences preferences = getSharedPreferences("userInfo",
				Context.MODE_APPEND);
		// Toast.makeText(getApplicationContext(),
		// "userName:"+loginParm.get("user_name"),
		// Toast.LENGTH_SHORT).show();
		// Toast.makeText(getApplicationContext(),
		// "userPassword:"+loginParm.get("user_password"),
		// Toast.LENGTH_SHORT).show();
		String userName = loginParm.get("user_name");
		String userPassword = loginParm.get("user_password");
		preferences.edit().putString("userName", userName).commit();
		preferences.edit().putString("userPassword", userPassword).commit();
		preferences.edit().putString("description", description).commit();
	}

	/**
	 * 检查是否已经设置手势密码并启用手势密码
	 * 
	 * @return
	 */

	private void setDefaultInfo() {
		SharedPreferences preferences = getSharedPreferences("userInfo",
				Context.MODE_APPEND);
		String userName = preferences.getString("userName", "");
		String userPassword = preferences.getString("userPassword", "");
		// 设置
		usernameTextView.setText(userName);
		passwordTextView.setText(userPassword);
	}

}
