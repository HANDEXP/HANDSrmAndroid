package com.hand.srm.application;

import com.hand.hrms4android.parser.ConfigReader;
import com.hand.srm.utl.ConstantUtl;
import com.littlemvc.model.request.AsHttpRequestModel;
import com.littlemvc.utl.AsNetWorkUtl;

import android.app.Application;

public class SrmApplication extends Application{

	static SrmApplication application;
	public ConfigReader reader;
	
	public static SrmApplication getApplication()
	{
		
		return  application;		
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		application = this;
		

		AsNetWorkUtl.application = this;
//		AsHttpRequestModel.utl =AsNetWorkUtl.getAsNetWorkUtl(ConstantUtl.basicUrl);
		
		
	}
	
}
