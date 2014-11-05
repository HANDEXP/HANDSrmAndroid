package com.hand.srm.activities;

import com.hand.srm.model.LoadingModel;
import com.littlemvc.model.LMModel;
import com.littlemvc.model.LMModelDelegate;

import android.app.Activity;
import android.os.Bundle;

public class LoadingActivity extends Activity implements LMModelDelegate{
	
	LoadingModel model;
	
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		model = new LoadingModel(this);
		
		
	}
	 
	 
	 @Override
	protected void onResume() {
		 super.onResume();
		 
		 model.load();
	 }


	@Override
	public void modelDidFinshLoad(LMModel model) {
		
		System.out.println("hello world");
		
	}


	@Override
	public void modelDidStartLoad(LMModel model) {
		
	}


	@Override
	public void modelDidFaildLoadWithError(LMModel model) {
		
		System.out.println("hello world");
		
		
	}
	 
}
