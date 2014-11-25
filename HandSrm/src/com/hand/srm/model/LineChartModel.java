package com.hand.srm.model;

import com.hand.srm.utl.ConstantUtl;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;

public class LineChartModel extends AsHttpRequestModel{

	public LineChartModel(LMModelDelegate delegate) {
		super(delegate);

	}
	
	public void load()
	{
		
		this.get(ConstantUtl.lineChartUrl, null);
		
	}

}
