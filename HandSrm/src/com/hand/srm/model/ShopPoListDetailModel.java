package com.hand.srm.model;

public class ShopPoListDetailModel {
	private String itemCode;
	private String itemDesc;
	private String price;
	private String quantity;
	private String unit;
	private String totalAmount;
	
	public ShopPoListDetailModel(String itemCode,String itemDesc,String price,String quantity,String unit,String totalAmount){
		this.itemCode = itemCode;
		this.itemDesc = itemDesc;
		this.price = price;
		this.quantity = quantity;
		this.unit = unit;
		this.totalAmount = totalAmount;
	}
	
	public String getItemCode(){
		return itemCode;
	}
	
	public String getItemDesc(){
		return itemDesc;
	}
	
	public String getPrice(){
		return price;
	}
	
	public String getQuantity(){
		return quantity;
	}
	
	public String getUnit(){
		return unit;
	}
	
	public String getTotalAmount(){
		return totalAmount;
	}
}
