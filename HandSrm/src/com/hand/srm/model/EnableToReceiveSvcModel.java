package com.hand.srm.model;

import com.hand.srm.utl.ConstantUtl;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;
import com.littlemvc.utl.AsNetWorkUtl;

public class EnableToReceiveSvcModel extends AsHttpRequestModel {

	public EnableToReceiveSvcModel(LMModelDelegate delegate) {
		super(delegate);
		// TODO 自动生成的构造函数存根
	}
	
	public void load(){
		try {
			String queryUrl = ConstantUtl.receiveUrl;
			AsNetWorkUtl.removeAllCookies();
			this.post(queryUrl, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}