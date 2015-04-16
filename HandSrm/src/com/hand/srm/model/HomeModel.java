package com.hand.srm.model;

import com.hand.hrms4android.exception.ParseExpressionException;
import com.hand.hrms4android.parser.Expression;
import com.hand.hrms4android.parser.xml.XmlConfigReader;
import com.hand.srm.utl.ConstantUtl;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;

public class HomeModel extends AsHttpRequestModel{

	private XmlConfigReader configReader;
	
	public HomeModel(LMModelDelegate delegate) {
		super(delegate);
		configReader = XmlConfigReader.getInstance();
	
	}
	
	public void load()
	{
		try {
			String queryUrl = configReader
			        .getAttr(new Expression(
			                "/backend-config/url[@name='amountUrl']",
			                "value"));
			get(queryUrl, null);
		} catch (ParseExpressionException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
	}

}
