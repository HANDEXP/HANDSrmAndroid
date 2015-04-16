package com.hand.srm.model;

import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import com.hand.hrms4android.exception.ParseExpressionException;
import com.hand.hrms4android.parser.Expression;
import com.hand.hrms4android.parser.xml.XmlConfigReader;
import com.hand.srm.utl.ConstantUtl;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;

public class CloseModel extends AsHttpRequestModel{

	private XmlConfigReader configReader;
	
	public CloseModel(LMModelDelegate delegate) {
		super(delegate);
		configReader = XmlConfigReader.getInstance();
	}
	
	public void load(JSONArray closeArray){
		
		String queryUrl = null;
		try {
			queryUrl = configReader
			        .getAttr(new Expression(
			                "/backend-config/url[@name='closeUrl']",
			                "value"));
		} catch (ParseExpressionException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		HashMap  param =  new HashMap<String, JSONArray>();
		param.put("asn_headers", closeArray.toString());
		
		
		post(queryUrl, param);
		
	}
	
	

}
