package com.hand.srm.adapter;

import java.util.List;

import com.hand.srm.R;
import com.hand.srm.model.ShopPoListDetailModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ShopPoListDeatilAdapter extends BaseAdapter{

	private List<ShopPoListDetailModel> data;
	private Context context;
	private String currencySymbol;
	
	public ShopPoListDeatilAdapter(List<ShopPoListDetailModel> data, Context context,String currencySymbol){
		this.data = data;
		this.context = context;
		this.currencySymbol = currencySymbol;
	}
	
	
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		ShopPoListDetailModel dataInfo = data.get(position);
		String itemCodeString = dataInfo.getItemCode();
		String itemDescString = dataInfo.getItemDesc();
		String priceString = dataInfo.getPrice();
		String quantityString = dataInfo.getQuantity();
		String unitString = dataInfo.getUnit();
		String totalAmountString = dataInfo.getTotalAmount();
		
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.fragment_ship_list_detail, null);
		}
		TextView itemCode = (TextView) convertView.findViewById(R.id.itemCodeTextView);
		TextView itemDesc = (TextView) convertView.findViewById(R.id.itemDescTextView);
		TextView price = (TextView) convertView.findViewById(R.id.priceTextView);
		TextView amount = (TextView) convertView.findViewById(R.id.amountTextView);
		TextView totalAmount = (TextView) convertView.findViewById(R.id.totalAmountTextView);
		itemCode.setText(itemCodeString);
		itemDesc.setText(itemDescString);
		price.setText(currencySymbol.concat(priceString));
		amount.setText(quantityString.concat(unitString));
		totalAmount.setText(currencySymbol.concat(totalAmountString));
		
		return convertView;
	}

}
