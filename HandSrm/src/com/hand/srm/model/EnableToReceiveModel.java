package com.hand.srm.model;

public class EnableToReceiveModel {
	private String asn_header_id;
	private String asn_num;
	private String asn_type_name;
	private String vendor_id;
	private String vendor_name;
	private String status_name;
	private String expected_date;
	private String ship_date;
	private String ship_time;
	private String ship_day;
	
	public EnableToReceiveModel(String asn_header_id,String asn_num,String asn_type_name,String vendor_id,String vendor_name,
										String status_name,String expected_date,String ship_date,
										String ship_time, String ship_day) {
		this.asn_header_id = asn_header_id;
		this.asn_num = asn_num;
		this.asn_type_name = asn_type_name; 
		this.vendor_id = vendor_id;
		this.vendor_name = vendor_name;
		this.status_name = status_name;
		this.expected_date = expected_date;
		this.ship_date = ship_date;
		this.ship_day = ship_day;
		this.ship_time = ship_time;		
	}
	
	public String getAsnHeaderId() {
		
		return this.asn_header_id;
	}
	public String getAsnNum() {
		
		return this.asn_num;
	}
	public String getAsnTypeName(){
		return this.asn_type_name;
	}
	public String getVendorId(){
		return this.vendor_id;
	}
	public String getVendorName(){
		return this.vendor_name;
	}
	public String getStatusName(){
		return this.status_name;
	}
	public String getExpectedDate(){
		return this.expected_date;
	}
	public String getShipDate(){
		return this.ship_date;
	}
	public String getShipDay(){
		return this.ship_day;
	}
	public String getShipTime(){
		return this.ship_time;
	}
}
