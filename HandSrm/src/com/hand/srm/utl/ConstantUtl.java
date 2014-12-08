package com.hand.srm.utl;

public class ConstantUtl {
	
	public static  String  basicUrl = "http://172.20.0.125:10000/cloudtrain/";
	public static  String  configFile = "srm-app-android-1.xml";
	public static  String  loginUrl = "modules/app/login/app_login.svc";
	
//	public static  String  appPoList = "modules/app/order/app_po_list.svc";
//	public static  String  appPoParmUrl = "modules/app/order/app_po_list_with_parameter.svc";
//	public static  String  appPoDetailUrl = "modules/app/order/app_po_detail_info.svc";
//	public static  String  appAsnListUrl = "modules/app/order/app_asn_list.svc";
//	public static  String  appAsnParmUrl = "modules/app/order/app_asn_list_with_parameter.svc";
//	public static  String  appAsnDetailUrl = "modules/app/order/app_asn_detail_info.svc";
	public static  String  appPoInfoUrl = "modules/app/order/app_po_info.svc";
	public static  String  appAsnInfoUrl = "modules/app/order/app_asn_info.svc";
	public static  String  shipUrl = "modules/app/main/app_enable_to_ship_po_list.svc";
	public static  String  shipParmUrl = "modules/app/main/app_enable_to_ship_po_list_with_parameter.svc";
	public static  String  shipDetailUrl = "modules/app/main/app_enable_to_ship_po_detail_info.svc";
	public static  String  receiveUrl = "modules/app/main/app_enable_to_receive_asn_list.svc";
	public static  String  receiveParmUrl = "modules/app/main/app_enable_to_receive_asn_list_with_parameter.svc";
	public static  String  receiveDetailUrl = "modules/app/main/app_enable_to_receive_asn_detail_info.svc";

	////////////////////柱状图/////////////////
	public static  String barChartUrl =  "modules/app/report/app_pur_po_vender_list.svc";

	///////////////////饼状图//////////////
	public static String pieChartUrl = "modules/app/report/app_pur_po_item_vender_form.svc";
	
	///////////////////线状图///////////////
	public static String lineChartUrl = "modules/app/report/app_pur_po_vender_rpt.svc";

	
	////////////////整单关闭////////////////
	public static String  closeUrl =  "modules/app/main/app_enable_to_receive_asn_close.svc";
	
	///////////////整单加急////////////////
	public  static String  addUrgentUrl = "modules/app/main/app_enable_to_ship_po_urgent.svc";
	
	///////////////整单取消加急////////////
	public static  String  cancelUrgentUrl = "modules/app/main/app_enable_to_ship_po_urgent_abolish.svc";
	
	
	////////////////查询首页接口///////////
	public static String amountUrl   = "modules/app/main/app_get_enable_amount.svc";
	
}
