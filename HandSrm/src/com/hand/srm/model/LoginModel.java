package com.hand.srm.model;

import java.util.HashMap;

import android.net.ParseException;

import com.hand.srm.utl.ConstantUtl;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;
import com.littlemvc.utl.AsNetWorkUtl;

public class LoginModel extends AsHttpRequestModel {

//	private  XmlConfigReader configReader;	
	
	public LoginModel(LMModelDelegate delegate) {
		super(delegate);
		// TODO 自动生成的构造函数存根
//		configReader = XmlConfigReader.getInstance();
	}
	
	public void load(HashMap parm){
		try {
//			String queryUrl = configReader
//			        .getAttr(new Expression(
//			                "/backend-config/url[@name='login_submit_url']",
//			                "value"));
			
			String queryUrl = ConstantUtl.loginUrl;
			
			AsNetWorkUtl.removeAllCookies();
			
			this.post(queryUrl, parm);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
}
