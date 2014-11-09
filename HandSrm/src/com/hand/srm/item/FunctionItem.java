package com.hand.srm.item;

import android.content.Intent;

public class FunctionItem {
	
	public String title; // tab item title
	public int icon; // tab item icon
	public Intent intent; // tab item intent
	
	public FunctionItem(String title,int icon,Intent intent)
	{
		this.title = title;
		this.icon = icon;
		this.intent = intent;
		
	}
	
}
