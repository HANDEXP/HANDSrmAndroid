package com.hand.srm.application;

import com.hand.srm.utl.ConstantUtl;
import com.littlemvc.model.request.AsHttpRequestModel;
import com.littlemvc.utl.AsNetWorkUtl;

import android.app.Application;

public class SrmApplication extends Application{

	static SrmApplication application;
	
	public static SrmApplication getApplication()
	{
		
		return  application;		
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		application = this;
		
		//×°ÅäÍøÂç¹¤¾ß
		AsNetWorkUtl.application = this;
		AsHttpRequestModel.utl =AsNetWorkUtl.getAsNetWorkUtl(ConstantUtl.basicUrl);
		
		
	}
	
}
