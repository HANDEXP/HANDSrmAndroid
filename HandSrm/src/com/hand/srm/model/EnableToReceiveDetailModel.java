package com.hand.srm.model;

public class EnableToReceiveDetailModel {
	private String itemCode;
	private String itemName;
	private String poNum;
	private String shipQuantity;
	private String receiveQuantity;
	private String onTheWayQuantity;
	private String uomName;
	private String lineStatus;
	
	public EnableToReceiveDetailModel(String itemCode,String itemName,String poNum,String shipQuantity,String receiveQuantity,String onTheWayQuantity,String uomName,String lineStatus){
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.poNum = poNum;
		this.shipQuantity = shipQuantity;
		this.receiveQuantity = receiveQuantity;
		this.onTheWayQuantity = onTheWayQuantity;
		this.uomName = uomName;
		this.lineStatus = lineStatus;
	}
	
	public String getItemCode(){
		return itemCode;
	}
	
	public String getItemDesc(){
		return itemName;
	}
	
	public String getPoNum(){
		return poNum;
	}
	
	public String getShipQuantity(){
		return shipQuantity;
	}
	
	public String getReceiveQuantity(){
		return receiveQuantity;
	}
	
	public String getOnTheWayQuantity() {
		return onTheWayQuantity;
	}
	
	public String getUomName(){
		return uomName;
	}
	
	public String getLineStatus(){
		return lineStatus;
	}
}
