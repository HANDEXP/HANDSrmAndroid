package com.hand.srm.model;

import com.hand.srm.utl.ConstantUtl;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;
import com.littlemvc.model.request.LMRequestModel;

public class BarChartModel extends AsHttpRequestModel{

	public BarChartModel(LMModelDelegate delegate) {
		super(delegate);
	}
	
	public void load()
	{
		
		this.get(ConstantUtl.barChartUrl, null);
		
	}

}
