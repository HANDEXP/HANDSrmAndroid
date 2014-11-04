package com.hand.srm.model;

import com.hand.srm.utl.ConstantUtl;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;

public class LoadingModel  extends AsHttpRequestModel{

	public LoadingModel(LMModelDelegate delegate) {
		
		super(delegate);
		
	}
	
	
	public void load()
	{
		
		get(ConstantUtl.configFile, null);
		
	}
	
}
