package com.hand.srm.model;

import com.hand.hrms4android.exception.ParseExpressionException;
import com.hand.hrms4android.parser.Expression;
import com.hand.hrms4android.parser.xml.XmlConfigReader;
import com.hand.srm.utl.ConstantUtl;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;
import com.littlemvc.model.request.LMRequestModel;

public class BarChartModel extends AsHttpRequestModel{

	private  XmlConfigReader configReader;	
	
	public BarChartModel(LMModelDelegate delegate) {
		super(delegate);
		configReader = XmlConfigReader.getInstance();
	}
	
	public void load()
	{
		String queryUrl = null;
		try {
			queryUrl = configReader
			        .getAttr(new Expression(
			                "/backend-config/url[@name='barChartUrl']",
			                "value"));
		} catch (ParseExpressionException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		this.get(queryUrl, null);
		
	}

}
