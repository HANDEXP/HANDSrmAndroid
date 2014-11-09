package com.hand.srm.item;

import android.content.Intent;

public class TabItem {

	private String title; // tab item title
	private int icon; // tab item icon
	private Intent intent; // tab item intent

	public TabItem(String title, int icon, Intent intent) {
		super();
		this.title = title;
		this.icon = icon;
		this.intent = intent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}
}
