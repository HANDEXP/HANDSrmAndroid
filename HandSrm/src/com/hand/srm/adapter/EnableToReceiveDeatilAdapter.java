package com.hand.srm.adapter;

import java.util.List;

import com.hand.srm.R;
import com.hand.srm.model.EnableToReceiveDetailModel;
import com.hand.srm.model.EnableToReceiveModel;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EnableToReceiveDeatilAdapter extends BaseAdapter{

	private List<EnableToReceiveDetailModel> data;
	private Context context;
	
	public EnableToReceiveDeatilAdapter(List<EnableToReceiveDetailModel> data, Context context){
		this.data = data;
		this.context = context;
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
		EnableToReceiveDetailModel dataInfo = data.get(position);
		String itemCodeString = dataInfo.getItemCode();
		String itemDescString = dataInfo.getItemDesc();
		String lineStatusString = dataInfo.getLineStatus();
		String poNumString = dataInfo.getPoNum();
		String shipQuantityString = dataInfo.getShipQuantity();
		String receiveQuantityString = dataInfo.getReceiveQuantity();
		String uomNameString = dataInfo.getUomName();
		String onTheWayQuantityString = dataInfo.getOnTheWayQuantity();
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.fragment_receive_list_detail, null);
		}
		TextView itemCode = (TextView) convertView.findViewById(R.id.itemCodeTextView);
		TextView itemDesc = (TextView) convertView.findViewById(R.id.itemDescTextView);
		TextView poNum = (TextView) convertView.findViewById(R.id.poNumTextView);
		TextView lineStatus = (TextView) convertView.findViewById(R.id.itemStatusTextView);
		TextView shipQuantity = (TextView) convertView.findViewById(R.id.shipQuantityTextView);
		TextView receiveQuantity = (TextView) convertView.findViewById(R.id.receiveQuantityTextView);
		TextView onTheWayQuantity = (TextView) convertView.findViewById(R.id.onTheWayQuantityTextView);
		itemCode.setText(itemCodeString);
		itemDesc.setText(itemDescString);
		poNum.setText(poNumString);
		lineStatus.setText(lineStatusString);
		shipQuantity.setText(shipQuantityString.concat(uomNameString));
		receiveQuantity.setText(receiveQuantityString.concat(uomNameString));
		onTheWayQuantity.setText(onTheWayQuantityString.concat(uomNameString));
		
		return convertView;
	}

}
