package com.hand.srm.model;

import com.hand.srm.utl.ConstantUtl;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;

public class PieChartModel extends AsHttpRequestModel{

	public PieChartModel(LMModelDelegate delegate) {
		super(delegate);
	}
	
	
	public void load()
	{
		
		get(ConstantUtl.pieChartUrl, null);
		
	}

}
