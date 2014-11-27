package com.hand.srm.model;

import java.util.HashMap;

import org.json.JSONArray;

import com.hand.srm.utl.ConstantUtl;
import com.littlemvc.model.LMModel;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;

public class UrgentModel extends AsHttpRequestModel{

	public String  CurrentMethod;
	
	public UrgentModel(LMModelDelegate delegate) {
		super(delegate);
	}
	
	public void AddUrgent(JSONArray addUrgenArray){
		
		HashMap  param =  new HashMap<String, JSONArray>();
		param.put("asn_headers", addUrgenArray.toString());
		
		post(ConstantUtl.closeUrl, param);
	}
	
	public void CancelUrgent(JSONArray cancelUrgentArray){
		
		
	}

}
