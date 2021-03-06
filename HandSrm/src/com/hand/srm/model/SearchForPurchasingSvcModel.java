package com.hand.srm.model;

import java.util.HashMap;

import com.hand.hrms4android.parser.Expression;
import com.hand.hrms4android.parser.xml.XmlConfigReader;
import com.hand.srm.utl.ConstantUtl;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;
import com.littlemvc.utl.AsNetWorkUtl;

public class SearchForPurchasingSvcModel extends AsHttpRequestModel{

	private XmlConfigReader configReader;
	
	public SearchForPurchasingSvcModel(LMModelDelegate delegate) {
		super(delegate);
		// TODO 自动生成的构造函数存根
		configReader = XmlConfigReader.getInstance();
	}
	
	public void load(HashMap parm){
		try {
			String queryUrl = configReader
			        .getAttr(new Expression(
			                "/backend-config/url[@name='receiveParmUrl']",
			                "value"));
//			String queryUrl = ConstantUtl.receiveParmUrl;
			this.post(queryUrl, parm);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
