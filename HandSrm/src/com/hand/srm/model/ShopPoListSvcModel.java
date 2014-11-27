package com.hand.srm.model;

import java.util.HashMap;

import com.hand.srm.utl.ConstantUtl;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;
import com.littlemvc.utl.AsNetWorkUtl;

public class ShopPoListSvcModel extends AsHttpRequestModel {

	public ShopPoListSvcModel(LMModelDelegate delegate) {
		super(delegate);
		// TODO 自动生成的构造函数存根
	}
	
	public void load(){
		try {
			String queryUrl = ConstantUtl.appPoInfoUrl;
			this.post(queryUrl, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void search(HashMap parm){
		try {
			String queryUrl = ConstantUtl.appPoInfoUrl;
			this.post(queryUrl, parm);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
