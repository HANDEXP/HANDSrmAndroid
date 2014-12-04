package com.hand.srm.model;

public class ShopPoListModel {
	private String pur_header_id;
	private String num;
	private String vendor_id;
	private String vendor_name;
	private String srm_status_name;
	private String total_amount;
	private String currency_code;
	private String currency_symbol;
	private String release_date;
	private String release_time;
	private String release_day;
	
	public ShopPoListModel(String pur_header_id,String num,String vendor_id,String vendor_name,
										String srm_status_name,String total_amount,String release_date,
										String release_time, String release_day,String currency_code,String currency_symbol) {
		this.pur_header_id = pur_header_id;
		this.num = num;
		this.vendor_id = vendor_id;
		this.vendor_name = vendor_name;
		this.srm_status_name = srm_status_name;
		this.total_amount = total_amount;
		this.release_date = release_date;
		this.release_day = release_day;
		this.release_time = release_time;	
		this.currency_code = currency_code;
		this.currency_symbol = currency_symbol;
	}
	
	public String getPurHeaderId() {
		
		return this.pur_header_id;
	}
	public String getNum() {
		
		return this.num;
	}
	public String getVendorId(){
		return this.vendor_id;
	}
	public String getVendorName(){
		return this.vendor_name;
	}
	public String getSrmStatusName(){
		return this.srm_status_name;
	}
	public String getTotalAmount(){
		return this.total_amount;
	}
	public String getReleaseDate(){
		return this.release_date;
	}
	public String getReleaseDay(){
		return this.release_day;
	}
	public String getReleaseTime(){
		return this.release_time;
	}
	public String getCurrencyCode(){
		return this.currency_code;
	}
	public String getCurrencySymbol(){
		return this.currency_symbol;
	}
	
}
