package com.hand.srm.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hand.hrms4android.parser.Expression;
import com.hand.hrms4android.parser.xml.XmlConfigReader;
import com.hand.srm.R;
import com.hand.srm.application.SrmApplication;
import com.hand.srm.model.LoadingModel;
import com.hand.srm.utl.ConstantUtl;
import com.littlemvc.model.LMModel;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;
import com.littlemvc.utl.AsNetWorkUtl;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoadingActivity extends Activity implements LMModelDelegate{
	
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private String baseUrl;

    private LoadingModel model;

    private TextView mBasicUrlTextView;
    private EditText mBasicUrlEditText;
    private LinearLayout mBasicUrlLL;
    private Button mConfirmButton;
    private Button mReloadButton;	
	
	
	
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		model = new LoadingModel(this);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        
        baseUrl = mPreferences.getString("sys_basic_url", "http://");
        bindAllViews();
	}
	 

	 
	 @Override
	protected void onResume() {
		 super.onResume();
		 
		 if(!baseUrl.equals("") && !baseUrl.equals("http://") && getIntent().getAction() != null){
			 doReload();
		 }
	 }


	@Override
	public void modelDidFinshLoad(LMModel model) {
		
		System.out.println("FinishLoad");
		if (model.equals(this.model)){
			File dir = SrmApplication.getApplication().getDir(ConstantUtl.SYS_PREFRENCES_CONFIG_FILE_DIR_NAME, Context.MODE_PRIVATE);
			File configFile = new File(dir,ConstantUtl.configFile);
			FileOutputStream fileOutputStream = null;
			try {
                fileOutputStream = new FileOutputStream(configFile);
                fileOutputStream.write(this.model.mresponseBody);
                SrmApplication.getApplication().reader = XmlConfigReader.getInstance();
                SrmApplication.getApplication().reader.getAttr(new Expression("/backend-config", ""));
                fileOutputStream.close();
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(this, "读写配置文件出现错误", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
				return;
			}finally {
				ConstantUtl.basicUrl = baseUrl;
				mEditor.putString("sys_basic_url",baseUrl);
				mEditor.commit();
				Intent intent = new Intent(LoadingActivity.this,LoginActivity.class);
				finish();
				startActivity(intent);
			}
		}
	}


	@Override
	public void modelDidStartLoad(LMModel model) {
		System.out.println("startLoad");
	}


	@Override
	public void modelDidFaildLoadWithError(LMModel model) {
		
		System.out.println("LoadWithError");
        if (model.equals(this.model)) {
            Toast.makeText(this, "未找到配置文件", Toast.LENGTH_SHORT).show();
        }
        ConstantUtl.basicUrl = baseUrl;
        mEditor.putString("sys_basic_url",baseUrl);
        mEditor.commit();
        Intent intent = new Intent(LoadingActivity.this,LoginActivity.class);
        finish();
        startActivity(intent);		
		
	}

    private void bindAllViews(){
        mBasicUrlTextView = (TextView) findViewById(R.id.basic_url_text_view);
        mReloadButton = (Button) findViewById(R.id.reload_button);
        mBasicUrlLL = (LinearLayout) findViewById(R.id.basic_url_setting_ll);

        mBasicUrlLL.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				View contentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_dialog, null);
				final EditText editText = (EditText) contentView.findViewById(R.id.edit_text_in_dialog);
				editText.setText(baseUrl);
				editText.setSelectAllOnFocus(true);
                new AlertDialog.Builder(LoadingActivity.this)
                .setTitle("Server Address")
                .setView(contentView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO 自动生成的方法存根
						baseUrl = editText.getText().toString();
						mBasicUrlTextView.setText(baseUrl);
					}
				})
                .setNegativeButton("取消", null).show();		
                
			}
		});
        mBasicUrlTextView.setText(baseUrl);
        mReloadButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                doReload();
            }
        });

    }
    
    /**
     * 请求配置文件 
     */
    private void doReload(){
        AsHttpRequestModel.utl = AsNetWorkUtl.getAsNetWorkUtl(baseUrl);
        String regex = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]" ;
        Pattern pattern = Pattern. compile(regex);
        Matcher matcher = pattern.matcher(baseUrl);
        boolean isMatch = matcher.matches();
        if (!isMatch){
            Toast.makeText(this,"地址不合法",Toast.LENGTH_SHORT).show();
        }else {
            this.model.load();
        }
    }
	 
}
