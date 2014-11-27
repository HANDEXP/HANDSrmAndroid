package com.hand.srm.model;

import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import com.hand.srm.utl.ConstantUtl;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;

public class CloseModel extends AsHttpRequestModel{

	public CloseModel(LMModelDelegate delegate) {
		super(delegate);
	}
	
	public void load(JSONArray closeArray){
		
		HashMap  param =  new HashMap<String, JSONArray>();
		param.put("asn_headers", closeArray.toString());
		
		post(ConstantUtl.closeUrl, param);
		
	}
	
	

}
